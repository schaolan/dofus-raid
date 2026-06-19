# Backend

API REST Spring Boot du MVP Dofus Raid.

## Prerequis

- Java 25
- Gradle (ou utiliser le wrapper `./gradlew`)
- PostgreSQL 16 (via `docker compose up -d` a la racine)

## Lancement

```bash
./gradlew bootRun
```

Build du jar executable :

```bash
./gradlew clean build
java -jar build/libs/dofus-raid-backend-0.0.1-SNAPSHOT.jar
```

## Build system

- Gradle 9.1 (Kotlin DSL : `build.gradle.kts`, `settings.gradle.kts`)
- Toolchain Java 25 declaree dans `build.gradle.kts` (Gradle telecharge le JDK si absent)

## Architecture hexagonale (ports & adapters)

Chaque contexte (`guild`, `raid`, `progression`, `chat`) suit le decoupage :

```
<contexte>/
├── domain/                 modele metier pur (records, enums) - aucune dependance framework
├── application/
│   ├── port/in/            cas d'usage (interfaces *UseCase / *Query)
│   ├── port/out/           ports de sortie (interfaces *Port)
│   └── service/            services applicatifs (@Service) implementant les ports in
└── adapter/
    ├── in/web/             controleurs REST + DTOs web (statut/difficulte serialises en String)
    └── out/
        ├── persistence/    entites JPA (*JpaEntity), repos Spring Data (*JpaRepository),
        │                   *PersistenceAdapter implementant les ports out, mappers entite<->domaine
        └── external/        adapters appelant les use cases d'un autre contexte
```

Regles de dependance : `adapter -> application -> domain`. Le domaine ne depend de rien.
Les echanges entre contextes passent par des ports :

- `progression -> raid` via `RaidCatalogPort` (impl. `RaidCatalogAdapter`)
- `progression -> guild` via `GuildDirectoryPort` (impl. `GuildDirectoryAdapter`)
- `chat -> raid` via `PalierLookupPort` (impl. `RaidPalierAdapter`)
- l'endpoint `GET /api/raids/{raidId}/paliers` delegue le calcul du statut au port
  d'entree `PalierViewQuery` du contexte progression (le statut appartient a progression).

L'encodage de stockage `"a|b|c"` des listes (actions/roles/pieges) est un detail de
persistance decode en `List<String>` par `PalierPersistenceMapper` ; le domaine ne manipule
que des listes.

## Endpoints principaux

- `GET /api/guilds`
- `GET /api/raids`
- `GET /api/raids/{raidId}`
- `GET /api/raids/{raidId}/paliers?guildId=1`
- `POST /api/guilds/{guildId}/raids/{raidId}/start`
- `GET /api/guilds/{guildId}/raids/{raidId}/progression`
- `POST /api/guilds/{guildId}/raids/{raidId}/paliers/{palierId}/validate`
- `POST /api/chat/questions`
