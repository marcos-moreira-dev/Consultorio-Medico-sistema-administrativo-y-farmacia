param(
  [string]$Root,
  [string]$BackendScript,
  [string]$LogFile,
  [string]$DbUrl,
  [string]$DbUsername,
  [string]$DbPassword
)

$ErrorActionPreference = "Stop"

try {
  if ([string]::IsNullOrWhiteSpace($Root) -or [string]::IsNullOrWhiteSpace($BackendScript)) {
    throw "Faltan parámetros del launcher del consultorio."
  }

  if (-not [string]::IsNullOrWhiteSpace($DbUrl)) { $env:DB_URL = $DbUrl }
  if (-not [string]::IsNullOrWhiteSpace($DbUsername)) { $env:DB_USERNAME = $DbUsername }
  if (-not [string]::IsNullOrWhiteSpace($DbPassword)) { $env:DB_PASSWORD = $DbPassword }

  Set-Location $Root
  if (-not [string]::IsNullOrWhiteSpace($LogFile)) {
    & $BackendScript 2>&1 | Tee-Object -FilePath $LogFile -Append
  } else {
    & $BackendScript
  }

  $exitCode = $LASTEXITCODE
  if ($exitCode -ne 0) {
    throw "El backend del consultorio terminó con código $exitCode."
  }
} catch {
  $msg = $_ | Out-String
  Write-Host $msg -ForegroundColor Red
  if (-not [string]::IsNullOrWhiteSpace($LogFile)) {
    Add-Content -Path $LogFile -Value ("`n[launcher-error] " + $msg)
  }
  Write-Host "El proceso terminó con error. Revisa el log generado y presiona Enter para cerrar." -ForegroundColor Red
  Read-Host | Out-Null
  exit 1
}
