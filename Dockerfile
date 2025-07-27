# Dockerfile for Android Tetris Development Environment
FROM ubuntu:22.04

# Set non-interactive to avoid prompts during installation
ENV DEBIAN_FRONTEND=noninteractive

# Install necessary packages
RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    git \
    curl \
    openjdk-17-jdk \
    build-essential \
    file \
    apt-utils \
    && rm -rf /var/lib/apt/lists/*

# Set Java environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# Install Android SDK
ENV ANDROID_HOME=/opt/android-sdk
ENV ANDROID_SDK_ROOT=$ANDROID_HOME
ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/build-tools

# Create Android SDK directory
RUN mkdir -p $ANDROID_HOME

# Download and install Android Command Line Tools
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -O cmdline-tools.zip \
    && unzip cmdline-tools.zip -d $ANDROID_HOME \
    && rm cmdline-tools.zip \
    && mv $ANDROID_HOME/cmdline-tools $ANDROID_HOME/cmdline-tools-temp \
    && mkdir -p $ANDROID_HOME/cmdline-tools/latest \
    && mv $ANDROID_HOME/cmdline-tools-temp/* $ANDROID_HOME/cmdline-tools/latest/ \
    && rm -rf $ANDROID_HOME/cmdline-tools-temp

# Accept Android SDK licenses
RUN yes | sdkmanager --licenses

# Install required Android SDK components
RUN sdkmanager \
    "platforms;android-34" \
    "platforms;android-24" \
    "build-tools;34.0.0" \
    "build-tools;33.0.2" \
    "platform-tools" \
    "emulator" \
    "system-images;android-34;google_apis;x86_64"

# Set up Gradle
ENV GRADLE_VERSION=8.7
RUN wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -O gradle.zip \
    && unzip gradle.zip -d /opt \
    && rm gradle.zip \
    && ln -s /opt/gradle-${GRADLE_VERSION}/bin/gradle /usr/local/bin/gradle

ENV GRADLE_HOME=/opt/gradle-${GRADLE_VERSION}
ENV PATH=$PATH:$GRADLE_HOME/bin

# Create working directory
WORKDIR /workspace

# Copy project files
COPY . .

# Set proper permissions for gradlew
RUN chmod +x ./gradlew

# Expose port for development server if needed
EXPOSE 8080

# Default command
CMD ["./gradlew", "assembleDebug"]