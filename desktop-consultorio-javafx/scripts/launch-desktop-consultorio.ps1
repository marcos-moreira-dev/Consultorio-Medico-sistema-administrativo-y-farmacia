param(
  [string]$Root,
  [string]$DesktopScript,
  [string]$LogFile
)

$ErrorActionPreference = "Stop"

try {
  if ([string]::IsNullOrWhiteSpace($Root) -or [string]::IsNullOrWhiteSpace($DesktopScript)) {
    throw "Faltan parámetros del desktop del consultorio."
  }
  Set-Location $Root
  if (-not [string]::IsNullOrWhiteSpace($LogFile)) {
    & $DesktopScript 2>&1 | Tee-Object -FilePath $LogFile -Append
  } else {
    & $DesktopScript
  }
  $exitCode = $LASTEXITCODE
  if ($exitCode -ne 0) {
    throw "El desktop del consultorio terminó con código $exitCode."
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
