#!/bin/bash

# ------------------------------------------------------
# Java 17 Installation and Project Configuration Script
# ------------------------------------------------------

echo "===== Java 17 Setup for Android Project ====="
echo ""

# Function to check if Java 17 is already installed
check_java17_installed() {
  echo "Checking for existing Java 17 installations..."
  
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
      echo "✓ Found Java 17 at: $java_home"
      export JAVA_HOME="$java_home"
      return 0
    fi
  done
  
  return 1
}

# Function to display Java installation instructions
show_java17_installation_instructions() {
  echo "Java 17 not found. Here are installation options:"
  echo ""
  echo "Option 1: Install using Homebrew (Recommended)"
  echo "----------------------------------------"
  echo "1. Open Terminal"
  echo "2. Run the following commands:"
  echo "   $ brew update"
  echo "   $ brew install openjdk@17"
  echo ""
  echo "3. Follow the post-installation instructions from Homebrew"
  echo "   (Usually involves creating a symlink with sudo)"
  echo ""
  echo "Option 2: Install using SDKMAN!"
  echo "----------------------------------------"
  echo "1. Install SDKMAN! if not already installed:"
  echo "   $ curl -s \"https://get.sdkman.io\" | bash"
  echo "   $ source \"$HOME/.sdkman/bin/sdkman-init.sh\""
  echo ""
  echo "2. Install Java 17:"
  echo "   $ sdk install java 17.0.9-tem"
  echo ""
  echo "Option 3: Download from Adoptium"
  echo "----------------------------------------"
  echo "1. Visit https://adoptium.net/"
  echo "2. Download Java 17 JDK for macOS"
  echo "3. Run the installer package"
  echo ""
  echo "After installation, run this script again to configure your project."
  echo ""
}

# Function to update build.gradle files to use Java 17
update_gradle_files() {
  echo "Updating build.gradle files to use Java 17..."
  
  # Check if we need to update root build.gradle
  if [ -f "build.gradle" ]; then
    if grep -q "JavaVersion.VERSION_1[1-6]" build.gradle; then
      sed -i '' 's/JavaVersion\.VERSION_1[1-6]/JavaVersion.VERSION_17/g' build.gradle
      echo "✓ Updated root build.gradle Java compatibility"
    fi
  fi
  
  # Update app/build.gradle
  if [ -f "app/build.gradle" ]; then
    # Update sourceCompatibility and targetCompatibility
    if grep -q "sourceCompatibility JavaVersion.VERSION_1[1-6]" app/build.gradle; then
      sed -i '' 's/sourceCompatibility JavaVersion\.VERSION_1[1-6]/sourceCompatibility JavaVersion.VERSION_17/g' app/build.gradle
      echo "✓ Updated app/build.gradle sourceCompatibility"
    fi
    
    if grep -q "targetCompatibility JavaVersion.VERSION_1[1-6]" app/build.gradle; then
      sed -i '' 's/targetCompatibility JavaVersion\.VERSION_1[1-6]/targetCompatibility JavaVersion.VERSION_17/g' app/build.gradle
      echo "✓ Updated app/build.gradle targetCompatibility"
    fi
    
    # Update jvmTarget if present
    if grep -q "jvmTarget = '[0-9][0-9]*'" app/build.gradle; then
      sed -i '' "s/jvmTarget = '[0-9][0-9]*'/jvmTarget = '17'/g" app/build.gradle
      echo "✓ Updated app/build.gradle Kotlin jvmTarget"
    fi
  fi
  
  echo "✓ Gradle files updated for Java 17 compatibility"
}

# Function to create local.properties with Java home if not exists
update_local_properties() {
  echo "Updating local.properties with Java 17 path..."
  
  # Create or update local.properties
  if [ -f "local.properties" ]; then
    # Remove any existing org.gradle.java.home property
    grep -v "^org.gradle.java.home=" local.properties > local.properties.tmp
    mv local.properties.tmp local.properties
  fi
  
  # Add the new Java home path
  echo "org.gradle.java.home=$JAVA_HOME" >> local.properties
  echo "✓ Updated local.properties with Java 17 path"
}

# Function to clean Gradle caches
clean_gradle_caches() {
  echo "Cleaning Gradle caches to avoid class version issues..."
  rm -rf ~/.gradle/caches/transforms-*
  rm -rf ~/.gradle/caches/build-cache-*
  rm -rf ~/.gradle/caches/*/scripts
  echo "✓ Gradle caches cleaned"
}

# Function to verify the setup
verify_setup() {
  echo "Verifying Java 17 setup..."
  
  # Check JAVA_HOME is set correctly
  if [ -z "$JAVA_HOME" ]; then
    echo "❌ JAVA_HOME is not set. Setup is incomplete."
    return 1
  fi
  
  # Check Java version
  java_version=$("$JAVA_HOME/bin/java" -version 2>&1 | grep "version" | awk -F '"' '{print $2}')
  echo "Java version: $java_version"
  
  if [[ "$java_version" == 17.* ]]; then
    echo "✓ Java 17 is correctly configured"
  else
    echo "❌ Java version is not 17. Setup is incomplete."
    return 1
  fi
  
  # Check if question_mark_attributes.xml exists
  if [ -f "app/src/main/res/values/question_mark_attributes.xml" ]; then
    echo "✓ Resource fix file is present"
  else
    echo "❌ Resource fix file is missing. Resource linking errors may persist."
    return 1
  fi
  
  echo "✓ Setup verification complete"
  return 0
}

# Function to attempt a test build
test_build() {
  echo "Attempting a test build to verify configuration..."
  
  # Clean and build using gradlew
  ./gradlew clean :app:assembleDebug \
    -x lint \
    -Dorg.gradle.java.home="$JAVA_HOME"
    
  # Check if build was successful
  if [ $? -eq 0 ]; then
    echo "✓ Build successful! Your project is now properly configured with Java 17."
  else
    echo "❌ Build failed. Please check the error messages above for details."
  fi
}

# Main execution flow
echo "Step 1: Checking for Java 17 installation"
echo "----------------------------------------"
if check_java17_installed; then
  echo "Java 17 found and set as JAVA_HOME: $JAVA_HOME"
else
  show_java17_installation_instructions
  echo "Please install Java 17 and run this script again."
  exit 1
fi

echo ""
echo "Step 2: Updating project configuration"
echo "----------------------------------------"
update_gradle_files
update_local_properties
clean_gradle_caches

echo ""
echo "Step 3: Verifying configuration"
echo "----------------------------------------"
if verify_setup; then
  echo "Configuration looks good!"
else
  echo "Configuration issues detected. Please check the messages above."
  exit 1
fi

echo ""
echo "Step 4: Test build"
echo "----------------------------------------"
echo "Would you like to perform a test build now? (y/n)"
read -p "> " do_test_build

if [[ $do_test_build == "y" || $do_test_build == "Y" ]]; then
  test_build
else
  echo "Skipping test build."
  echo "You can run a build manually with:"
  echo "./gradlew clean :app:assembleDebug -x lint -Dorg.gradle.java.home=\"$JAVA_HOME\""
fi

echo ""
echo "===== Java 17 Setup Complete ====="
echo ""
echo "To build your project in the future, you can:"
echo "1. Set JAVA_HOME for your session: export JAVA_HOME=\"$JAVA_HOME\""
echo "2. Run Gradle: ./gradlew assembleDebug"
echo ""
echo "Or use this script again to verify your setup."