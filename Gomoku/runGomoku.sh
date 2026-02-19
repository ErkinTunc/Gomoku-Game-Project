# runGomoku.sh
#!/bin/bash
set -euo pipefail

echo "Compiling..."

mkdir -p target/classes
find src/main/java -name "*.java" > target/sources.txt

javac -d target/classes @target/sources.txt

echo
echo "Running Gomoku..."
echo

java -cp target/classes app.Gomoku

read -p "Press enter to exit..."