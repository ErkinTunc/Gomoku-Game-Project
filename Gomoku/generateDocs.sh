#!/bin/bash
set -euo pipefail

echo "Generating Javadoc with private methods..."

mkdir -p target
find src/main/java -name "*.java" > target/sources.txt

rm -rf doc
mkdir -p doc

javadoc -d doc -private @target/sources.txt

echo "Javadoc generated in the 'doc' folder."

if command -v xdg-open &> /dev/null; then
  xdg-open doc/index.html
elif command -v open &> /dev/null; then
  open doc/index.html
else
  echo "Open doc/index.html manually in your browser."
fi

read -p "Press enter to exit..."