@echo off
title Consultorio Desktop
cd /d "%~dp0desktop-consultorio-javafx"
set "DB_URL=jdbc:postgresql://localhost:5433/consultorio_medico"
set "DB_USERNAME=postgres"
set "DB_PASSWORD=postgres"
if exist "mvnw.cmd" (
  mvnw.cmd javafx:run
) else (
  mvn javafx:run
)
pause
