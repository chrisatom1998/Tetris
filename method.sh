#!/bin/bash

# This script replaces the "method" command with proper Gradle command
# Usage: ./method.sh [optional task name]

# If no argument is provided, run the tasks command to list all available tasks
if [ $# -eq 0 ]; then
  echo "Running: ./gradlew tasks"
  ./gradlew tasks
else
  # Otherwise, run the specified method/task
  TASK=$1
  echo "Running: ./gradlew $TASK"
  ./gradlew $TASK
fi