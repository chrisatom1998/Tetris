#!/bin/bash
./gradlew clean :app:assembleDebug -x lint -x kaptDebugKotlin
