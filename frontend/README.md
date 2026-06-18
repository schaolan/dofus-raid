# Frontend

Application React du MVP Dofus Raid.

## Prerequis

- Node 20+
- npm

## Lancement

```bash
npm install
npm run dev
```

Le frontend attend le backend sur `http://localhost:8080` (configurable via
`VITE_API_URL`, voir `.env.example`).

## Pages

- `/` : liste des raids
- `/raids/:raidId` : detail d'un raid et ses paliers
- `/raids/:raidId/paliers/:palierId` : fiche de palier + chat contextuel

## Routage

Le projet utilise TanStack Router en mode declaratif (`src/app/router.tsx`), sans
generation de `routeTree.gen.ts`, pour un demarrage sans outillage supplementaire.
