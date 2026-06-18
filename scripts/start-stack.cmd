@echo off
cd /d "%~dp0.."
where docker >nul 2>nul
if not %errorlevel%==0 (
  echo [start-stack] Docker introuvable. Installez Docker Desktop puis relancez.
  exit /b 1
)
docker compose --profile full up -d --build
echo [start-stack] Frontend : http://localhost:5173
echo [start-stack] Backend  : http://localhost:8080
