:: runGomoku.bat
@echo off
setlocal

echo Compiling...

if not exist target\classes mkdir target\classes

dir /s /b src\main\java\*.java > target\sources.txt

javac -d target\classes @target\sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)

echo.
echo Running Gomoku...
echo.

java -cp target\classes app.Gomoku

pause