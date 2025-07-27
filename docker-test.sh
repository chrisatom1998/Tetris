#!/bin/bash

# Docker validation script for Android Tetris project

set -e

echo "Testing Docker setup for Android Tetris project..."

# Test if Docker is available
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed or not in PATH"
    exit 1
fi

echo "✅ Docker is available"

# Test if docker compose is available
if ! docker compose version &> /dev/null; then
    echo "❌ Docker Compose is not available"
    exit 1
fi

echo "✅ Docker Compose is available"

# Build the development image
echo "🔨 Building development Docker image..."
docker build -f Dockerfile.dev -t tetris-android-dev .

if [ $? -eq 0 ]; then
    echo "✅ Development Docker image built successfully"
else
    echo "❌ Failed to build development Docker image"
    exit 1
fi

# Test running a simple command in the container
echo "🧪 Testing container functionality..."
docker run --rm tetris-android-dev java -version

if [ $? -eq 0 ]; then
    echo "✅ Java is working in container"
else
    echo "❌ Java test failed in container"
    exit 1
fi

# Test Android SDK
echo "🧪 Testing Android SDK..."
docker run --rm tetris-android-dev sdkmanager --list | head -n 5

if [ $? -eq 0 ]; then
    echo "✅ Android SDK is working in container"
else
    echo "❌ Android SDK test failed in container"
    exit 1
fi

# Test Gradle
echo "🧪 Testing Gradle..."
docker run --rm tetris-android-dev gradle --version

if [ $? -eq 0 ]; then
    echo "✅ Gradle is working in container"
else
    echo "❌ Gradle test failed in container"
    exit 1
fi

echo ""
echo "🎉 All Docker tests passed!"
echo ""
echo "You can now use:"
echo "  docker compose up tetris-dev     # For development"
echo "  docker compose up tetris-build   # To build the APK"
echo "  ./docker-build.sh               # Quick build script"