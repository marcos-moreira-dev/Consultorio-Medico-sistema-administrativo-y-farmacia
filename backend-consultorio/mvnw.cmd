@echo off
setlocal
set "MAVEN_CMD="

if exist "C:\apache-maven-3.9.14\bin\mvn.cmd" set "MAVEN_CMD=C:\apache-maven-3.9.14\bin\mvn.cmd"
if not defined MAVEN_CMD if defined MAVEN_HOME if exist "%MAVEN_HOME%\bin\mvn.cmd" set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"
if not defined MAVEN_CMD if defined M2_HOME if exist "%M2_HOME%\bin\mvn.cmd" set "MAVEN_CMD=%M2_HOME%\bin\mvn.cmd"
if not defined MAVEN_CMD for /f "delims=" %%F in ('dir /b /s "C:\apache-maven-*\bin\mvn.cmd" 2^>nul') do (
  if not defined MAVEN_CMD set "MAVEN_CMD=%%F"
)
if not defined MAVEN_CMD (
  where mvn >nul 2>&1
  if %ERRORLEVEL% EQU 0 set "MAVEN_CMD=mvn"
)

if not defined MAVEN_CMD (
  echo [backend-consultorio] No se encontro Maven en PATH ni en rutas conocidas.
  echo Define MAVEN_HOME o instala Maven en C:\apache-maven-3.9.14\bin\mvn.cmd
  exit /b 1
)

echo [backend-consultorio] Maven seleccionado: %MAVEN_CMD%
call "%MAVEN_CMD%" %*
exit /b %ERRORLEVEL%
