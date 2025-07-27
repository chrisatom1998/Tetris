# Makefile for Android Tetris Docker development

.PHONY: help build dev build-apk test clean

# Default target
help:
	@echo "Android Tetris Docker Commands:"
	@echo ""
	@echo "  make build      - Build the Docker development image"
	@echo "  make dev        - Start interactive development environment"
	@echo "  make build-apk  - Build the Android APK"
	@echo "  make test       - Test Docker setup"
	@echo "  make clean      - Clean Docker images and containers"
	@echo ""

# Build the development Docker image
build:
	docker build -f Dockerfile.dev -t tetris-android-dev .

# Start interactive development environment
dev:
	docker-compose up tetris-dev

# Build the Android APK
build-apk:
	docker-compose up tetris-build

# Test Docker setup
test:
	./docker-test.sh

# Clean up Docker images and containers
clean:
	docker-compose down -v
	docker rmi -f tetris-android-dev tetris-android 2>/dev/null || true
	docker system prune -f