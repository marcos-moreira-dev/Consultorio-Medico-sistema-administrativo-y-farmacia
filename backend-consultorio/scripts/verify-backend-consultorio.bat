@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0.."

echo [backend-consultorio] Verificando Maven...
call mvnw.cmd -v
if errorlevel 1 exit /b 1

echo [backend-consultorio] Ejecutando compilacion...
call mvnw.cmd -DskipTests compile
if errorlevel 1 exit /b 1

echo [backend-consultorio] Ejecutando tests...
call mvnw.cmd test
exit /b %ERRORLEVEL%
