#!/bin/bash

# Navigate to the Angular project directory
cd WebView/site

# Build the Angular project
ng build

# Return to the root project directory
cd ../..

# Remove existing files in the static directory before copying new files
rm -rf SpringBootServer/src/main/resources/static/*

# Copy the built Angular files to the static directory
cp -rf WebView/site/dist/site/* SpringBootServer/src/main/resources/static
