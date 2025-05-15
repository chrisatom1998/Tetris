# Java 17 Installation and Android Project Configuration Guide

This guide will help you install Java 17 on macOS and configure your Android project to use it, resolving the "Unsupported class file major version 68" error and resource linking issues.

## 1. Installing Java 17 on macOS

### Option A: Using Homebrew (Recommended)

Homebrew is the easiest way to install and manage Java versions on macOS.

1. **Install Homebrew** (if not already installed):
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. **Install Java 17**:
   ```bash
   brew update
   brew install openjdk@17
   ```

3. **Create a symlink** (if the brew output suggests it):
   ```bash
   sudo ln -sfn $(brew --prefix)/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
   ```

4. **Add to your shell profile** (.zshrc or .bash_profile):
   ```bash
   echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
   source ~/.zshrc
   ```

### Option B: Using SDKMAN!

SDKMAN! is a tool for managing parallel versions of multiple Software Development Kits.

1. **Install SDKMAN!**:
   ```bash
   curl -s "https://get.sdkman.io" | bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   ```

2. **Install Java 17**:
   ```bash
   sdk install java 17.0.9-tem
   ```

3. **Set as default** (optional):
   ```bash
   sdk default java 17.0.9-tem
   ```

### Option C: Manual Installation from Adoptium

1. Visit [Adoptium](https://adoptium.net/)
2. Download the macOS .pkg installer for Java 17
3. Run the installer package and follow the prompts
4. Set JAVA_HOME:
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   ```

## 2. Verifying Java 17 Installation

After installation, verify Java 17 is correctly installed:

```bash
java -version
```

You should see output indicating Java 17, similar to:
```
openjdk version "17.0.9" 2023-10-17
OpenJDK Runtime Environment Temurin-17.0.9+9 (build 17.0.9+9)
OpenJDK 64-Bit Server VM Temurin-17.0.9+9 (build 17.0.9+9, mixed mode)
```

## 3. Configuring Your Android Project

### Using the Provided Script

We've created a script that automates the project configuration process:

1. **Run the script**:
   ```bash
   ./java17-setup.sh
   ```

2. **What the script does**:
   - Detects Java 17 installation
   - Updates build.gradle files to use Java 17
   - Updates local.properties to reference Java 17
   - Cleans Gradle caches to avoid version conflicts
   - Verifies the setup
   - Offers to perform a test build

3. **Follow the prompts** in the script to complete the configuration

### Manual Project Configuration

If you prefer to configure manually:

1. **Update app/build.gradle**:
   - Find the `compileOptions` block and change to:
     ```gradle
     compileOptions {
         sourceCompatibility JavaVersion.VERSION_17
         targetCompatibility JavaVersion.VERSION_17
     }
     ```
   - Find the `kotlinOptions` block and change to:
     ```gradle
     kotlinOptions {
         jvmTarget = '17'
     }
     ```

2. **Create/update local.properties**:
   - Add the line (replace with your actual Java 17 path):
     ```
     org.gradle.java.home=/path/to/your/java17/home
     ```

3. **Clean Gradle caches**:
   ```bash
   rm -rf ~/.gradle/caches/transforms-*
   rm -rf ~/.gradle/caches/build-cache-*
   rm -rf ~/.gradle/caches/*/scripts
   ```

## 4. Resource Linking Errors Fix

The resource linking errors related to question mark attributes (`?` and `??`) have already been addressed by adding the file `app/src/main/res/values/question_mark_attributes.xml`.

This file provides the necessary attribute definitions to resolve the errors:
```
Android resource linking failed
com.tetris.app-mergeDebugResources-40:/values/values.xml:2087: error: resource attr/? (aka com.tetris:attr/?) not found.
com.tetris.app-mergeDebugResources-40:/values/values.xml:2116: error: resource attr/?? (aka com.tetris:attr/??) not found.
```

The file contains proper attribute definitions for:
- `question_mark_attr` (replacement for `attr/?`)
- `double_question_mark_attr` (replacement for `attr/??`)

## 5. Building and Verifying the Project

After configuration, build the project to verify everything works:

```bash
./gradlew clean :app:assembleDebug -x lint
```

If successful, you should see a build successful message with no errors about:
- Unsupported class file major version
- Resource attr/? not found
- Resource attr/?? not found

## 6. Troubleshooting

### Issue: Java version still incorrect

**Solution**: Make sure JAVA_HOME is correctly set:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
echo $JAVA_HOME
```

### Issue: Resource linking errors persist

**Solution**: Verify the resource fix file exists and try:
```bash
./gradlew clean :app:assembleDebug -x lint -Pandroid.disableResourceValidation=true
```

### Issue: Build fails with other errors

**Solution**: Check Gradle version compatibility:
```bash
# Update gradle wrapper to 7.6.1
./gradlew wrapper --gradle-version=7.6.1
```

### Issue: IDE still uses wrong Java version

**Solution**: Configure IDE Java settings:
- In Android Studio: File → Project Structure → SDK Location → JDK Location
- Set to your Java 17 installation path

## 7. Permanent Environment Setup

For permanent configuration, add to your shell profile (.zshrc, .bash_profile):

```bash
# Set default Java version to 17
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

Source your profile or restart your terminal for changes to take effect.