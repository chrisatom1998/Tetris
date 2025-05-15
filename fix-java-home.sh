#!/bin/bash

echo "===== Fixing Java Home Configuration ====="

# Check for available Java installations
JAVA_HOMES=()
JAVA_VERSIONS=()

# Check common Java installation directories
if [ -d "/Library/Java/JavaVirtualMachines" ]; then
  echo "Scanning Java installations..."
  for java_dir in /Library/Java/JavaVirtualMachines/*/Contents/Home; do
    if [ -d "$java_dir" ]; then
      version=$($java_dir/bin/java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
      if [[ $version == 11* || $version == 17* ]]; then
        JAVA_HOMES+=("$java_dir")
        JAVA_VERSIONS+=("$version")
        echo "Found compatible Java $version at $java_dir"
      fi
    fi
  done
fi

# Check Homebrew Java installations
if [ -d "/opt/homebrew/Cellar/openjdk@17" ]; then
  for java_dir in /opt/homebrew/Cellar/openjdk@17/*/libexec/openjdk.jdk/Contents/Home; do
    if [ -d "$java_dir" ]; then
      version=$($java_dir/bin/java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
      JAVA_HOMES+=("$java_dir")
      JAVA_VERSIONS+=("$version (Homebrew)")
      echo "Found compatible Java $version (Homebrew) at $java_dir"
    fi
  done
fi

# Select the best Java installation (prefer Java 17)
SELECTED_JAVA_HOME=""
for i in "${!JAVA_HOMES[@]}"; do
  if [[ ${JAVA_VERSIONS[$i]} == 17* ]]; then
    SELECTED_JAVA_HOME="${JAVA_HOMES[$i]}"
    echo "Selected Java ${JAVA_VERSIONS[$i]}"
    break
  fi
done

# If no Java 17 found, use the first compatible Java
if [ -z "$SELECTED_JAVA_HOME" ] && [ ${#JAVA_HOMES[@]} -gt 0 ]; then
  SELECTED_JAVA_HOME="${JAVA_HOMES[0]}"
  echo "Selected Java ${JAVA_VERSIONS[0]}"
fi

# If no compatible Java found, exit
if [ -z "$SELECTED_JAVA_HOME" ]; then
  echo "ERROR: No compatible Java installation found (need Java 11 or 17)."
  echo "Please install Java 17 as described in JAVA17_INSTALLATION_GUIDE.md"
  exit 1
fi

# Update local.properties
echo "Updating Java home in local.properties..."
if [ -f "local.properties" ]; then
  cp local.properties local.properties.bak
  grep -v "^org.gradle.java.home=" local.properties > local.properties.tmp
  mv local.properties.tmp local.properties
  echo "org.gradle.java.home=$SELECTED_JAVA_HOME" >> local.properties
  echo "✅ Updated local.properties with Java path: $SELECTED_JAVA_HOME"
else
  echo "ERROR: local.properties not found!"
  exit 1
fi

# Add KAPT configurations to gradle.properties
echo "Adding KAPT configurations to gradle.properties..."
if [ -f "gradle.properties" ]; then
  cp gradle.properties gradle.properties.bak
  
  # Add KAPT config if not already present
  if ! grep -q "kapt.use.worker.api" gradle.properties; then
    echo "" >> gradle.properties
    echo "# KAPT configuration" >> gradle.properties
    echo "kapt.use.worker.api=false" >> gradle.properties
    echo "kapt.incremental.apt=false" >> gradle.properties
    echo "✅ Added KAPT configuration to gradle.properties"
  fi
else
  echo "ERROR: gradle.properties not found!"
  exit 1
fi

echo ""
echo "✅ Java configuration complete! Now try building with:"
echo "./gradlew clean :app:assembleDebug -x lint"