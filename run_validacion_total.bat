@echo off
setlocal EnableExtensions EnableDelayedExpansion
cd /d "%~dp0"
if not exist ".diagnostics" mkdir ".diagnostics"
set "REPORT=%cd%\.diagnostics\validacion-total.log"
if not "%~1"=="" set "REPORT=%~1"
for %%I in ("%REPORT%") do set "REPORT=%%~fI"
if not exist "%~dp0.diagnostics" mkdir "%~dp0.diagnostics"

> "%REPORT%" echo ==========================================================
>> "%REPORT%" echo consultorio-medico :: validacion integral
>> "%REPORT%" echo ==========================================================
>> "%REPORT%" echo Fecha: %date% %time%
>> "%REPORT%" echo Raiz : %cd%

call :section "Entorno base"
call :run_cmd "docker --version" "docker --version"
call :run_cmd "docker compose version" "docker compose version"
call :run_cmd "node -v" "node -v"
call :run_cmd "npm -v" "npm -v"

call :section "Farmacia :: servicios"
call :run_cmd "docker compose farmacia ps" "docker compose -f infra\compose\docker-compose.local.yml ps"
call :run_cmd "curl health farmacia" "curl --max-time 6 --silent --show-error http://localhost:3001/api/v1/health"
call :run_cmd "curl storefront farmacia" "curl --max-time 6 --silent --show-error http://localhost:4200"

call :section "Farmacia :: backend"
pushd backend-farmacia >nul
call :run_cmd "npm test" "npm test"
popd >nul

call :section "Farmacia :: storefront"
pushd storefront-farmacia-angular >nul
call :run_cmd "npm run verify" "npm run verify"
popd >nul

call :section "Consultorio :: servicios"
call :run_cmd "docker compose consultorio ps" "docker compose -f infra\compose\docker-compose.consultorio-local.yml ps"
call :run_cmd "curl health consultorio" "curl --max-time 6 --silent --show-error http://localhost:8080/actuator/health"

call :section "Consultorio :: backend"
pushd backend-consultorio >nul
call :run_cmd "mvnw.cmd -v" "mvnw.cmd -v"
call :run_cmd "mvnw.cmd -DskipTests compile" "mvnw.cmd -DskipTests compile"
call :run_cmd "mvnw.cmd test" "mvnw.cmd test"
popd >nul

call :section "Consultorio :: desktop"
pushd desktop-consultorio-javafx >nul
call :run_cmd "mvnw.cmd -v" "mvnw.cmd -v"
call :run_cmd "mvnw.cmd -DskipTests compile" "mvnw.cmd -DskipTests compile"
call :run_cmd "mvnw.cmd test" "mvnw.cmd test"
popd >nul

>> "%REPORT%" echo.
>> "%REPORT%" echo Archivo generado: %REPORT%
type "%REPORT%"
exit /b 0

:section
>> "%REPORT%" echo.
>> "%REPORT%" echo ----------------------------------------------------------
>> "%REPORT%" echo %~1
>> "%REPORT%" echo ----------------------------------------------------------
exit /b 0

:run_cmd
set "LABEL=%~1"
set "COMMAND=%~2"
>> "%REPORT%" echo.
>> "%REPORT%" echo [CMD] %LABEL%
cmd /c %COMMAND% >> "%REPORT%" 2>&1
>> "%REPORT%" echo [EXIT] !ERRORLEVEL!
exit /b 0
