#!/bin/bash

echo "Setting up Java 17 environment for Gradle..."

# Check if Java 17 is installed
if [ -d "/Library/Java/JavaVirtualMachines/jdk-17.jdk" ]; then
  echo "Java 17 found at /Library/Java/JavaVirtualMachines/jdk-17.jdk"
else
  echo "Java 17 not found. You need to install it first."
  echo "For macOS, you can download it from:"
  echo "https://www.oracle.com/java/technologies/downloads/#java17"
  echo "or use Homebrew:"
  echo "brew install openjdk@17"
  exit 1
fi

# Create or update gradle.properties with Java 17 path
GRADLE_PROPS="gradle.properties"

# Check if org.gradle.java.home is already set
if grep -q "org.gradle.java.home" "$GRADLE_PROPS"; then
  # Replace the existing line
  sed -i '' 's|org.gradle.java.home=.*|org.gradle.java.home=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home|g' "$GRADLE_PROPS"
else
  # Add the line
  echo "org.gradle.java.home=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home" >> "$GRADLE_PROPS"
fi

# Add JVM args to allow native access
if ! grep -q "\-\-add-opens java.base/java.lang=ALL-UNNAMED" "$GRADLE_PROPS"; then
  # Backup the old file
  cp "$GRADLE_PROPS" "${GRADLE_PROPS}.bak"
  
  # Update the JVM args line or add it if not present
  if grep -q "org.gradle.jvmargs=" "$GRADLE_PROPS"; then
    sed -i '' 's|org.gradle.jvmargs=.*|org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED|g' "$GRADLE_PROPS"
  else
    echo "org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED" >> "$GRADLE_PROPS"
  fi
fi

# Update compileOptions in build.gradle
APP_BUILD_GRADLE="app/build.gradle"
if [ -f "$APP_BUILD_GRADLE" ]; then
  # Check if Java version is set to 17
  if ! grep -q "JavaVersion.VERSION_17" "$APP_BUILD_GRADLE"; then
    echo "Updating compileOptions in $APP_BUILD_GRADLE to use Java 17..."
    sed -i '' 's/sourceCompatibility = JavaVersion.VERSION_[0-9_]*/sourceCompatibility = JavaVersion.VERSION_17/g' "$APP_BUILD_GRADLE"
    sed -i '' 's/targetCompatibility = JavaVersion.VERSION_[0-9_]*/targetCompatibility = JavaVersion.VERSION_17/g' "$APP_BUILD_GRADLE"
    sed -i '' 's/jvmTarget = "[0-9.]*/jvmTarget = "17/g' "$APP_BUILD_GRADLE"
  fi
fi

echo "Environment setup complete. You can now run Gradle commands."
echo "Try running: ./gradlew tasks"