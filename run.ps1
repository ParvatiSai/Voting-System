# PowerShell script to compile and run the Voting System
# Usage: .\run.ps1

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  College Voting System - Runner" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if MySQL connector exists
$mysqlConnector = "mysql-connector-j-9.4.0.jar"
if (-not (Test-Path $mysqlConnector)) {
    Write-Host "Warning: MySQL Connector not found: $mysqlConnector" -ForegroundColor Yellow
    Write-Host "The application may fail to connect to the database." -ForegroundColor Yellow
    Write-Host ""
}

# Compile
Write-Host "Step 1: Compiling Java sources..." -ForegroundColor Green
$files = Get-ChildItem -Path .\src\main\java -Recurse -Filter *.java `
    | Where-Object { $_.Name -ne 'tempCodeRunnerFile.java' } `
    | ForEach-Object { $_.FullName }

if (-not (Test-Path out)) {
    New-Item -ItemType Directory -Path out | Out-Null
}

& javac -d out $files

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful!" -ForegroundColor Green
    Write-Host ""
    
    # Run
    Write-Host "Step 2: Starting application..." -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
    
    if (Test-Path $mysqlConnector) {
        java -cp "out;resources;$mysqlConnector" app.Main
    } else {
        Write-Host "Attempting to run without MySQL connector..." -ForegroundColor Yellow
        java -cp "out;resources" app.Main
    }
} else {
    Write-Host "Compilation failed! Please check the errors above." -ForegroundColor Red
    exit 1
}
