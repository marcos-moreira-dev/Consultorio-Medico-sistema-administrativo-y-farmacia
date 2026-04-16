@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "ROOT=%~dp0"
set "COMPOSE_FILE=%ROOT%infra\compose\docker-compose.local.yml"
set "ACTION=%~1"
if "%ACTION%"=="" set "ACTION=up"

set "BACKEND_URL=http://localhost:3001/api/v1/catalogo"
set "SWAGGER_URL=http://localhost:3001/docs"
set "FRONTEND_URL=http://localhost:4200"
set "CONSULTORIO_BACKEND=http://localhost:8080"
set "OPEN_BROWSER=%OPEN_BROWSER%"
if "%OPEN_BROWSER%"=="" set "OPEN_BROWSER=1"
set "LAUNCH_DESKTOP=%LAUNCH_DESKTOP%"
if "%LAUNCH_DESKTOP%"=="" set "LAUNCH_DESKTOP=1"
set "DIAG_DIR=%ROOT%.diagnostics"
set "DIAG_FILE=%DIAG_DIR%\farmacia-last-run.log"

call :banner
call :checkPrerequisites || exit /b 1

if /I "%ACTION%"=="up" goto :up
if /I "%ACTION%"=="rebuild" goto :rebuild
if /I "%ACTION%"=="down" goto :down
if /I "%ACTION%"=="logs" goto :logs
if /I "%ACTION%"=="ps" goto :ps
if /I "%ACTION%"=="doctor" goto :doctor
if /I "%ACTION%"=="report" goto :report
if /I "%ACTION%"=="resetdb" goto :resetdb
if /I "%ACTION%"=="help" goto :help

echo [ERROR] Accion no reconocida: %ACTION%
echo.
goto :help

:banner
echo ==========================================================
echo consultorio-medico :: launcher local de farmacia + desktop
echo ==========================================================
echo Compose: %COMPOSE_FILE%
echo Backend : %BACKEND_URL%
echo Swagger : %SWAGGER_URL%
echo Frontend: %FRONTEND_URL%
echo Desktop : Consultorio JavaFX (si LAUNCH_DESKTOP=1)
echo Alias recomendado: run_farmacia.bat
echo.
exit /b 0

:checkPrerequisites
if not exist "%COMPOSE_FILE%" (
  echo [ERROR] No existe el compose local esperado.
  exit /b 1
)

docker version >nul 2>nul
if errorlevel 1 (
  echo [ERROR] Docker no responde. Abre Docker Desktop o verifica la instalacion.
  exit /b 1
)

docker compose version >nul 2>nul
if errorlevel 1 (
  echo [ERROR] Tu instalacion no expone "docker compose".
  exit /b 1
)

docker compose -f "%COMPOSE_FILE%" config >nul
if errorlevel 1 (
  echo [ERROR] El archivo compose no paso la validacion de Docker Compose.
  exit /b 1
)
exit /b 0

:doctor
echo [INFO] Validando compose y estado actual...
docker compose -f "%COMPOSE_FILE%" config || exit /b 1
echo.
docker compose -f "%COMPOSE_FILE%" ps
call :writeReport "DOCTOR" >nul 2>nul
echo [INFO] Reporte actualizado en: %DIAG_FILE%
exit /b %ERRORLEVEL%

:report
call :writeReport "MANUAL" || exit /b 1
echo [INFO] Reporte actualizado en: %DIAG_FILE%
exit /b 0

:up
echo [INFO] Levantando stack local en segundo plano con build...
echo [INFO] Usa run_all.bat logs para seguir los logs en vivo despues del arranque.
docker compose -f "%COMPOSE_FILE%" up --build --remove-orphans -d
if errorlevel 1 goto :diagnostics
call :postStartChecks || goto :diagnostics
exit /b 0

:rebuild
echo [INFO] Rebuild forzado del stack local en segundo plano...
docker compose -f "%COMPOSE_FILE%" up --build --force-recreate --remove-orphans -d
if errorlevel 1 goto :diagnostics
call :postStartChecks || goto :diagnostics
exit /b 0

