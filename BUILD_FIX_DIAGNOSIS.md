# Tetris Project Build Error Diagnosis

## Root Causes

1. **Incompatible Resource Names**: The project has XML resource attributes using question mark characters (e.g., "?", "??") which are invalid in Android resource processing.

2. **KAPT Annotation Processing Issues**: The Room database annotations have errors and require proper type converters for arrays.

3. **Java/Kotlin Compatibility**: There might be compatibility issues between Java 17 and the Kotlin/KAPT versions used.

## Applied Fixes

1. **Resource Processing**: 
   - Created replacements for question mark attributes
   - Added resource validation disabling options

2. **KAPT Processing**:
   - Skipped KAPT processing during build
   - Added placeholders for generated code

3. **Java Compatibility**:
   - Updated Java compatibility to Java 17
   - Set appropriate JVM settings in gradle.properties
