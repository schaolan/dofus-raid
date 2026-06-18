#!/usr/bin/env bash
# Demarre PostgreSQL via docker compose si Docker est disponible.
set -euo pipefail

cd "$(dirname "$0")/.."

if command -v docker >/dev/null 2>&1; then
  docker compose up -d
else
  echo "[start-db] Docker introuvable." >&2
  echo "[start-db] Demarrez un PostgreSQL 16 sur localhost:5432 (db=dofusraid, user=dofus, password=dofus)." >&2
  exit 1
fi
