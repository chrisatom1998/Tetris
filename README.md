# Android Tetris Game

A modern Android Tetris game built with Kotlin, featuring smooth gameplay, customizable themes, and persistent game state.

## Requirements

- Android SDK 24+ (Android 7.0+)
- Target SDK: 34 (Android 14)
- Java 17
- Gradle 8.7

## Development Setup

### Option 1: Docker Development (Recommended)

Docker provides a consistent development environment with all required tools pre-installed.

#### Prerequisites
- Docker
- Docker Compose

## Quick Start Guide

### Using Make (Recommended)
```bash
# Test Docker setup
make test

# Start development environment
make dev

# Build APK
make build-apk

# Clean up
make clean
```

### Using Docker Compose

1. **Build and run using Docker Compose:**
   ```bash
   # Development environment (interactive shell)
   docker-compose up tetris-dev
   
   # Or build the APK directly
   docker-compose up tetris-build
   ```

2. **Build using the provided script:**
   ```bash
   ./docker-build.sh
   ```

3. **Manual Docker commands:**
   ```bash
   # Build the Docker image
   docker build -t tetris-android .
   
   # Run interactive development environment
   docker run -it --rm \
     -v $(pwd):/workspace \
     -v gradle-cache:/root/.gradle \
     tetris-android /bin/bash
   
   # Build the APK
   docker run --rm \
     -v $(pwd):/workspace \
     -v gradle-cache:/root/.gradle \
     tetris-android ./gradlew assembleDebug
   ```

#### Docker Services

- **tetris-dev**: Interactive development environment (lightweight)
- **tetris-build**: Automated build service
- **tetris-android-dev**: Legacy development service (backwards compatibility)

The built APK will be available in `app/build/outputs/apk/debug/`.

### Option 2: Local Development

1. **Install Java 17:**
   - Download and install OpenJDK 17
   - Set `JAVA_HOME` environment variable

2. **Install Android SDK:**
   - Install Android Studio or Android Command Line Tools
   - Set `ANDROID_HOME` environment variable
   - Install Android SDK 34 and build tools

3. **Build the project:**
   ```bash
   ./gradlew assembleDebug
   ```

## Project Structure

```
app/
├── src/main/java/com/tetris/
│   ├── model/          # Data models (GameState, Piece, Board, etc.)
│   ├── game/           # Game logic (TetrisEngine, InputManager, etc.)
│   ├── ui/             # UI components and activities
│   ├── database/       # Room database and DAOs
│   └── utils/          # Utility classes
└── src/main/res/       # Android resources
```

## Features

- **Core Gameplay:**
  - Classic Tetris mechanics
  - Smooth piece movement and rotation
  - Line clearing with animations
  - Progressive difficulty scaling

- **UI/UX:**
  - Touch controls optimized for mobile
  - Customizable themes
  - Responsive layout for different screen sizes

- **Data Persistence:**
  - High scores tracking
  - Game state saving/loading
  - Player statistics
  - Achievement system

- **Social Features:**
  - Score sharing capabilities
  - Achievement sharing

## Docker Environment Details

The Docker environment includes:
- Ubuntu 22.04 base image
- OpenJDK 17
- Android SDK with API levels 24 and 34
- Android Build Tools 34.0.0
- Gradle 8.7
- All necessary development dependencies

Volume mounts preserve:
- Gradle cache for faster builds
- Android SDK for persistence
- Source code for live development

## Troubleshooting

### Docker Issues

1. **Permission errors:**
   ```bash
   sudo chown -R $USER:$USER .
   ```

2. **Build tool issues:**
   ```bash
   docker-compose down -v  # Remove volumes
   docker-compose up --build tetris-build
   ```

3. **Memory issues:**
   - Increase Docker memory allocation to at least 4GB

### Build Issues

Check the build fix scripts in the repository root for common Android build problems.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Use Docker for consistent development environment
4. Test your changes thoroughly
5. Submit a pull request

## License

This project is open source. See the license file for details.