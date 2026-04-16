@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0.."

echo [doctor] Java actual:
java -version
if errorlevel 1 (
  echo [doctor] Java no esta disponible en PATH.
  exit /b 1
)

echo [doctor] Maven actual:
call mvnw.cmd -v
if errorlevel 1 (
  echo [doctor] Maven/wrapper no pudo ejecutarse.
  exit /b 1
)

echo [doctor] Recordatorio de toolchains:
echo   Copia scripts\toolchains-temurin-21.example.xml a %%USERPROFILE%%\.m2\toolchains.xml
echo   y ajusta la ruta jdkHome a tu Temurin 21 real.

echo [doctor] Variables relevantes:
echo   DB_URL=%DB_URL%
echo   DB_USERNAME=%DB_USERNAME%
echo   SERVER_PORT=%SERVER_PORT%

exit /b 0
