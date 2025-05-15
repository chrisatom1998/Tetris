#!/bin/bash

echo "===== Searching for ALL question mark occurrences in resources ====="

# Create a report directory
mkdir -p reports
REPORT_FILE="reports/question_mark_search_report.txt"
echo "Question Mark Resource Report" > "$REPORT_FILE"
echo "Generated: $(date)" >> "$REPORT_FILE"
echo "----------------------------------------" >> "$REPORT_FILE"

# Find XML files containing literal question marks
echo "Finding XML files with literal question marks..."
find app/src -type f -name "*.xml" -exec grep -l "[?]" {} \; | sort > temp_question_mark_files.txt
echo "Found $(wc -l < temp_question_mark_files.txt) files with literal question marks" | tee -a "$REPORT_FILE"

# Find XML files with attr/? pattern (direct attribute references)
echo "Finding XML files with attr/? pattern..."
find app/src -type f -name "*.xml" -exec grep -l "attr/[?]" {} \; | sort > temp_question_mark_attrs.txt
echo "Found $(wc -l < temp_question_mark_attrs.txt) files with attr/? pattern" | tee -a "$REPORT_FILE"

# Generate detailed report
echo "----------------------------------------" >> "$REPORT_FILE"
echo "FILES WITH LITERAL QUESTION MARKS:" >> "$REPORT_FILE"
while read -r file; do
  echo "FILE: $file" >> "$REPORT_FILE"
  grep -n "[?]" "$file" >> "$REPORT_FILE"
  echo "" >> "$REPORT_FILE"
done < temp_question_mark_files.txt

echo "----------------------------------------" >> "$REPORT_FILE"
echo "FILES WITH ATTR/? PATTERN:" >> "$REPORT_FILE"
while read -r file; do
  echo "FILE: $file" >> "$REPORT_FILE"
  grep -n "attr/[?]" "$file" >> "$REPORT_FILE"
  echo "" >> "$REPORT_FILE"
done < temp_question_mark_attrs.txt

echo "----------------------------------------" >> "$REPORT_FILE"
echo "KAPT ANNOTATION PROCESSOR DETECTION:" >> "$REPORT_FILE"

# Check for KAPT and Room annotations
find app/src -type f -name "*.kt" -exec grep -l "@Dao\|@Entity\|@Database" {} \; | sort > temp_room_files.txt
echo "Found $(wc -l < temp_room_files.txt) files with Room annotations" | tee -a "$REPORT_FILE"

# Clean up temporary files
rm temp_question_mark_files.txt temp_question_mark_attrs.txt temp_room_files.txt

echo "✅ Analysis complete. Report saved to $REPORT_FILE"
echo "Next step: Run the more aggressive fix script to replace ALL question mark occurrences"