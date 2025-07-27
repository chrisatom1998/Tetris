#!/bin/bash

# Docker build script for Android Tetris project

set -e

echo "Building Android Tetris project using Docker..."

# Build the Docker image
echo "Building Docker image..."
docker build -t tetris-android .

# Run the build
echo "Running Android build in container..."
docker run --rm \
    -v "$(pwd)":/workspace \
    -v gradle-cache:/root/.gradle \
    -v android-sdk-cache:/opt/android-sdk \
    tetris-android ./gradlew clean assembleDebug

echo "Build completed! APK should be available in app/build/outputs/apk/debug/"