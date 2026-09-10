# Déploiement Azure

Déploiement de la stack (backend Spring Boot, frontend React/nginx, PostgreSQL) sur
**Azure Container Apps** + **Azure Database for PostgreSQL Flexible Server**, provisionné
en **Bicep** et orchestré par **Azure Developer CLI (`azd`)**.

## Architecture

```
Internet ─▶ frontend  (Container App, ingress externe :80, nginx)
                 │  proxy /api/*  (même origine, pas de CORS)
                 ▼
             backend   (Container App, ingress INTERNE :8080, Spring Boot)
                 │  JDBC + sslmode=require
                 ▼
        PostgreSQL Flexible Server (managé, base « dofusraid »)
```

- Le **frontend** est construit en relatif (`VITE_API_URL=""`) : le navigateur appelle
  `/api/...` en même origine, et **nginx proxifie** ces requêtes vers l'ingress interne du
  backend (`BACKEND_URL`). Aucun CORS, aucune URL backend figée au build.
- Le **backend** n'est pas exposé publiquement (ingress interne).
- **Flyway** applique les migrations `V1`/`V2` au démarrage sur la base vide.
- Les images sont poussées dans un **Azure Container Registry**, tirées via une **identité
  managée** (rôle `AcrPull`).

## Prérequis

- [Azure Developer CLI (`azd`)](https://learn.microsoft.com/azure/developer/azure-developer-cli/install-azd)
- [Azure CLI (`az`)](https://learn.microsoft.com/cli/azure/install-azure-cli)
- Docker (build local des images par `azd`)
- Une souscription Azure avec droits de création de ressources

## Déploiement en une commande

```bash
azd auth login
azd up
```

`azd up` demande :
1. un **nom d'environnement** (préfixe des ressources, ex. `dofus-prod`) ;
2. la **souscription** et la **région** (voir [Régions supportées](#régions-supportées)) ;
3. le **mot de passe administrateur PostgreSQL** (paramètre `postgresAdminPassword`).

> Astuce : pour éviter la saisie interactive du mot de passe, définissez-le avant :
> ```bash
> azd env set POSTGRES_PASSWORD '<MotDePasseFort!>'
> azd up
> ```

À la fin, `azd` affiche l'URL du frontend (sortie `FRONTEND_URL`).

### Étapes exécutées

- `azd provision` : déploie `infra/main.bicep` (RG, ACR, environnement Container Apps,
  identité managée, PostgreSQL, 2 Container Apps avec image temporaire).
- `azd deploy` : build les 2 Dockerfiles, pousse les images dans l'ACR et met à jour les
  Container Apps (mapping via le tag `azd-service-name`).

## Paramètres & variables

| Élément | Source | Défaut |
|---|---|---|
| `environmentName` | `AZURE_ENV_NAME` (azd) | — (saisi) |
| `location` | `AZURE_LOCATION` (azd) | — (saisi) |
| `postgresAdminLogin` | `POSTGRES_ADMIN_LOGIN` | `dofusadmin` |
| `postgresAdminPassword` | `POSTGRES_PASSWORD` (secret) | — (saisi) |

Variables d'application injectées automatiquement par le Bicep :
- backend : `SPRING_DATASOURCE_URL` (avec `sslmode=require`), `SPRING_DATASOURCE_USERNAME`,
  `SPRING_DATASOURCE_PASSWORD` (secret), `APP_CORS_ALLOWED_ORIGINS`.
- frontend : `BACKEND_URL` (FQDN interne HTTPS du backend).

## Régions supportées

Le paramètre `location` de [`infra/main.bicep`](../infra/main.bicep) est volontairement
restreint (`@allowed`) aux régions où **PostgreSQL Flexible Server** est provisionnable :

`francecentral`, `northeurope`, `swedencentral`, `uksouth`, `centralus`, `westus3`,
`canadacentral`.

Plusieurs régions très courantes (`eastus`, `eastus2`, `westeurope`,
`germanywestcentral`, …) sont **restreintes** pour les souscriptions pay-as-you-go /
MSDN : la création du serveur PostgreSQL y échoue avec une erreur trompeuse, tandis que
toutes les autres ressources (ACR, Container Apps, Log Analytics) se créent normalement :

```
ParameterOutOfRange: The value of the 'Version' should be in: [].
```

`azd` traduit cet échec en un message encore plus trompeur :
`A resource with this name already exists or is in a conflicting state`. Il ne s'agit
**pas** d'un conflit de nom.

### Vérifier la disponibilité pour votre souscription

```bash
az provider show --namespace Microsoft.DBforPostgreSQL --query "resourceTypes[?resourceType=='flexibleServers'].locations" -o tsv

# Détail pour une région donnée (liste vide de versions = région restreinte)
az postgres flexible-server list-skus --location francecentral -o table
```

Si la région est restreinte, la réponse contient :
`Subscriptions are restricted from provisioning in this region.`

> ⚠️ `resourceToken` dérive de `uniqueString(subscription().id, environmentName, location)` :
> changer de région après un déploiement renomme **toutes** les ressources. Faites
> `azd down --purge` (ou supprimez le groupe `rg-<env>`) avant de changer `AZURE_LOCATION`,
> sinon l'ancien jeu de ressources reste orphelin et facturé dans le même groupe.

### Diagnostiquer un échec de `provision`

`azd` masque souvent l'erreur réelle. Pour l'obtenir :

```bash
# Dernier déploiement au scope souscription
az deployment sub list --query "[0].{name:name,state:properties.provisioningState}" -o table

# Opérations en échec du déploiement imbriqué (scope groupe de ressources)
az deployment operation group list -g rg-<env> -n resources \
  --query "[?properties.provisioningState=='Failed'].properties.statusMessage" -o json
```

## Vérification

```bash
# Logs backend (Flyway + démarrage Spring)
az containerapp logs show -g rg-<env> -n ca-backend-<token> --follow

# API via le frontend (proxy nginx)
curl https://<FRONTEND_URL>/api/raids
```

Attendu : `Successfully applied 2 migrations ... v2`, `Started DofusRaidApplication`, et
la liste des raids (*Sanctuaire des Jardins eternels*, *Gouffre du Gigalodon*).

## CI/CD — GitHub Actions (OIDC)

Le workflow [`.github/workflows/deploy-azure.yml`](../.github/workflows/deploy-azure.yml)
déploie sur `push main` ou manuellement (`workflow_dispatch`).

### 1. Créer une application Entra ID + federated credential

```bash
# App + service principal
appId=$(az ad app create --display-name "dofus-raid-github" --query appId -o tsv)
az ad sp create --id "$appId"

# Federated credential pour la branche main
az ad app federated-credential create --id "$appId" --parameters '{
  "name": "github-main",
  "issuer": "https://token.actions.githubusercontent.com",
  "subject": "repo:<OWNER>/<REPO>:ref:refs/heads/main",
  "audiences": ["api://AzureADTokenExchange"]
}'

# Droits Contributor + User Access Administrator (pour l'attribution de rôle AcrPull)
subId=$(az account show --query id -o tsv)
spId=$(az ad sp show --id "$appId" --query id -o tsv)
az role assignment create --assignee "$spId" --role "Contributor" --scope "/subscriptions/$subId"
az role assignment create --assignee "$spId" --role "User Access Administrator" --scope "/subscriptions/$subId"
```

> Pour un déclenchement `workflow_dispatch`, ajoutez aussi un federated credential avec
> `subject: repo:<OWNER>/<REPO>:ref:refs/heads/main` (déjà couvert) ou
> `subject: repo:<OWNER>/<REPO>:environment:<env>` selon votre configuration.

### 2. Configurer le dépôt GitHub

**Secrets** (Settings → Secrets and variables → Actions → Secrets) :
- `AZURE_CLIENT_ID` = `$appId`
- `AZURE_TENANT_ID` = `az account show --query tenantId -o tsv`
- `AZURE_SUBSCRIPTION_ID` = `$subId`
- `POSTGRES_PASSWORD` = mot de passe admin PostgreSQL

**Variables** (onglet Variables) :
- `AZURE_ENV_NAME` = nom d'environnement (ex. `dofus-prod`)
- `AZURE_LOCATION` = région (ex. `francecentral`, cf. [Régions supportées](#régions-supportées))

## Suppression des ressources

```bash
azd down --purge
```

Supprime le groupe de ressources et purge l'ACR/PostgreSQL.

## Scale-to-zero

Les deux Container Apps sont en **`minReplicas: 0`** ([`infra/resources.bicep`](../infra/resources.bicep)) :
après ~5 min sans trafic (cooldown KEDA par défaut), plus aucune replica n'est facturée.

Contrepartie mesurée en `francecentral` depuis 0 replica :

| Requête | À froid | À chaud |
|---|---|---|
| `GET /` (réveil nginx) | ~22 s | ~0,05 s |
| `GET /api/raids` (réveil JVM + Spring Boot + Flyway, via le proxy) | ~54 s | ~0,11 s |

Un visiteur qui arrive sur un environnement en veille attend donc jusqu'à **~75 s** avant
de voir la liste des raids. C'est le prix du scale-to-zero sur une stack JVM.

> ⚠️ Les timeouts du `location /api/` de [`frontend/nginx.conf.template`](../frontend/nginx.conf.template)
> sont portés à **90 s** pour cette raison. Avec les 60 s par défaut de nginx, le réveil du
> backend (~54 s) passe tout juste — un démarrage un peu plus lent renverrait un **504**.
> Ne pas les rabaisser tant que `minReplicas: 0` est actif sur le backend.

### Revenir à des replicas chaudes

Dans [`infra/resources.bicep`](../infra/resources.bicep), repasser `minReplicas` à `1` :

- sur le **backend** uniquement : supprime les ~54 s d'attente sur l'API, ne laisse que le
  réveil nginx (~22 s). Bon compromis latence/coût ;
- sur les **deux** : plus aucun cold start, coût de compute permanent.

## Coûts indicatifs (ordre de grandeur, région Europe)

- PostgreSQL Flexible `Standard_B1ms` (Burstable) : le poste principal, facturé **24/7
  indépendamment du scale-to-zero** des Container Apps.
- 2 Container Apps : facturés à l'usage, ~0 en veille grâce à `minReplicas: 0`.
- ACR `Basic` + Log Analytics : marginaux.

Le scale-to-zero ne réduit que la part compute. Pour un environnement de démo, le vrai
levier restant est **l'arrêt du serveur PostgreSQL** entre deux sessions :

```bash
az postgres flexible-server stop  -g rg-<env> -n psql-<token>
az postgres flexible-server start -g rg-<env> -n psql-<token>
```

(Azure redémarre automatiquement un serveur arrêté au bout de 7 jours.)
