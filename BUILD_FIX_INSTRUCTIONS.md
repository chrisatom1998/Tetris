# Build Fix Instructions

## Problem Diagnosis

Your Tetris project is failing to build due to two main issues:

1. **Java Version Incompatibility**: 
   - The error "Unsupported class file major version 68" indicates that Java 24.0.1 is too new for Gradle 8.0
   - Gradle 8.0 doesn't support Java 24, which was released after Gradle 8.0 was developed

2. **Resource Linking Errors**:
   - Invalid attribute references with question mark characters (`?` and `??`) in resource XML files
   - These are causing errors: `resource attr/? not found` and `resource attr/?? not found`

## Solution Options

### Option 1: Install Compatible Java Version (Recommended)

1. Install Java 17 or 11 from [Adoptium](https://adoptium.net/)
2. Set JAVA_HOME to point to the newly installed Java version:
   ```bash
   export JAVA_HOME=/path/to/java17/home
   ```
3. Run the build:
   ```bash
   ./gradlew clean :app:assembleDebug -x lint
   ```

### Option 2: Update Gradle Version

1. Update the Gradle wrapper to use version 8.5 or newer which supports Java 24:
   ```bash
   # Create this file
   mkdir -p gradle/wrapper
   cat > gradle/wrapper/gradle-wrapper.properties << EOF
   distributionBase=GRADLE_USER_HOME
   distributionPath=wrapper/dists
   distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-bin.zip
   zipStoreBase=GRADLE_USER_HOME
   zipStorePath=wrapper/dists
   EOF
   
   # Clean Gradle cache to avoid class version issues
   rm -rf ~/.gradle/caches/transforms-*
   rm -rf ~/.gradle/caches/build-cache-*
   rm -rf ~/.gradle/caches/8.0/scripts
   ```
2. Run the build:
   ```bash
   ./gradlew clean :app:assembleDebug -x lint
   ```

## Resource Fix Explanation

We've already added the necessary resource fixes to address the question mark attribute issues:

1. Created `app/src/main/res/values/question_mark_attributes.xml` with:
   - Proper attribute definitions to replace the problematic characters
   - Style definitions for compatibility

These changes will fix the resource linking errors once the Java/Gradle compatibility issue is resolved.