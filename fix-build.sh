#!/bin/bash

# ===========================================================
# Android Project Build Fix Script - Corrected Version
# ===========================================================

echo "===== Android Build Fix Script (Corrected) ====="
echo ""

# 1. Setup environment and configurations
JAVA_HOME="/Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home"
if [ ! -d "$JAVA_HOME" ]; then
  JAVA_HOME="/Library/Java/JavaVirtualMachines/zulu-19.jdk/Contents/Home"
fi
echo "Setting JAVA_HOME to: $JAVA_HOME"
export JAVA_HOME="$JAVA_HOME"

# 2. Update local.properties
echo "Updating local.properties with Java path..."
if [ -f "local.properties" ]; then
  cp local.properties local.properties.bak
  grep -v "^org.gradle.java.home=" local.properties > local.properties.tmp
  mv local.properties.tmp local.properties
  echo "org.gradle.java.home=$JAVA_HOME" >> local.properties
  echo "✓ Updated local.properties"
else
  echo "ERROR: local.properties not found!"
  exit 1
fi

# 3. Clean cache before building
echo "Cleaning Gradle caches..."
rm -rf ~/.gradle/caches/transforms-*
rm -rf ~/.gradle/caches/build-cache-*
echo "✓ Gradle caches cleaned"

# 4. Run the build with proper flags to address deprecation warnings
echo ""
echo "Starting build with deprecation warnings enabled..."
./gradlew clean :app:assembleDebug \
  --warning-mode all \
  --stacktrace \
  -Pandroid.disableResourceValidation=true \
  -Pkapt.use.worker.api=false \
  --no-daemon

BUILD_RESULT=$?

# 5. Check build result
if [ $BUILD_RESULT -eq 0 ]; then
  echo ""
  echo "✅ BUILD SUCCESSFUL!"
  echo "The APK should be located at: app/build/outputs/apk/debug/app-debug.apk"
else
  echo ""
  echo "❌ BUILD FAILED with exit code: $BUILD_RESULT"
  echo "Check the logs above for detailed error information."
fi

echo ""
echo "===== Build Script Complete ====="