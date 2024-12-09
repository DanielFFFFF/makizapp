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
 

### How to run (Manual Setup)

1. Firstly we need a postgres database to communicate with the spring server
   - Use the docker compose file in the makizapp directory by running the command
   ```docker compose up```
   This will create and run a container which runs a postgres database
   
2. Configure the server so the api calls your machine
   - In ```SpringBootServer/src/main/resources/static/assets/app.config.json```
    change the variable SERVER_PATH to the ip of your machine. 
   - Also change the IP in the application.yml file in the static server folder of the Springboot application,  for example if the ip of your machine is 
   192.168.14.31, you should have the following in your application.yml:
```yml
  server:
    address: 192.168.14.31
    port: 8080
  ```


3. Using intelliJ, right click on MakizApplication and Run, it should be in the following directory:
   ```SpringBootServer/src/main/java/fr/makizart/restserver/MakizappApplication.java```
4. Afterwards you should be able to connect using the ip of your machine in your browser

### How to compile front-end

In
     ```webview/site```
run the command 
    ```ng build```
this should compile into ```webview/site/dist/site/```
you then need to move the newly compiled files into 
```SpringBootServer/src/main/resources/static```
so that SpringBoot uses the newly compiled frontend.


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

2. Run the script `./start.sh`:
    - ```shell
      chmod +x start.sh
      ./start.sh
   ```
 More details on the script behaviour can be found in the script itself.

