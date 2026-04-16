@echo off
setlocal enabledelayedexpansion

set BASE_URL=%~1
if "%BASE_URL%"=="" set BASE_URL=http://localhost:8080

set LOGIN_URL=%BASE_URL%/api/v1/auth/login
set DOCS_URL=%BASE_URL%/api/v1/api-docs
set HEALTH_URL=%BASE_URL%/actuator/health

echo [smoke] Probando health: %HEALTH_URL%
curl --fail --silent --show-error "%HEALTH_URL%"
if errorlevel 1 (
  echo.
  echo [smoke] Fallo health.
  exit /b 1
)

echo.
echo [smoke] Probando api-docs: %DOCS_URL%
curl --fail --silent --show-error "%DOCS_URL%"
if errorlevel 1 (
  echo.
  echo [smoke] Fallo api-docs.
  exit /b 1
)

echo.
echo [smoke] Recordatorio: para login real usa POST sobre %LOGIN_URL%
echo [smoke] Smoke basico completado.
exit /b 0
