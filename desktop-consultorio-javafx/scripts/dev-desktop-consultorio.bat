@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0.."

echo [desktop-consultorio] Verificando Maven...
call mvnw.cmd -v
if errorlevel 1 (
  echo [desktop-consultorio] No se pudo ejecutar Maven.
  exit /b 1
)

echo [desktop-consultorio] Iniciando desktop JavaFX...
call mvnw.cmd javafx:run
exit /b %ERRORLEVEL%
