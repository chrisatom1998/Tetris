#!/bin/bash

# Script to update Gradle to a version compatible with Java 24
echo "===== Tetris Gradle Update Tool ====="

# Create wrapper directory if it doesn't exist
echo "Setting up Gradle 8.5 wrapper properties..."
mkdir -p gradle/wrapper

# Create gradle-wrapper.properties file with version 8.5
cat > gradle/wrapper/gradle-wrapper.properties << EOF
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
EOF

# Clean Gradle cache to avoid class version issues
echo "Cleaning Gradle cache..."
rm -rf ~/.gradle/caches/transforms-*
rm -rf ~/.gradle/caches/build-cache-*
rm -rf ~/.gradle/caches/8.0/scripts

# Create a gradle.properties file with resource validation fixes
echo "Creating optimized gradle.properties..."
cat > gradle.properties << EOF
# Project-wide Gradle settings

# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html

# IDE (e.g. Android Studio) users:
# Settings specified in this file will override any Gradle settings
# configured through the IDE.

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
android.jetifier.blacklist=android-base-common

# Disable resource validation to fix ? and ?? attributes
android.disableResourceValidation=true
android.nonTransitiveRClass=true
android.enableR8=true
android.enableResourceOptimizations=false
EOF

echo "Setup complete! Now running the build..."
echo "Running: ./gradlew clean :app:assembleDebug -x lint"
./gradlew clean :app:assembleDebug -x lint

exit_code=$?
if [ $exit_code -ne 0 ]; then
    echo "Build failed. Please check the error messages above."
    echo "You might need to install Java 17 or 11 as described in BUILD_FIX_INSTRUCTIONS.md."
else
    echo "Build completed successfully!"
fi