:down
echo [INFO] Bajando stack local...
docker compose -f "%COMPOSE_FILE%" down --remove-orphans
exit /b %ERRORLEVEL%

:logs
echo [INFO] Adjuntando logs del stack local...
docker compose -f "%COMPOSE_FILE%" logs --follow --tail=200 --timestamps
exit /b %ERRORLEVEL%

:ps
echo [INFO] Estado actual de contenedores...
docker compose -f "%COMPOSE_FILE%" ps
exit /b %ERRORLEVEL%

:resetdb
echo [WARN] Esto elimina volumenes del stack local, incluida la BD de farmacia.
choice /M "Continuar"
if errorlevel 2 exit /b 0

docker compose -f "%COMPOSE_FILE%" down --volumes --remove-orphans
if errorlevel 1 (
  echo [ERROR] Fallo al bajar y limpiar el stack.
  exit /b 1
)

echo [INFO] Volumenes eliminados. Levantando stack limpio...
docker compose -f "%COMPOSE_FILE%" up --build --remove-orphans -d
if errorlevel 1 goto :diagnostics
call :postStartChecks || goto :diagnostics
exit /b 0

:postStartChecks
echo.
echo [INFO] Esperando salud de contenedores principales...
call :waitForService "consultorio-medico-postgres-farmacia" healthy 60 || exit /b 1
call :waitForService "consultorio-medico-backend-farmacia" healthy 120 || exit /b 1
call :waitForService "consultorio-medico-storefront-farmacia" healthy 150 || exit /b 1
call :printStatusSummary
if /I "%LAUNCH_DESKTOP%"=="1" (
  call :launchDesktop
)
if /I "%OPEN_BROWSER%"=="1" (
  call :openUrl "%SWAGGER_URL%"
  call :openUrl "%FRONTEND_URL%"
)
exit /b 0

:waitForService
set "SERVICE_NAME=%~1"
set "TARGET_STATUS=%~2"
set /a MAX_ATTEMPTS=%~3 / 2
if %MAX_ATTEMPTS% lss 1 set /a MAX_ATTEMPTS=1
set /a ATTEMPT=0

:waitLoop
set /a ATTEMPT+=1
set "CURRENT_STATUS=unknown"
for /f "usebackq delims=" %%S in (`docker inspect -f "{{if .State.Health}}{{.State.Health.Status}}{{else}}{{.State.Status}}{{end}}" "%SERVICE_NAME%" 2^>nul`) do set "CURRENT_STATUS=%%S"

if /I "!CURRENT_STATUS!"=="%TARGET_STATUS%" (
  echo [OK] %SERVICE_NAME% -> !CURRENT_STATUS!
  exit /b 0
)

if /I "!CURRENT_STATUS!"=="exited" (
  echo [FAIL] %SERVICE_NAME% -> !CURRENT_STATUS!
  exit /b 1
)

if /I "!CURRENT_STATUS!"=="dead" (
  echo [FAIL] %SERVICE_NAME% -> !CURRENT_STATUS!
  exit /b 1
)

if !ATTEMPT! geq %MAX_ATTEMPTS% (
  echo [FAIL] %SERVICE_NAME% no alcanzo estado %TARGET_STATUS%. Estado actual: !CURRENT_STATUS!
  exit /b 1
)

echo [WAIT] %SERVICE_NAME% -> !CURRENT_STATUS!
timeout /t 2 /nobreak >nul
goto :waitLoop

:printStatusSummary
echo.
echo ==========================================================
echo Estado final del stack local de farmacia
echo ==========================================================
docker compose -f "%COMPOSE_FILE%" ps
echo.
echo [OK] PostgreSQL farmacia inicializado
echo [OK] Backend farmacia disponible en %BACKEND_URL%
echo [OK] Swagger disponible en %SWAGGER_URL%
echo [OK] Storefront disponible en %FRONTEND_URL%
if /I "%LAUNCH_DESKTOP%"=="1" (
  echo [OK] Desktop consultorio JavaFX lanzado en ventana separada
)
echo.
echo Usa run_all.bat logs para ver logs en vivo.
call :writeReport "SUCCESS" >nul 2>nul
echo [INFO] Reporte actualizado en: %DIAG_FILE%
exit /b 0

