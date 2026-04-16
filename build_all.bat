@echo off
setlocal EnableExtensions EnableDelayedExpansion

:: ============================================================
:: build_all.bat
::
:: Compila TODOS los componentes del proyecto en orden correcto:
::   1. backend-farmacia (NestJS)
::   2. storefront-farmacia-angular (Angular)
::   3. backend-consultorio (Spring Boot)
::   4. desktop-consultorio-javafx (JavaFX)
::
:: Uso:
::   build_all.bat          - compila todo
::   build_all.bat farmacia - solo compila farmacia (backend + storefront)
::   build_all.bat consultorio - solo compila consultorio (backend + desktop)
:: ============================================================

set "ROOT=%~dp0"
set "ACTION=%~1"
set "EXIT_CODE=0"

echo ============================================================
echo consultorio-medico :: compilacion integral
echo ============================================================
echo Raiz: %ROOT%
echo Fecha: %date% %time%
echo.

:: ============================================================
:: Farmacia: Backend NestJS
:: ============================================================
if "%ACTION%"=="" if /I not "%ACTION%"=="consultorio" (
    call :buildFarmaciaBackend
    if !EXIT_CODE! neq 0 goto :summary
)
if /I "%ACTION%"=="farmacia" (
    call :buildFarmaciaBackend
    if !EXIT_CODE! neq 0 goto :summary
    call :buildStorefront
    if !EXIT_CODE! neq 0 goto :summary
    goto :summary
)

:: ============================================================
:: Farmacia: Storefront Angular
:: ============================================================
if "%ACTION%"=="" (
    call :buildStorefront
    if !EXIT_CODE! neq 0 goto :summary
)

:: ============================================================
:: Consultorio: Backend Spring Boot
:: ============================================================
if "%ACTION%"=="" if /I not "%ACTION%"=="farmacia" (
    call :buildConsultorioBackend
    if !EXIT_CODE! neq 0 goto :summary
)
if /I "%ACTION%"=="consultorio" (
    call :buildConsultorioBackend
    if !EXIT_CODE! neq 0 goto :summary
    call :buildConsultorioDesktop
    if !EXIT_CODE! neq 0 goto :summary
    goto :summary
)

:: ============================================================
:: Consultorio: Desktop JavaFX
:: ============================================================
if "%ACTION%"=="" (
    call :buildConsultorioDesktop
    if !EXIT_CODE! neq 0 goto :summary
)

:: ============================================================
goto :summary

:: ============================================================
:buildFarmaciaBackend
echo.
echo -----------------------------------------------------------
echo [1/4] backend-farmacia :: install + build + typecheck
echo -----------------------------------------------------------
pushd "%ROOT%backend-farmacia" >nul

:: Instalar dependencias si node_modules o el paquete clave no existen
if not exist "node_modules\@nestjs\core" (
    echo [INFO] Instalando dependencias de backend-farmacia...
    call npm install
    if errorlevel 1 (
        echo [ERROR] backend-farmacia: fallo en npm install
        popd >nul
        set "EXIT_CODE=1"
        exit /b 1
    )
)

call npm run build
if errorlevel 1 (
    echo [ERROR] backend-farmacia: fallo en build
    popd >nul
    set "EXIT_CODE=1"
    exit /b 1
)
call npm run typecheck
if errorlevel 1 (
    echo [ERROR] backend-farmacia: fallo en typecheck
    popd >nul
    set "EXIT_CODE=1"
    exit /b 1
)
echo [OK] backend-farmacia compilado correctamente
popd >nul
exit /b 0

:: ============================================================
:buildStorefront
echo.
echo -----------------------------------------------------------
echo [2/4] storefront-farmacia-angular :: install + build
echo -----------------------------------------------------------
pushd "%ROOT%storefront-farmacia-angular" >nul

:: Instalar dependencias si node_modules o el paquete clave no existen
if not exist "node_modules\@angular-devkit\build-angular" (
    echo [INFO] Instalando dependencias de storefront-farmacia-angular...
    call npm install
    if errorlevel 1 (
        echo [ERROR] storefront-farmacia-angular: fallo en npm install
        popd >nul
        set "EXIT_CODE=1"
        exit /b 1
    )
)

call npm run build
if errorlevel 1 (
    echo [ERROR] storefront-farmacia-angular: fallo en build
    popd >nul
    set "EXIT_CODE=1"
    exit /b 1
)
echo [OK] storefront-farmacia-angular compilado correctamente
popd >nul
exit /b 0

:: ============================================================
:buildConsultorioBackend
echo.
echo -----------------------------------------------------------
echo [3/4] backend-consultorio :: compile
echo -----------------------------------------------------------
pushd "%ROOT%backend-consultorio" >nul
call mvnw.cmd -q -DskipTests compile
if errorlevel 1 (
    echo [ERROR] backend-consultorio: fallo en compile
    popd >nul
    set "EXIT_CODE=1"
    exit /b 1
)
echo [OK] backend-consultorio compilado correctamente
popd >nul
exit /b 0

:: ============================================================
:buildConsultorioDesktop
echo.
echo -----------------------------------------------------------
echo [4/4] desktop-consultorio-javafx :: compile
echo -----------------------------------------------------------
pushd "%ROOT%desktop-consultorio-javafx" >nul
call mvnw.cmd -q -DskipTests compile
if errorlevel 1 (
    echo [ERROR] desktop-consultorio-javafx: fallo en compile
    popd >nul
    set "EXIT_CODE=1"
    exit /b 1
)
echo [OK] desktop-consultorio-javafx compilado correctamente
popd >nul
exit /b 0

:: ============================================================
:summary
echo.
echo ============================================================
if %EXIT_CODE% equ 0 (
    echo [OK] Compilacion completada sin errores.
) else (
    echo [ERROR] La compilacion fallo. Revisa los mensajes arriba.
)
echo ============================================================
echo Fecha final: %date% %time%
exit /b %EXIT_CODE%
