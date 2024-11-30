# Use an official Node.js image as the base
FROM node:18

# Set the working directory
WORKDIR /app

# Install dependencies required for canvas
RUN apt-get update && \
    apt-get install -y \
    libcairo2-dev \
    libjpeg-dev \
    libpango1.0-dev \
    libgif-dev \
    build-essential \
    && rm -rf /var/lib/apt/lists/*  # Clean up to reduce image size

# Copy your package.json and package-lock.json (if you have them)
COPY package*.json ./

# Install node modules (including canvas)
RUN npm install

RUN npm install canvas

# Copy the rest of the application files
COPY . .

# Expose the port your application will run on
EXPOSE 3000


# Command to run your app (modify as needed)
CMD ["node", "./src/generate_mind.js", "images"]

