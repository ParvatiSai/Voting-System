# PowerShell script to only compile the Voting System
# Usage: .\compile.ps1

Write-Host "Compiling Java sources..." -ForegroundColor Green

$files = Get-ChildItem -Path .\src\main\java -Recurse -Filter *.java | ForEach-Object { $_.FullName }

if (-not (Test-Path out)) {
    New-Item -ItemType Directory -Path out | Out-Null
}

& javac -d out $files

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilation successful!" -ForegroundColor Green
} else {
    Write-Host "✗ Compilation failed!" -ForegroundColor Red
    exit 1
}
