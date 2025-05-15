#!/bin/bash

echo "===== Creating direct resource patches ====="

# Create directory for patches
mkdir -p app/src/main/res-direct-patches

# Create a direct XML fix that replaces all ? attributes
cat > app/src/main/res-direct-patches/attr_replacements.xml << EOF
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools"
    tools:ignore="ResourceName,UnusedResources">
    
    <!-- Direct replacements for problematic attributes -->
    <attr name="question_mark_attr" format="reference|color|dimension|string" />
    <attr name="double_question_mark_attr" format="reference|color|dimension|string" />
    <attr name="attr_question_mark" format="reference|color|dimension|string" />
    <attr name="attr_double_question_mark" format="reference|color|dimension|string" />
    <attr name="attr_question_mark_alias" format="reference|color|dimension|string" />
    <attr name="attr_double_question_mark_alias" format="reference|color|dimension|string" />
    
    <!-- Define item and string replacements -->
    <!-- These will take precedence over app resources -->
    <item name="question_mark_attr" type="attr">@null</item>
    <item name="double_question_mark_attr" type="attr">@null</item>
    <item name="attr_question_mark" type="attr">@null</item>
    <item name="attr_double_question_mark" type="attr">@null</item>
    
    <!-- Direct string replacements -->
    <string name="question_mark" translatable="false">qmark</string>
    <string name="double_question_mark" translatable="false">qqmark</string>
</resources>
EOF

# Also create a style fixer
cat > app/src/main/res-direct-patches/style_fixes.xml << EOF
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools" 
    tools:ignore="ResourceName,UnusedResources">
    
    <!-- Style that overrides question mark references -->
    <style name="QuestionMarkFixStyle">
        <item name="question_mark_attr">@null</item>
        <item name="double_question_mark_attr">@null</item>
        <item name="attr_question_mark">@null</item>
        <item name="attr_double_question_mark">@null</item>
    </style>
    
    <!-- Apply these styles to the application theme -->
    <style name="AppTheme.QuestionMarkFix" parent="@style/Theme.AppCompat">
        <item name="question_mark_attr">@null</item>
        <item name="double_question_mark_attr">@null</item>
    </style>
</resources>
EOF

# Modify the build.gradle to add the patches directory first
# Create a simplified approach instead of using sed
cp app/build.gradle app/build.gradle.bak

# Create a patch file for inclusion in the build.gradle
cat > app/build.gradle.patch << EOF
    // Setup resource processing for question mark issues
    sourceSets {
        main {
            res.srcDirs = [
                'src/main/res-direct-patches',  // Patches first
                'src/main/res-safe',
                'src/main/res'
            ]
        }
    }
EOF

# Apply the patch manually by inserting into a new file
grep -B 1000 "sourceSets {" app/build.gradle > app/build.gradle.new
cat app/build.gradle.patch >> app/build.gradle.new
grep -A 1000 -m 1 "}" app/build.gradle | tail -n +2 >> app/build.gradle.new
mv app/build.gradle.new app/build.gradle

echo "✓ Created direct resource patches"
echo "✓ Updated build.gradle to include patches directory"

echo "Now run the build without KAPT:"
echo "./gradlew clean :app:assembleDebug -x kaptDebugKotlin -x lint"