@echo off
setlocal EnableExtensions
set "ROOT=%~dp0"
set "ACTION=%~1"
if "%ACTION%"=="" set "ACTION=up"
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%scripts\run-consultorio-launcher.ps1" -Action "%ACTION%"
exit /b %ERRORLEVEL%
