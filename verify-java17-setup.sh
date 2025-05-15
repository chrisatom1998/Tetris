#!/bin/bash

# ------------------------------------------------------
# Java 17 and Android Project Verification Script
# ------------------------------------------------------

echo "===== Verifying Java 17 and Android Project Setup ====="
echo ""

# Set text colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

# Function to check if Java 17 is being used
check_java_version() {
  echo "Checking Java version..."
  
  # Check if Java is installed
  if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Java not found! Please install Java 17.${NC}"
    return 1
  fi
  
  # Get Java version
  java_version=$(java -version 2>&1 | grep -i version | cut -d'"' -f2)
  echo "Detected Java version: $java_version"
  
  # Check if it's Java 17
  if [[ "$java_version" == 17.* ]]; then
    echo -e "${GREEN}✓ Using Java 17 successfully${NC}"
    return 0
  else
    echo -e "${RED}❌ Not using Java 17! Current version: $java_version${NC}"
    return 1
  fi
}

# Function to check build.gradle configuration
check_build_gradle() {
  echo "Checking build.gradle configuration..."
  
  if [ ! -f "app/build.gradle" ]; then
    echo -e "${RED}❌ app/build.gradle not found!${NC}"
    return 1
  fi
  
  # Check sourceCompatibility setting
  if grep -q "sourceCompatibility JavaVersion.VERSION_17" app/build.gradle; then
    echo -e "${GREEN}✓ sourceCompatibility is set to VERSION_17${NC}"
  else
    echo -e "${RED}❌ sourceCompatibility is not set to VERSION_17${NC}"
    echo "Current setting:"
    grep "sourceCompatibility" app/build.gradle
    return 1
  fi
  
  # Check targetCompatibility setting
  if grep -q "targetCompatibility JavaVersion.VERSION_17" app/build.gradle; then
    echo -e "${GREEN}✓ targetCompatibility is set to VERSION_17${NC}"
  else
    echo -e "${RED}❌ targetCompatibility is not set to VERSION_17${NC}"
    echo "Current setting:"
    grep "targetCompatibility" app/build.gradle
    return 1
  fi
  
  # Check Kotlin jvmTarget
  if grep -q "jvmTarget = '17'" app/build.gradle; then
    echo -e "${GREEN}✓ Kotlin jvmTarget is set to 17${NC}"
  else
    echo -e "${RED}❌ Kotlin jvmTarget is not set to 17${NC}"
    echo "Current setting:"
    grep "jvmTarget" app/build.gradle
    return 1
  fi
  
  return 0
}

# Function to check resource fix file
check_resource_fix() {
  echo "Checking resource fix file..."
  
  if [ -f "app/src/main/res/values/question_mark_attributes.xml" ]; then
    echo -e "${GREEN}✓ Resource fix file exists${NC}"
    
    # Verify contents
    if grep -q "question_mark_attr" app/src/main/res/values/question_mark_attributes.xml && \
       grep -q "double_question_mark_attr" app/src/main/res/values/question_mark_attributes.xml; then
      echo -e "${GREEN}✓ Resource fix file contains required attribute definitions${NC}"
    else
      echo -e "${YELLOW}⚠️ Resource fix file exists but may be missing required definitions${NC}"
    fi
    
    return 0
  else
    echo -e "${RED}❌ Resource fix file missing!${NC}"
    echo "The file app/src/main/res/values/question_mark_attributes.xml is needed to fix resource linking errors"
    return 1
  fi
}

# Function to check local.properties
check_local_properties() {
  echo "Checking local.properties..."
  
  if [ ! -f "local.properties" ]; then
    echo -e "${YELLOW}⚠️ local.properties file not found${NC}"
    return 1
  fi
  
  if grep -q "org.gradle.java.home" local.properties; then
    echo -e "${GREEN}✓ org.gradle.java.home is set in local.properties${NC}"
    java_home_path=$(grep "org.gradle.java.home" local.properties | cut -d'=' -f2)
    echo "  Path: $java_home_path"
    
    # Check if path exists
    if [ -d "$java_home_path" ]; then
      echo -e "${GREEN}✓ Java home path exists${NC}"
      
      # Check if it's Java 17
      if [ -f "$java_home_path/bin/java" ]; then
        version=$("$java_home_path/bin/java" -version 2>&1 | grep -i version | head -1)
        echo "  Java version: $version"
      else
        echo -e "${YELLOW}⚠️ Cannot verify Java version at path${NC}"
      fi
    else
      echo -e "${RED}❌ Java home path does not exist: $java_home_path${NC}"
    fi
  else
    echo -e "${YELLOW}⚠️ org.gradle.java.home not set in local.properties${NC}"
  fi
}

