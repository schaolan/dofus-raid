@echo off
cd /d "%~dp0..\frontend"
if not exist node_modules (
  echo [start-frontend] npm install...
  call npm install
)
call npm run dev
