# Makizapp

The goal of this repository is to improve upon the existing Makizapp created by a group of M2 students for their capstone project of 2023/2024

## Contributors 2024/2025

- BROSSARD Victor
- CADOREL Jules
- FISHER Daniel
- MELEHI Hanae

### How to install

1. To begin, you have to clone the repository. Click on the green button ``` <>Code ``` (1), and copy the link of the git (2).  
   [Screen of the path to follow on GitHub](docs/images/screen_gitclone.png)
2. Then, navigate with your terminale en the folder where you want to put the project Makizapp.
3. Use the command ``` git clone [url of the project, copied into the first step] ```.
4. After that, use ``` cd makizapp ```
5. Now, you have to install some dependencies :
   - TODO

### How to run

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