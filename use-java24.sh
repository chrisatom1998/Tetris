#!/bin/bash

# Script to switch the terminal session to use Java 24
# This script should be sourced, not executed (use: source ./use-java24.sh)

export JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-24.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

# Display Java version to confirm the switch
echo "Switched to Java $(java -version 2>&1 | head -1)"

# Additional helpful information
echo ""
echo "Note: This change only affects the current terminal session."
echo "To use this Java version with Gradle, you can either:"
echo "1. Run Gradle commands from this terminal session, or"
echo "2. Use the gradle.properties file which is already configured"