#!/bin/bash

echo "===== Temporarily moving problematic resource files ====="

# Create backup directory if it doesn't exist
mkdir -p resource_backups

# Find all XML files with ? in the name or content
echo "Finding XML files with question marks..."

# Move resource files with ? in the name
find app/src/main/res app/src/main/res-safe -type f -name "*?*" | while read file; do
  echo "Moving: $file"
  BACKUP_PATH="resource_backups/$(basename "$file")"
  mv "$file" "$BACKUP_PATH"
done

# Check for XML files with ? content and move them
find app/src/main/res app/src/main/res-safe -type f -name "*.xml" -exec grep -l "attr/?" {} \; | while read file; do
  echo "Moving file with question mark content: $file"
  BACKUP_PATH="resource_backups/$(basename "$file")"
  cp "$file" "$BACKUP_PATH"
  
  # Replace problematic content with valid attribute names
  sed -i '' 's/attr\/?/attr\/question_mark_attr/g' "$file"
  sed -i '' 's/attr\/??/attr\/double_question_mark_attr/g' "$file"
  sed -i '' 's/name="?"/name="question_mark_attr"/g' "$file"
  sed -i '' 's/name="??"/name="double_question_mark_attr"/g' "$file"
  echo "Fixed: $file"
done

echo "✓ Problematic resources have been temporarily moved to resource_backups/"
echo "✓ Try building now with: ./gradlew clean :app:assembleDebug -x lint"
echo "✓ After a successful build, run: ./restore-question-marks-resources.sh"