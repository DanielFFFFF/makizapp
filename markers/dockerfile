# Use an older version of Node.js (e.g., version 12)
FROM node:12

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install all dependencies
RUN npm install

# Copy the entire markerCreatorAppFolder into the working directory
COPY NFT-Marker-Creator ./markerCreatorAppFolder

# Create an output directory
RUN mkdir output

# Set the command to run your application and keep the container alive
CMD ["/bin/sh", "-c", "node ./markerCreatorAppFolder/app.js -i ./output/thumbnail.png -o ./output;"]

# to build
# sudo docker build -t marker-creator-app .
