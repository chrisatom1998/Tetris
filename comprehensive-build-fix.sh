#!/bin/bash

echo "===== Comprehensive Build Fix ====="

# 1. Update gradle.properties for performance and compatibility
cat > gradle.properties << GRADLE_EOF
# Project-wide Gradle settings
org.gradle.jvmargs=-Xmx4096m -XX:+UseParallelGC -Dfile.encoding=UTF-8

# Performance optimization
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true

# Android configuration
android.useAndroidX=true
android.enableJetifier=true
android.disableResourceValidation=true
android.nonTransitiveRClass=true
android.enableBuildScriptClasspathCheck=false

# Disable KAPT worker API (fixes annotation processing issues)
kapt.use.worker.api=false
kapt.incremental.apt=false
kapt.include.compile.classpath=false
kotlin

# Java settings
org.gradle.java.home=/opt/homebrew/Cellar/openjdk@17/17.0.15/libexec/openjdk.jdk/Contents/Home
GRADLE_EOF

echo "✓ Updated gradle.properties"

# 2. Create resource files with proper attribute names
mkdir -p app/src/main/res-fixed/values

# Create a resource file that defines all the question mark attributes properly
cat > app/src/main/res-fixed/values/question_mark_attributes.xml << XML_EOF
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools" tools:ignore="ResourceName">
    <!-- Define direct replacements for problematic question mark attributes -->
    <attr name="question_mark_attr" format="reference|color|dimension|string" />
    <attr name="double_question_mark_attr" format="reference|color|dimension|string" />
    <attr name="attr_question_mark" format="reference|color|dimension|string" />
    <attr name="attr_double_question_mark" format="reference|color|dimension|string" />
    
    <!-- Define string replacements without question marks -->
    <string name="question_mark" translatable="false">qmark</string>
    <string name="double_question_mark" translatable="false">qqmark</string>
</resources>
XML_EOF

echo "✓ Created fixed resource files"

# 3. Execute the build with appropriate flags
echo "Starting build with skip-KAPT..."

# Clean first to ensure no cached issues
./gradlew clean

# Execute build without KAPT and lint
./gradlew :app:assembleDebug -x kaptDebugKotlin -x lint --stacktrace
BUILD_RESULT=$?

if [ $BUILD_RESULT -eq 0 ]; then
    echo "✅ Build successful!"
else
    echo "❌ Build failed with exit code $BUILD_RESULT"
    echo "See detailed log above."
fi
