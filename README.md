# Makizapp

The goal of this repository is to improve upon the existing Makizapp created by a group of M2 students for their capstone project of 2023/2024

## Contributors 2024/2025

- BROSSARD Victor
- CADOREL Jules
- FISHER Daniel
- MELEHI Hanae


## Contributors 2023/2024

- CORNET Kevin
- MEUNIER Rodrigue
- MOLLI Bilal
- SELIN Ludivine
 
### Getting Started
1. To begin, you have to clone the repository. Click on the green button ``` <>Code ``` (1), and copy the link of the git (2).  
   [Screen of the path to follow on GitHub](docs/images/screen_gitclone.png)
2. Then, navigate with your terminale en the folder where you want to put the project Makizapp.
3. Use the command ``` git clone [url of the project, copied into the first step] ```.
4. After that, use ``` cd makizapp ```
5. Now, you have to install some dependencies :
    - TODO

### How to run (Manual Setup)

1. Firstly we need a postgres database to communicate with the spring server
   - Use the docker compose file in the makizapp directory by running the command
     ``` sudo docker compose up ```. This will create and run a container which runs a postgres database.
   > If the port is already in use, you can list the processes using it with this command ```sudo lsof -i :5432``` and kill it with this command ```sudo kill [PID return with the previous command]```

2. Configure the server so the api calls your machine :
   - In ```SpringBootServer/src/main/resources/static/assets/app.config.json```,
     change the variable SERVER_PATH to the ip of your machine.
   - Also change the IP in ```SpringBootServer/src/main/resources/static/application.yml```.
     Your have to change the variable ``` address: ``` of the file like that :

   ```yml
   server:
    address: [ip of your machine]
    port: 8080
   ```
   > To have your ip address, use the following command ``` hostname -I | awk '{print $1}' ```.
   > Example of ip address :  ``` 192.168.6.63 ```.

3. Now, we will compile the front-end. Before, run this command to change the right of the file :
   ``` chmod +x ./build-front.sh ```, and then run this command to compile : ``` ./build-front.sh ```.

4. It's time to execute the application. To begin, you need to clean and compile with  ```sudo ./gradlew clean build ```.
   After that, run the command ```sudo ./gradlew :SpringBootServer:bootRun ```.

   > If you prefer, you can use intelliJ. Right click on MakizApplication and Run, it should be in the following directory:
   > ```SpringBootServer/src/main/java/fr/makizart/restserver/MakizappApplication.java```.

5. Afterwards you should be able to connect using the ip of your machine in your browser with this link :
   ``` [ip of your machine]:8080 ```

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


### Resetting database

If database is modified such as after the removal of the old fset markers, the database must be remade:

```docker-compose down --volumes --remove-orphans```

This command will completely reset the database, this will delete all projects in the database (but not the stored .mind files)




### How to run (Automated Setup with Script)

Make sure you have docker and Java 17 set up on your machine.

Also, in order to test the app in external devices such as phones, you only need to do the following:
- Make sure your developpement machine and the device are on the same network or WiFi.
    - You can also test in their connection by `ping <ip-of-target-device>` from your machine. The IP of the target device can be found in the `about` settings of the device.
- Make sure your machine allows incoming connections on the port 8080 (`sudo ufw allow 8080` on linux per example)
- You will need to determine the IP address of your machine on that network (you can do this by running `ip a` on linux or `ipconfig` on windows).
- Once you have it, you can access the app by typing `http://<the-ip>:8080` in the browser of the device.

This steps are not necessary if you are running the app on the same machine you are developing on.

#### Using the script:

1. Firstly we need a postgres database to communicate with the spring server
    - Use the docker compose file in the makizapp directory by running `docker compose up`.
      This will create and run a container which runs a postgres database.

2. Run the app:
    - ```shell
      cd 
      docker build -t mind-tracker-compiler ./image-tracking-project
      chmod +x start.sh
      ./start.sh
      ```
      
More details on the script behaviour can be found in the script itself.
