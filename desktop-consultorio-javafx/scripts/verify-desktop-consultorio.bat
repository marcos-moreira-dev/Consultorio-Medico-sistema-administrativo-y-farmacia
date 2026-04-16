@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0.."
call mvnw.cmd -v
if errorlevel 1 exit /b 1
call mvnw.cmd -DskipTests compile
if errorlevel 1 exit /b 1
call mvnw.cmd test
exit /b %ERRORLEVEL%
