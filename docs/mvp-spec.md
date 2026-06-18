# MVP Dofus Raid

## Objectif

Permettre à une guilde de suivre un raid et d'afficher, à chaque palier débloqué, les
informations essentielles :

- objectif
- résumé
- actions
- rôles concernés
- pièges
- condition de réussite
- suite
- conseil

## Fonctionnalités

- liste des raids
- détail d'un raid
- progression d'une guilde
- validation du palier courant
- chat contextuel sur un palier

## Statuts métier

- Raid : `NOT_STARTED`, `IN_PROGRESS`, `COMPLETED`
- Palier : `LOCKED`, `UNLOCKED`, `VALIDATED`

## Règles

- un palier verrouillé n'affiche pas le détail complet
- un palier débloqué affiche automatiquement sa fiche
- le chat répond uniquement avec les informations du raid et du palier courant
- valider un palier `UNLOCKED` débloque le suivant ; le dernier validé termine le raid

## Hors périmètre

- authentification
- synchronisation temps réel
- permissions avancées
- génération automatique de stratégie
