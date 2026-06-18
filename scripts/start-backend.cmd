@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0..\backend"

rem Detecte un JDK 25 (requis par la toolchain Gradle) si JAVA_HOME ne l'est pas deja.
if not defined JAVA_HOME goto :detect
"%JAVA_HOME%\bin\java" -version 2>&1 | findstr /c:"version \"25" >nul && goto :run

:detect
for /d %%d in ("%USERPROFILE%\.jdks\*25*") do set "JAVA_HOME=%%d"
if not defined JAVA_HOME for /d %%d in ("%ProgramFiles%\Eclipse Adoptium\jdk-25*") do set "JAVA_HOME=%%d"
if not defined JAVA_HOME for /d %%d in ("%ProgramFiles%\Java\jdk-25*") do set "JAVA_HOME=%%d"

:run
echo [start-backend] JAVA_HOME=%JAVA_HOME%
call gradlew.bat bootRun
endlocal
