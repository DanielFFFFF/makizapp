#!/bin/bash

# Prompt for the root password
read -s -p "Enter root password: " ROOT_PASSWORD
echo

# Function to run a command with optional sudo
run_as_root() {
  if [ "$EUID" -ne 0 ]; then
    printf "Running as root: %s\n" "$*"
    echo "$ROOT_PASSWORD" | sudo -S "$@"
  else
    printf "Running as root: %s\n" "$*"
    "$@"
  fi
}

# Build the frontend
cd WebView/site

# Check if --test flag is passed (example ./start.sh --test)
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
  npm run build-ci
fi

# Moving to makizart root folder
cd ../../

# Removing already generated frontend build files
run_as_root rm -rf SpringBootServer/src/main/resources/static/*
# Moving compiled frontend files to the backend
run_as_root mv WebView/site/dist/site/* SpringBootServer/src/main/resources/static

# Start the backend
echo 'Starting the backend...'

sudo ./gradlew clean build :SpringBootServer:bootRun
