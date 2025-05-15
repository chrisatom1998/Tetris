#!/bin/bash

echo "===== Restoring backed up resource files ====="

# Check if backup directory exists
if [ ! -d "resource_backups" ]; then
    echo "No backup directory found. Has remove-question-marks-resources.sh been run?"
    exit 1
fi

# Count number of files
BACKUP_COUNT=$(find resource_backups -type f | wc -l)

if [ "$BACKUP_COUNT" -eq 0 ]; then
    echo "No backup files found in resource_backups directory."
    exit 1
fi

echo "Found $BACKUP_COUNT files to restore"

# Restore original files with question marks
find resource_backups -type f | while read file; do
    FILENAME=$(basename "$file")
    
    # Try to find original location
    ORIGINAL=$(find app/src/main/res app/src/main/res-safe -name "$FILENAME" 2>/dev/null)
    
    if [ -n "$ORIGINAL" ]; then
        echo "Restoring: $file -> $ORIGINAL"
        mv "$file" "$ORIGINAL"
    else
        # File was likely deleted during remove process
        echo "Restoring file with original name: $file"
        TARGET_DIR="app/src/main/res/values"
        if [[ $FILENAME == *fix* || $FILENAME == *question* || $FILENAME == *attr* ]]; then
            mv "$file" "$TARGET_DIR/$FILENAME"
        fi
    fi
done

echo "✓ Resource files have been restored"
if [ -z "$(ls -A resource_backups 2>/dev/null)" ]; then
    rmdir resource_backups
    echo "✓ Removed empty backup directory"
else
    echo "Note: Some files still remain in resource_backups directory"
fi