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
  rm -rf SpringBootServer/src/main/resources/static/*.js
else
  echo "Directory SpringBootServer/src/main/resources/static does not exist!"
  exit 1
fi

# Copy the built Angular files to the static directory
echo "Copying files to Spring Boot static directory..."
cp -rf WebView/site/dist/site/* SpringBootServer/src/main/resources/static/

# Get the ip
ip=$(hostname -I | awk '{print $1}')
SERVER_PATH="http://$ip:8080"

# Path of the JSON file
JSON_FILE="SpringBootServer/src/main/resources/static/assets/app.config.json"

# Check if JSON exist
if [[ -f $JSON_FILE ]]; then
    # Update SERVER_PATH
    jq --arg newPath "$SERVER_PATH" '.SERVER_PATH = $newPath' "$JSON_FILE" > temp.json && mv temp.json "$JSON_FILE"
    echo "Le fichier JSON a été mis à jour avec SERVER_PATH = $SERVER_PATH :"
    cat "$JSON_FILE"
else
    echo "Le fichier $JSON_FILE n'existe pas."
fi

echo "Deployment completed successfully!"
