#!/usr/bin/env bash
# Lance le frontend Vite (installe les dependances au premier lancement).
set -euo pipefail

cd "$(dirname "$0")/../frontend"

if [ ! -d node_modules ]; then
  echo "[start-frontend] npm install..."
  npm install
fi

exec npm run dev
