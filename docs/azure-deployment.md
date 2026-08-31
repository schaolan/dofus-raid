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
2. la **souscription** et la **région** (ex. `westeurope` / `francecentral`) ;
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
- `AZURE_LOCATION` = région (ex. `westeurope`)

## Suppression des ressources

```bash
azd down --purge
```

Supprime le groupe de ressources et purge l'ACR/PostgreSQL.

## Coûts indicatifs (ordre de grandeur, région Europe)

- PostgreSQL Flexible `Standard_B1ms` (Burstable) : le poste principal.
- 2 Container Apps : facturés à l'usage (possibilité de `minReplicas: 0` pour scale-to-zero
  si un léger cold start est acceptable — actuellement `minReplicas: 1`).
- ACR `Basic` + Log Analytics : marginaux.

Pour réduire les coûts d'un environnement de démo, envisager `minReplicas: 0` sur le
frontend et/ou l'arrêt du serveur PostgreSQL entre deux sessions.
