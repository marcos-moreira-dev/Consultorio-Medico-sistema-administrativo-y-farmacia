@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0.."

echo [backend-consultorio] Verificando Maven...
call mvnw.cmd -v
if errorlevel 1 (
  echo [backend-consultorio] No se pudo ejecutar Maven.
  exit /b 1
)

if defined DB_URL (
  echo [backend-consultorio] DB_URL=%DB_URL%
)
if defined DB_USERNAME (
  echo [backend-consultorio] DB_USERNAME=%DB_USERNAME%
)

echo [backend-consultorio] Iniciando backend con perfil dev...
set "SPRING_PROFILES_ACTIVE=dev"
if defined DB_URL set "SPRING_DATASOURCE_URL=%DB_URL%"
if defined DB_USERNAME set "SPRING_DATASOURCE_USERNAME=%DB_USERNAME%"
if defined DB_PASSWORD set "SPRING_DATASOURCE_PASSWORD=%DB_PASSWORD%"
set "MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info"
call mvnw.cmd clean -Dmaven.test.skip=true -DskipTests=true spring-boot:run
exit /b %ERRORLEVEL%