:openUrl
start "" "%~1" >nul 2>nul
exit /b 0

:launchDesktop
echo [INFO] Lanzando desktop del consultorio en ventana separada...
cmd /c start "Consultorio Desktop" /D "%ROOT%" "%ROOT%launch_desktop.bat"
exit /b 0

:diagnostics
echo.
echo [ERROR] El stack fallo al arrancar. Mostrando diagnostico rapido...
echo.
echo ---------------- docker compose ps ----------------
docker compose -f "%COMPOSE_FILE%" ps
echo.
echo ---------------- ultimos logs ----------------
docker compose -f "%COMPOSE_FILE%" logs --tail=250 --timestamps
echo.
echo [SUGERENCIAS]
echo - Verifica que Docker Desktop este corriendo.
echo - Si cambiaste dependencias, usa: run_farmacia.bat rebuild
echo - Si la base quedo sucia, usa: run_farmacia.bat resetdb
echo - Si el puerto 3001, 4200 o 5432 ya esta ocupado, libera el puerto primero.
echo - Para evitar el desktop usa: set LAUNCH_DESKTOP=0
call :writeReport "FAILURE" >nul 2>nul
echo - Reporte guardado en: %DIAG_FILE%
exit /b 1

:writeReport
set "REPORT_MODE=%~1"
if not exist "%DIAG_DIR%" mkdir "%DIAG_DIR%" >nul 2>nul
(
  echo ==========================================================
  echo consultorio-medico :: reporte diagnostico farmacia
  echo ==========================================================
  echo Modo: %REPORT_MODE%
  echo Fecha: %DATE% %TIME%
  echo Compose: %COMPOSE_FILE%
  echo.
  echo ---------------- docker compose ps ----------------
  docker compose -f "%COMPOSE_FILE%" ps
  echo.
  echo ---------------- estado de salud ----------------
  call :appendHealthLine "consultorio-medico-postgres-farmacia"
  call :appendHealthLine "consultorio-medico-backend-farmacia"
  call :appendHealthLine "consultorio-medico-storefront-farmacia"
  echo.
  echo ---------------- ultimos logs ----------------
  docker compose -f "%COMPOSE_FILE%" logs --tail=250 --timestamps
) > "%DIAG_FILE%"
exit /b 0

:appendHealthLine
set "TARGET_CONTAINER=%~1"
set "TARGET_CONTAINER_STATUS=no-encontrado"
for /f "usebackq delims=" %%S in (`docker inspect -f "{{if .State.Health}}{{.State.Health.Status}}{{else}}{{.State.Status}}{{end}}" "%TARGET_CONTAINER%" 2^>nul`) do set "TARGET_CONTAINER_STATUS=%%S"
echo %TARGET_CONTAINER% ^> !TARGET_CONTAINER_STATUS!
exit /b 0

:help
echo Uso:
echo   run_all.bat up       ^(default^)  - levanta el stack en segundo plano y valida salud
echo   run_all.bat rebuild             - rebuild forzado del stack
echo   run_all.bat down                - baja el stack
echo   run_all.bat logs                - sigue logs del stack
echo   run_all.bat ps                  - muestra estado de contenedores
echo   run_all.bat doctor              - valida compose, muestra estado y actualiza reporte
echo   run_all.bat report              - genera reporte diagnostico en .diagnostics\farmacia-last-run.log
echo   run_all.bat resetdb             - borra volumenes y recrea la BD local
echo   run_all.bat help                - muestra esta ayuda
echo.
echo Variables opcionales:
echo   set OPEN_BROWSER=0              - evita abrir Swagger y storefront tras un arranque sano
echo   set LAUNCH_DESKTOP=0            - evita lanzar el desktop JavaFX del consultorio
exit /b 0
