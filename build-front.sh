#!/bin/bash

# Navigate to the Angular project directory
if [ -d "WebView/site" ]; then
  cd WebView/site
else
  echo "Directory WebView/site does not exist!"
  exit 1
fi

# Build the Angular project (using production configuration)
echo "Building Angular project..."
ng build --configuration production

# Check if build was successful
if [ $? -ne 0 ]; then
  echo "Angular build failed!"
  exit 1
fi

# Return to the root project directory
cd ../..

# Check if static directory exists before removing files
if [ -d "SpringBootServer/src/main/resources/static" ]; then
  echo "Cleaning static directory..."
  rm -rf SpringBootServer/src/main/resources/static/*
else
  echo "Directory SpringBootServer/src/main/resources/static does not exist!"
  exit 1
fi

# Copy the built Angular files to the static directory
echo "Copying files to Spring Boot static directory..."
cp -rf WebView/site/dist/site/* SpringBootServer/src/main/resources/static/

echo "Deployment completed successfully!"
