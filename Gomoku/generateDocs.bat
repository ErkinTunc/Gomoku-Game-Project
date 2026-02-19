:: generateDocs.bat
@echo off
setlocal

echo Generating Javadoc with private methods...

if not exist target mkdir target
dir /s /b src\main\java\*.java > target\sources.txt

if exist doc rmdir /s /q doc
mkdir doc

javadoc -d doc -private @target\sources.txt
if %errorlevel% neq 0 (
    echo Failed to generate Javadoc.
    pause
    exit /b %errorlevel%
)

echo Javadoc generated in the 'doc' folder.
start doc\index.html

pause