@echo off
cd /d "%~dp0.."
where docker >nul 2>nul
if %errorlevel%==0 (
  docker compose up -d
) else (
  echo [start-db] Docker introuvable.
  echo [start-db] Demarrez un PostgreSQL 16 sur localhost:5432 ^(db=dofusraid, user=dofus, password=dofus^).
  exit /b 1
)
