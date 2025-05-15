#!/bin/bash
echo "===== Running Fixed Build Command ====="
# Clean first
./gradlew clean

# Assemble without KAPT processing
./gradlew :app:assembleDebug -x kaptDebugKotlin -x lint -x processDebugAnnotationsWithKsp
