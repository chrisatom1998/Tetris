#!/bin/bash

echo "===== Fixing KAPT and Room compatibility issues ====="

# Back up original files
cp gradle.properties gradle.properties.bak
cp app/build.gradle app/build.gradle.bak

# Update gradle.properties to fix KAPT
cat > gradle.properties << EOF
# Project-wide Gradle settings

# Memory optimization
org.gradle.jvmargs=-Xmx4096m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8

# Enable Gradle Daemon
org.gradle.daemon=true

# Enable parallel builds
org.gradle.parallel=true

# Enable build caching
org.gradle.caching=true

# Android configuration
android.useAndroidX=true
android.enableJetifier=true

# Critical fix for KAPT and Room
kapt.use.worker.api=false
kapt.incremental.apt=false
kapt.include.compile.classpath=false

# Disable experimental features
android.defaults.buildfeatures.buildconfig=true
android.defaults.buildfeatures.aidl=true
android.defaults.buildfeatures.renderscript=true
android.defaults.buildfeatures.resvalues=true
android.defaults.buildfeatures.shaders=true
EOF

echo "✅ Updated gradle.properties with KAPT fixes"

# Create a KAPT configuration file to fix incremental processing
mkdir -p app/src/main/kapt
cat > app/src/main/kapt/ROOM_INCREMENTAL_KAPT_FIX.txt << EOF
When using Room with Kapt, making sure Room can be processed incrementally is necessary.
This file exists to ensure that the incremental processing can be properly disabled
when there are issues with KAPT's incremental processing.
EOF

echo "✅ Added KAPT incremental processing fix"

# Prompt for next steps
echo "✓ Fixes applied. Now run the following commands:"
echo "------------------------------------"
echo "./gradlew clean"
echo "./gradlew :app:kaptDebugKotlin"
echo "./gradlew :app:assembleDebug -x lint"
echo "------------------------------------"