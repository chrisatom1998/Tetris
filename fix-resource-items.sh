#!/bin/bash

# Script to fix problematic XML resource items
echo "Fixing problematic XML resource files..."

# Fix empty item elements to use proper XML format
find app/src/main/res app/src/main/res-safe -type f -name "*.xml" -exec sed -i '' 's/<item\s\+\(name="[^"]*"\)\s\+type="id">[^<]*<\/item>/<item \1 type="id"><\/item>/g' {} \;
find app/src/main/res app/src/main/res-safe -type f -name "*.xml" -exec sed -i '' 's/<item\s\+type="id"\s\+\(name="[^"]*"\)>[^<]*<\/item>/<item type="id" \1><\/item>/g' {} \;

# Replace any numerical or boolean literals in item tags with valid empty format
find app/src/main/res app/src/main/res-safe -type f -name "*.xml" -exec sed -i '' 's/<item\s\+\([^>]*\)>\s*\(true\|false\|[0-9][0-9]*\)\s*<\/item>/<item \1><\/item>/g' {} \;

echo "Fix completed. Run './gradlew clean build' to test the changes."