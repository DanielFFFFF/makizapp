#!/bin/bash

# Build the frontend
cd WebView/site

## Check if --test flag is passed (example ./start.sh --test)
if [ "$1" == "--test" ]; then
  echo "Skipping frontend dependencies installation..."
else
  echo "Installing frontend dependencies..."
  npm install
fi

# Check if ng is installed
if ! [ -x "$(command -v ng)" ]; then
  echo 'Error: ng is not installed. Please install using: npm install -g @angular/cli' >&2
  exit 1
else
  echo 'Angular CLI is already installed. Building the frontend...'
  ng build
fi

#Moving to makizart root folder
cd ../../

# Removing already generated frontend build files
rm -rf SpringBootServer/src/main/resources/static/*
# Moving compiled frontend files to the backend
mv WebView/site/dist/site/* SpringBootServer/src/main/resources/static/

# Start the backend
echo 'Starting the backend...'

# Rn spring with gradle:
sudo ./gradlew clean build
sudo ./gradlew :SpringBootServer:bootRun

