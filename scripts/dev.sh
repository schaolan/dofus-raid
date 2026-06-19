#!/usr/bin/env bash
# Lance toute la stack en dev : PostgreSQL (best-effort), backend en arriere-plan, frontend.
# Ctrl+C arrete le frontend et le backend.
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"

"$ROOT/scripts/start-db.sh" || echo "[dev] DB non demarree automatiquement (voir ci-dessus)."

echo "[dev] Demarrage du backend..."
"$ROOT/scripts/start-backend.sh" &
BACK_PID=$!

cleanup() {
  echo "[dev] Arret du backend (pid $BACK_PID)..."
  kill "$BACK_PID" 2>/dev/null || true
}
trap cleanup EXIT INT TERM

echo "[dev] Demarrage du frontend..."
"$ROOT/scripts/start-frontend.sh"
