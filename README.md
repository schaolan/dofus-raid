# dofus-raid-mvp

Application MVP de suivi de raids de guilde Dofus avec :

- progression par paliers
- fiche de palier concise
- chat contextuel sur l'etape courante

## Stack

- **Backend** : Java 25, Spring Boot 4.1, PostgreSQL, Flyway
- **Frontend** : React, Vite, TypeScript, TanStack Router, TanStack Query

## Structure

- `backend/` : API REST
- `frontend/` : application web
- `docs/` : documentation

## Demarrage rapide

### 1. Lancer PostgreSQL

```bash
docker compose up -d
```

### 2. Lancer le backend

```bash
cd backend
./gradlew bootRun
```

ou si Gradle est installe : `gradle bootRun`

### 3. Lancer le frontend

```bash
cd frontend
npm install
npm run dev
```

## Scripts de lancement

Des scripts fournis dans `scripts/` (Git Bash et Windows `.cmd`) automatisent le demarrage.
Le script backend detecte automatiquement un JDK 25 (`~/.jdks`, Eclipse Adoptium, etc.).

| Action | Git Bash | Windows (cmd) |
|---|---|---|
| Tout lancer (db + back + front) | `./scripts/dev.sh` | _(lancer back et front separement)_ |
| Base PostgreSQL | `./scripts/start-db.sh` | `scripts\start-db.cmd` |
| Backend | `./scripts/start-backend.sh` | `scripts\start-backend.cmd` |
| Frontend | `./scripts/start-frontend.sh` | `scripts\start-frontend.cmd` |

`dev.sh` demarre la base (si Docker present), lance le backend en arriere-plan puis le
frontend ; `Ctrl+C` arrete les deux. Si Docker est absent, demarrez un PostgreSQL 16 sur
`localhost:5432` (db `dofusraid`, user `dofus`, password `dofus`).

## URLs

- Frontend : http://localhost:5173
- Backend : http://localhost:8080

## Fonctionnalites MVP

- lister les raids
- demarrer un raid pour une guilde
- afficher les paliers
- consulter une fiche de palier
- valider un palier
- poser une question contextuelle

## Donnees seed

- 1 guilde (`Les Veilleurs`, id 1)
- 1 raid (`Forge Obscure`, id 1)
- 3 paliers

## Endpoints principaux

- `GET /api/guilds`
- `GET /api/raids`
- `GET /api/raids/{raidId}`
- `GET /api/raids/{raidId}/paliers?guildId=1`
- `POST /api/guilds/{guildId}/raids/{raidId}/start`
- `GET /api/guilds/{guildId}/raids/{raidId}/progression`
- `POST /api/guilds/{guildId}/raids/{raidId}/paliers/{palierId}/validate`
- `POST /api/chat/questions`

## Note de compatibilite

Java 25 + Spring Boot 4.1 est un choix moderne assume. Si le build echoue dans votre
environnement, repliez sur Java 21 LTS et une version stable de Spring Boot compatible.