# Function to perform a test build
run_test_build() {
  echo "Running a test build..."
  
  # Save current time
  start_time=$(date +%s)
  
  # Run build with reduced output
  echo "./gradlew clean :app:assembleDebug -x lint"
  if ./gradlew clean :app:assembleDebug -x lint --quiet; then
    # Calculate execution time
    end_time=$(date +%s)
    duration=$((end_time - start_time))
    
    echo -e "${GREEN}✓ Build successful! (Time: ${duration}s)${NC}"
    
    # Check if APK was created
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
      echo -e "${GREEN}✓ Debug APK created successfully${NC}"
    else
      echo -e "${YELLOW}⚠️ Build succeeded but no APK found at expected location${NC}"
    fi
    
    return 0
  else
    echo -e "${RED}❌ Build failed!${NC}"
    return 1
  fi
}

# Function to check for specific errors in build output
check_for_errors() {
  echo "Running build and checking for specific errors..."
  
  # Run build and capture output
  build_output=$(./gradlew :app:assembleDebug -x lint 2>&1)
  
  # Check for version error
  if echo "$build_output" | grep -q "Unsupported class file major version 68"; then
    echo -e "${RED}❌ Java version error detected: 'Unsupported class file major version 68'${NC}"
    echo "This indicates Java 24 is still being used instead of Java 17"
    return 1
  fi
  
  # Check for resource linking error
  if echo "$build_output" | grep -q "resource attr/?.*not found"; then
    echo -e "${RED}❌ Resource linking error detected: 'resource attr/? not found'${NC}"
    return 1
  fi
  
  if echo "$build_output" | grep -q "resource attr/\?\?.*not found"; then
    echo -e "${RED}❌ Resource linking error detected: 'resource attr/?? not found'${NC}"
    return 1
  fi
  
  echo -e "${GREEN}✓ No specific errors detected in build output${NC}"
  return 0
}

# Main execution
echo "Step 1: Checking Java environment"
echo "----------------------------------------"
check_java_version
java_result=$?

echo ""
echo "Step 2: Checking project configuration"
echo "----------------------------------------"
check_build_gradle
build_gradle_result=$?

check_resource_fix
resource_fix_result=$?

check_local_properties
local_properties_result=$?

echo ""
echo "Step 3: Verification summary"
echo "----------------------------------------"
if [ $java_result -eq 0 ] && [ $build_gradle_result -eq 0 ] && [ $resource_fix_result -eq 0 ]; then
  echo -e "${GREEN}✓ Basic configuration looks good!${NC}"
  
  echo ""
  echo "Would you like to run a test build to verify everything works? (y/n)"
  read -p "> " do_test_build
  
  if [[ $do_test_build == "y" || $do_test_build == "Y" ]]; then
    echo ""
    echo "Step 4: Running test build"
    echo "----------------------------------------"
    run_test_build
  else
    echo "Skipping test build. Run manually with: ./gradlew clean :app:assembleDebug -x lint"
  fi
else
  echo -e "${RED}❌ Configuration issues detected.${NC}"
  echo "Please fix the issues highlighted above and run this script again."
  
  echo ""
  echo "Recommended next steps:"
  if [ $java_result -ne 0 ]; then
    echo "1. Install Java 17 using instructions in JAVA17_INSTALLATION_GUIDE.md"
  fi
  if [ $build_gradle_result -ne 0 ]; then
    echo "2. Run the configure-java17.sh script to update build.gradle files"
  fi
  if [ $resource_fix_result -ne 0 ]; then
    echo "3. Ensure the question_mark_attributes.xml file is correctly placed"
  fi
fi

echo ""
echo "===== Verification Complete ====="