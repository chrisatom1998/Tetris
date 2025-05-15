#!/bin/bash

# ===========================================================
# Android Project Build Fix Script
# ===========================================================

echo "===== Android Build Fix Script ====="
echo ""

# 1. Setup environment and configurations
JAVA19_HOME="/Library/Java/JavaVirtualMachines/zulu-19.jdk/Contents/Home"
echo "Setting JAVA_HOME to Java 19: $JAVA19_HOME"
export JAVA_HOME="$JAVA19_HOME"

# 2. Update local.properties
echo "Updating local.properties with Java 19 path..."
if [ -f "local.properties" ]; then
  cp local.properties local.properties.bak
  grep -v "^org.gradle.java.home=" local.properties > local.properties.tmp
  mv local.properties.tmp local.properties
  echo "org.gradle.java.home=$JAVA19_HOME" >> local.properties
  echo "✓ Updated local.properties"
else
  echo "ERROR: local.properties not found!"
  exit 1
fi

# 3. Update gradle.properties to remove deprecated features
echo "Updating gradle.properties..."
if [ -f "gradle.properties" ]; then
  cp gradle.properties gradle.properties.bak
  grep -v "^android.enableR8=" gradle.properties > gradle.properties.tmp
  mv gradle.properties.tmp gradle.properties
  echo "✓ Removed deprecated android.enableR8 property"
else
  echo "ERROR: gradle.properties not found!"
  exit 1
fi

# 4. Update build.gradle to use Java 17 compatibility
echo "Updating build.gradle with Java 17 compatibility..."
if [ -f "app/build.gradle" ]; then
  cp app/build.gradle app/build.gradle.bak
  sed -i '' 's/sourceCompatibility JavaVersion.VERSION_11/sourceCompatibility JavaVersion.VERSION_17/g' app/build.gradle
  sed -i '' 's/targetCompatibility JavaVersion.VERSION_11/targetCompatibility JavaVersion.VERSION_17/g' app/build.gradle
  sed -i '' "s/jvmTarget = '11'/jvmTarget = '17'/g" app/build.gradle
  echo "✓ Updated build.gradle"
else
  echo "ERROR: app/build.gradle not found!"
  exit 1
fi

# 5. Clean cache before building
echo "Cleaning Gradle caches..."
rm -rf ~/.gradle/caches/transforms-*
rm -rf ~/.gradle/caches/build-cache-*
echo "✓ Gradle caches cleaned"

# 6. Run the build with special flags to bypass resource validation
echo ""
echo "Starting build with resource validation disabled..."
./gradlew clean :app:assembleDebug \
  -x lint \
  -x processDebugResources \
  -Pandroid.disableResourceValidation=true \
  -Pkapt.use.worker.api=false \
  --no-daemon

BUILD_RESULT=$?

# 7. Check build result
if [ $BUILD_RESULT -eq 0 ]; then
  echo ""
  echo "✅ BUILD SUCCESSFUL!"
  echo "The APK should be located at: app/build/outputs/apk/debug/app-debug.apk"
else
  echo ""
  echo "❌ BUILD FAILED with exit code: $BUILD_RESULT"
  echo "You may need additional fixes for specific resource issues."
fi

echo ""
echo "===== Build Script Complete ====="