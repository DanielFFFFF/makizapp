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

## Context

This project is a web application made in Angular and Spring boot that lets you play videos and images in augmented reality on images that serve as markers.

## Getting Started
1. To begin, you have to clone the repository. For that, you need to navigate with your terminal in the folder where you want to put the project Makizapp.
2. Use the command ```git clone https://github.com/DanielFFFFF/makizapp.git```.
3. After that, enter the project by doing ```cd makizapp```.

## First Execution
1. Firstly we need a postgres database to communicate with the spring server
   - Use the docker compose file in the makizapp directory by running the command
     ```sudo docker compose up```. This will create and run a container which runs a postgres database.
   > If the port is already in use, you can list the processes using it with this command ```sudo lsof -i :5432``` and kill it with this command ```sudo kill [PID return with the previous command]```

   > [How to reset database ?](#Resetting-database)

2. After that, we need to set the Environment Variable.
   Open the secrets.txt and copy the line of JWT_SECRET with its value, then open a new terminal and set it in global environment file:
   ```bash
   sudo nano /etc/environment
   source /etc/environment
   ```
   > You need to restart your machine  ```reboot``` in order to apply this change permanently, so its required only once.
   > This is needed for the authentication to work in both root and ordinary environments.

3. Then, do ```chmod +x first-build.sh``` and ```./first-build.sh```.

4. Afterwards, you should be able to connect using the ip of your machine in your browser with this command :
   ```echo "https://$(hostname -I | awk '{print $1}'):8080"```

## How to run

1. Just do ```sudo ./gradlew :SpringBootServer:bootRun```
   > If you need to compile the front or / and the application, do ```./build-front.sh```

## How to use the application as an Admin

1. [Launch the application](#How-to-run)
   > If it's the first time [Launch the application for the first time](#First-Execution)

2. Log in to the admin connector.

### How to create new project

Click on ```New folder```, then put a name and click on create.
//Image

### How to create a resource

1. Select the project where you want to create a resource
2. Click on ```Create new image tracking```
3. Select at least a name and a video 
   > By default, the first frame of the video will be chosen if no frame is selected
4. Click on ```Enregistrer``` to save your resource.
//Image
### How to share a project

1. To start, you need to go to the corresponding project
2. After that, click on the gear and the button ```Share project```
3. Now you have to choices : 
   - You use the QR code
   - You use the link

## How to use the application as a User

1. First of all, you need to scan the QR code or copy the link of a project
> [Share a project](#How-to-share-a-project)

2. Now you can scan the images and enjoy
//Image
## Util

### Resetting database

If database is modified such as after the removal of the old fset markers, the database must be remade:

```docker-compose down --volumes --remove-orphans```

This command will completely reset the database, this will delete all projects in the database (but not the stored .mind files)




## TODO

We need to allow Springboot to use docker commands without sudo.

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


## How to run (Automated Setup with Script)

Make sure you have docker and Java 17 set up on your machine.

Also, in order to test the app in external devices such as phones, you only need to do the following:
- Make sure your developpement machine and the device are on the same network or WiFi.
    - You can also test in their connection by `ping <ip-of-target-device>` from your machine. The IP of the target device can be found in the `about` settings of the device.
- Make sure your machine allows incoming connections on the port 8080 (`sudo ufw allow 8080` on linux per example)
- You will need to determine the IP address of your machine on that network (you can do this by running `ip a` on linux or `ipconfig` on windows).
- Once you have it, you can access the app by typing `http://<the-ip>:8080` in the browser of the device.

This steps are not necessary if you are running the app on the same machine you are developing on.

### Using the script:

2. Run the app:
    - ```shell
      cd 
      docker build -t mind-tracker-compiler ./image-tracking-project
      chmod +x start.sh
      ./start.sh
      ```
More details on the script behaviour can be found in the script itself.
