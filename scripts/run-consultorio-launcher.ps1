param(
  [string]$Action = 'up'
)

$ErrorActionPreference = 'Stop'

$Root = Split-Path -Parent $PSScriptRoot
$BackendScript = Join-Path $Root 'backend-consultorio\scripts\dev-backend-consultorio.bat'
$DesktopScript = Join-Path $Root 'desktop-consultorio-javafx\scripts\dev-desktop-consultorio.bat'
$ComposeFile = Join-Path $Root 'infra\compose\docker-compose.consultorio-local.yml'
$BackendUrl = 'http://localhost:8080/api/v1'
$HealthUrl = 'http://localhost:8080/actuator/health'
$DbUrl = 'jdbc:postgresql://localhost:5433/consultorio_medico'
$DbUsername = 'postgres'
$DbPassword = 'postgres'
$DiagDir = Join-Path $Root '.diagnostics'
$ReportFile = Join-Path $DiagDir 'consultorio-last-run.log'
$BackendLogFile = Join-Path $DiagDir 'consultorio-backend-console.log'
$DesktopLogFile = Join-Path $DiagDir 'consultorio-desktop-console.log'
$BackendBootstrap = Join-Path $Root 'backend-consultorio\scripts\launch-backend-consultorio.ps1'
$DesktopBootstrap = Join-Path $Root 'desktop-consultorio-javafx\scripts\launch-desktop-consultorio.ps1'

function Banner {
  Write-Host '=========================================================='
  Write-Host 'consultorio-medico :: launcher local de consultorio'
  Write-Host '=========================================================='
  Write-Host "Backend script: $BackendScript"
  Write-Host "Desktop script: $DesktopScript"
  Write-Host "Compose DB    : $ComposeFile"
  Write-Host "Backend URL   : $BackendUrl"
  Write-Host ''
}

function Ensure-Diagnostics {
  if (-not (Test-Path $DiagDir)) { New-Item -ItemType Directory -Path $DiagDir | Out-Null }
}

function Ensure-Db {
  Write-Host '[INFO] Levantando PostgreSQL local del consultorio en Docker...'
  & docker compose -f $ComposeFile up -d | Out-Host
  if ($LASTEXITCODE -ne 0) { throw 'No se pudo levantar la BD local del consultorio.' }
}

function Wait-Backend([int]$Seconds = 180) {
  Write-Host '[INFO] Esperando disponibilidad del backend del consultorio...'
  $deadline = (Get-Date).AddSeconds($Seconds)
  do {
    try {
      $r = Invoke-WebRequest -Uri $HealthUrl -UseBasicParsing -TimeoutSec 3
      if ($r.StatusCode -ge 200 -and $r.StatusCode -lt 500) { return $true }
    } catch {}
    Start-Sleep -Seconds 2
  } while ((Get-Date) -lt $deadline)
  return $false
}

function Write-Report([string]$Mode) {
  Ensure-Diagnostics
  $lines = @(
    '==========================================================',
    'consultorio-medico :: reporte diagnostico consultorio',
    '==========================================================',
    "Modo: $Mode",
    "Fecha: $(Get-Date)",
    "Backend URL: $BackendUrl",
    "Health URL : $HealthUrl",
    "DB URL     : $DbUrl",
    '',
    '---------------- estado BD consultorio ----------------'
  )
  Set-Content -Path $ReportFile -Value $lines -Encoding UTF8
  & docker compose -f $ComposeFile ps | Add-Content -Path $ReportFile
  Add-Content -Path $ReportFile -Value @('', '---------------- chequeo backend ----------------')
  try {
    $r = Invoke-WebRequest -Uri $HealthUrl -UseBasicParsing -TimeoutSec 4
    Add-Content -Path $ReportFile -Value "status=$($r.StatusCode)"
  } catch {
    Add-Content -Path $ReportFile -Value 'status=DOWN'
    Add-Content -Path $ReportFile -Value $_.Exception.Message
  }
  Add-Content -Path $ReportFile -Value @('', '---------------- logs utiles ----------------', "backend_log=$BackendLogFile", "desktop_log=$DesktopLogFile")
}

function Start-BootstrapWindow([string]$Title, [string]$ScriptPath, [hashtable]$ArgsMap) {
  if (-not (Test-Path $ScriptPath)) { throw "No existe el script bootstrap: $ScriptPath" }
  $argList = @('-NoExit', '-NoProfile', '-ExecutionPolicy', 'Bypass', '-File', $ScriptPath)
  foreach ($entry in $ArgsMap.GetEnumerator()) {
    $argList += @('-' + $entry.Key, [string]$entry.Value)
  }
  Start-Process -FilePath 'powershell.exe' -ArgumentList $argList -WorkingDirectory $Root | Out-Null
}

function Launch-Backend {

  Write-Host '[INFO] Abriendo backend del consultorio en ventana separada...'
  Start-BootstrapWindow 'consultorio-backend' $BackendBootstrap @{
    Root = $Root
    BackendScript = $BackendScript
    LogFile = $BackendLogFile
    DbUrl = $DbUrl
    DbUsername = $DbUsername
    DbPassword = $DbPassword
  }
}

function Launch-Desktop {
  Write-Host '[INFO] Abriendo desktop del consultorio en ventana separada...'
  Start-BootstrapWindow 'consultorio-desktop' $DesktopBootstrap @{
    Root = $Root
    DesktopScript = $DesktopScript
    LogFile = $DesktopLogFile
  }
}

Banner
Ensure-Diagnostics

switch ($Action.ToLowerInvariant()) {
  'db' {
    Ensure-Db
    Write-Host '[OK] Base local del consultorio lista en localhost:5433'
    Write-Report 'DB'
  }
  'backend' {
    Ensure-Db
    Launch-Backend
    if (Wait-Backend 240) { Write-Host "[OK] Backend consultorio disponible en $BackendUrl" } else { Write-Host '[WARN] El backend no confirmo disponibilidad a tiempo.' }
    Write-Report 'BACKEND'
  }
  'desktop' {
    Launch-Desktop
    Write-Report 'DESKTOP'
  }
  'doctor' {
    Ensure-Db
    Write-Report 'MANUAL'
    Write-Host "[INFO] Reporte actualizado en: $ReportFile"
  }
  'report' {
    if (Test-Path $ReportFile) { Get-Content -Path $ReportFile } else { Write-Host '[WARN] Aun no existe un reporte de consultorio.' }
  }
  'help' {
    Write-Host 'Uso:'
    Write-Host '  run_consultorio_all.bat up       (default)  - levanta BD local, backend y desktop'
    Write-Host '  run_consultorio_all.bat db                  - levanta solo PostgreSQL local del consultorio'
    Write-Host '  run_consultorio_all.bat backend             - levanta BD local y luego solo el backend'
    Write-Host '  run_consultorio_all.bat desktop             - abre solo JavaFX'
    Write-Host '  run_consultorio_all.bat doctor              - verifica salud basica'
    Write-Host '  run_consultorio_all.bat report              - muestra el ultimo reporte guardado'
  }
  default {
    Ensure-Db
    Launch-Backend
    Launch-Desktop
    if (Wait-Backend 90) { Write-Host "[OK] Backend consultorio disponible en $BackendUrl" } else { Write-Host '[WARN] El backend del consultorio sigue sin confirmar salud. El desktop igual se abrió en paralelo.' }
    Write-Report 'UP'
    Write-Host '[OK] Se solicitaron BD local, backend y desktop del consultorio.'
    Write-Host "[INFO] Reporte actualizado en: $ReportFile"
  }
}
