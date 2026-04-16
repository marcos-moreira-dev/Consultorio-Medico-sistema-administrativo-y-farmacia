$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot/..

if (-not (Test-Path 'node_modules')) {
    Write-Host 'No se encontro node_modules. Ejecuta npm install primero.' -ForegroundColor Yellow
    exit 1
}

npm run start
