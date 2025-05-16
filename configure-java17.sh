#!/bin/bash

# ------------------------------------------------------
# Project Configuration Script for Java 17
# ------------------------------------------------------

echo "===== Configuring Android Project for Java 17 ====="
echo ""

# Get the current directory
PROJECT_DIR=$(pwd)
echo "Project directory: $PROJECT_DIR"

# Find Java 17 installation
find_java17() {
  echo "Looking for Java 17 installation..."
  
  # Try to use java_home command first (macOS)
  if command -v /usr/libexec/java_home &> /dev/null; then
    JAVA17_HOME=$(/usr/libexec/java_home -v 17 2>/dev/null)
    if [ -n "$JAVA17_HOME" ]; then
      echo "Found Java 17 using java_home: $JAVA17_HOME"
      return 0
    fi
  fi
  
  # Common Java 17 installation paths on macOS
  possible_java_homes=(
    "/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home"
    "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
    "/Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home"
    "/Library/Java/JavaVirtualMachines/adoptopenjdk-17.jdk/Contents/Home"
    "/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home"
    "/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
    "/usr/local/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
  )
  
  for java_home in "${possible_java_homes[@]}"; do
    if [ -d "$java_home" ]; then
      echo "Found Java 17 at: $java_home"
      JAVA17_HOME="$java_home"
      return 0
    fi
  done
  
  return 1
}

# Update app/build.gradle
update_app_build_gradle() {
  echo "Updating app/build.gradle for Java 17 compatibility..."
  
  if [ ! -f "app/build.gradle" ]; then
    echo "❌ app/build.gradle not found!"
    return 1
  fi
  
  # Create a backup of the original file
  cp app/build.gradle app/build.gradle.bak
  
  # Update compileOptions block
  sed -i '' 's/sourceCompatibility JavaVersion.VERSION_1[1-6]/sourceCompatibility JavaVersion.VERSION_17/g' app/build.gradle
  sed -i '' 's/targetCompatibility JavaVersion.VERSION_1[1-6]/targetCompatibility JavaVersion.VERSION_17/g' app/build.gradle
  
  # Update kotlinOptions block
  sed -i '' "s/jvmTarget = '[0-9][0-9]*'/jvmTarget = '17'/g" app/build.gradle
  
  echo "✓ app/build.gradle updated for Java 17"
}

# Update local.properties
update_local_properties() {
  echo "Updating local.properties with Java 17 path..."
  
  # Create a backup if local.properties exists
  if [ -f "local.properties" ]; then
    cp local.properties local.properties.bak
    
    # Remove any existing org.gradle.java.home line
    grep -v "^org.gradle.java.home=" local.properties > local.properties.tmp
    mv local.properties.tmp local.properties
  fi
  
  # Add Java 17 home path
  echo "org.gradle.java.home=$JAVA17_HOME" >> local.properties
  echo "✓ local.properties updated with Java 17 path: $JAVA17_HOME"
}

# Update gradle.properties
update_gradle_properties() {
  echo "Updating gradle.properties with recommended settings..."
  
  if [ ! -f "gradle.properties" ]; then
    touch gradle.properties
  else
    cp gradle.properties gradle.properties.bak
  fi
  
  # Add or update properties
  if ! grep -q "android.disableResourceValidation" gradle.properties; then
  fi
  
  if ! grep -q "org.gradle.jvmargs" gradle.properties; then
    echo "org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8" >> gradle.properties
  fi
  
  echo "✓ gradle.properties updated with recommended settings"
}

# Verify resource fix file exists
check_resource_fix() {
  echo "Checking for resource fix file..."
  
  if [ -f "app/src/main/res/values/question_mark_attributes.xml" ]; then
    echo "✓ Resource fix file exists"
  else
    echo "❌ Resource fix file missing. Creating it now..."
    
    # Create directory if it doesn't exist
    mkdir -p app/src/main/res/values
    
    # Create the resource fix file
    cat > app/src/main/res/values/question_mark_attributes.xml << EOF
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools" tools:ignore="ResourceName">
    <!-- Define direct replacements for problematic question mark attributes -->
    <attr name="question_mark_attr" format="reference|color|dimension|string" />
    <attr name="double_question_mark_attr" format="reference|color|dimension|string" />
    
    <!-- These are used by the resource processor to replace attr/? and attr/?? -->
    <string name="question_mark">?</string>
    <string name="double_question_mark">??</string>
    
    <!-- Define attributes that might be referenced with ? in themes -->
    <attr name="attr_question_mark" format="reference|color|dimension|string" />
    <attr name="attr_double_question_mark" format="reference|color|dimension|string" />
    
    <!-- Legacy compatibility for direct ? references -->
    <style name="QuestionMarkCompatStyle">
        <item name="question_mark_attr">@null</item>
        <item name="double_question_mark_attr">@null</item>
        <item name="attr_question_mark">@null</item>
        <item name="attr_double_question_mark">@null</item>
    </style>
</resources>
EOF
    echo "✓ Created resource fix file"
  fi
}

# Main execution flow
echo "Step 1: Locating Java 17 installation"
echo "----------------------------------------"
if find_java17; then
  echo "Java 17 found at: $JAVA17_HOME"
else
  echo "❌ Java 17 not found. Please install Java 17 first."
  echo "See JAVA17_INSTALLATION_GUIDE.md for installation instructions."
  exit 1
fi

echo ""
echo "Step 2: Configuring project files"
echo "----------------------------------------"
update_app_build_gradle
update_local_properties
update_gradle_properties
check_resource_fix

echo ""
echo "Step 3: Configuration complete"
echo "----------------------------------------"
echo "Your project has been configured to use Java 17."
echo ""
echo "To build your project, run:"
echo "./gradlew clean :app:assembleDebug -x lint"
echo ""
echo "If you encounter any issues, please refer to JAVA17_INSTALLATION_GUIDE.md"
echo "for troubleshooting steps."
echo ""
echo "NOTE: Make sure to restart Android Studio if it's currently open."