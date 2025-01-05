#!/bin/bash

# Install jq to manipulate json files
#sudo apt-get update && sudo apt-get install -y jq

# Navigate to the Angular project directory
if [ -d "WebView/site" ]; then
  cd WebView/site
else
  echo "Directory WebView/site does not exist!"
  exit 1
fi

# Install npm
echo "Install npm..."
npm install

# Return to the root project directory
cd ../..

# Path of the YAML file
YAML_FILE="SpringBootServer/src/main/resources/application.yml"

# Path of keystore.p12
keystore="$(pwd)/SpringBootServer/src/main/resources/keystore.p12"

# Get the ip
ip=$(hostname -I | awk '{print $1}')

# Change ip in the YAML
sed -i "s|^\(\s*address:\).*|\1 $ip|" "$YAML_FILE"

# Change address in the YAML
sed -i "s|^\(\s*key-store:\).*|\1 $keystore|" "$YAML_FILE"

chmod +x build-front.sh
./build-front.sh