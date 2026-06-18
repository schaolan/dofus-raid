#!/usr/bin/env bash
# Lance toute la stack en conteneurs (postgres + backend + frontend) via docker compose.
set -euo pipefail

cd "$(dirname "$0")/.."

if ! command -v docker >/dev/null 2>&1; then
  echo "[start-stack] Docker introuvable. Installez Docker Desktop puis relancez." >&2
  exit 1
fi

docker compose --profile full up -d --build
echo "[start-stack] Frontend : http://localhost:5173"
echo "[start-stack] Backend  : http://localhost:8080"
