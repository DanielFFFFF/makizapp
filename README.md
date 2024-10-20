# Makizapp

The goal of this repository is to improve upon the existing Makizapp created by a group of M2 students for their capstone project of 2023/2024

## Contributors 2024/2025

- BROSSARD Victor
- CADOREL Jules
- FISHER Daniel
- MELEHI Hanae

### How to run

1. Firstly we need a postgres database to communicate with the spring server
   - Use the docker compose file in the makizapp directory by running the command
   ```docker compose up```
   This will create and run a container which runs a postgres database
   
2. Configure the server so the api calls your machine
   - In ```SpringBootServer/src/main/resources/static/assets/app.config.json```
    change the variable SERVER_PATH to the ip of your machine. 
3. Using intelliJ, right click on MakizappApplication and Run, it should be in the following directory:
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
