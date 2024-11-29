# Makizapp

The goal of this repository is to improve upon the existing Makizapp created by a group of M2 students for their capstone project of 2023/2024

## Contributors 2024/2025

- BROSSARD Victor
- CADOREL Jules
- FISHER Daniel
- MELEHI Hanae

### How to run

1. We need to allow Springboot to use docker commands without sudo.

Create  a docker group\
   ```sudo groupadd docker```\
Get your username\
   ```whoami```\
Use the username you just found to add yourself to the docker group\
  ```sudo usermod -aG docker $USER```\
Apply the groupchange to your current session to avoid logging in and out\
   ```newgrp docker```

All these changes must be done to user you plan on running the server from.

We also need to build an image that the server uses for building markers:
from the makizapp folder run\
``` sudo docker build -t marker-creator-app ./markers```

docker build -t mind-tracker-compiler .
docker run --rm -v ./output:/app/src/images mind-tracker-compiler

2. We'll need a postgres database to communicate with the spring server
   - Use the docker compose file in the makizapp directory by running the command
   ```docker compose up```
   This will create and run a container which runs a postgres database.

3. Configure the server so the api calls your machine
   - In ```SpringBootServer/src/main/resources/static/assets/app.config.json```
    change the variable SERVER_PATH to the ip of your machine. 
   - Also change the IP in the application.yml file in the static server folder of the Springboot application,  for example if the ip of your machine is 
   192.168.14.31, you should have the following in your application.yml:
```yml
  server:
    address: 192.168.14.31
    port: 8080
  ```


4. Using intelliJ, right click on MakizApplication and Run, it should be in the following directory:
   ```SpringBootServer/src/main/java/fr/makizart/restserver/MakizappApplication.java```
5.Afterwards you should be able to connect using the ip of your machine in your browser

### How to compile front-end

In
     ```webview/site```
run the command 
    ```ng build```
this should compile into ```webview/site/dist/site/```
you then need to move the newly compiled files into 
```SpringBootServer/src/main/resources/static```
so that SpringBoot uses the newly compiled frontend.