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

## Endpoints principaux

- `GET /api/guilds`
- `GET /api/raids`
- `GET /api/raids/{raidId}`
- `GET /api/raids/{raidId}/paliers?guildId=1`
- `POST /api/guilds/{guildId}/raids/{raidId}/start`
- `GET /api/guilds/{guildId}/raids/{raidId}/progression`
- `POST /api/guilds/{guildId}/raids/{raidId}/paliers/{palierId}/validate`
- `POST /api/chat/questions`
