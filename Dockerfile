#os used: alpine
FROM node:23-alpine as frontend-builder
WORKDIR /app
COPY WebView/site /app/WebView/site

RUN apk update && apk add build-base cairo-dev pango-dev jpeg-dev giflib-dev librsvg-dev

WORKDIR /app/WebView/site
RUN npm install
RUN npm install -g @angular/cli
RUN ng build --configuration production

# PART 3
# Use another image: a docker image having docker installed which doesnt require root
FROM eclipse-temurin:17-jdk-jammy as runner
WORKDIR /app
COPY . /app

# Move the frontend files to the SpringBootServer
COPY --from=frontend-builder /app/WebView/site/dist/site /app/SpringBootServer/src/main/resources/static/

# use our env variables in docker
ENV JWT_SECRET="d095d37725430bff93b35467a9cbd13091e8c0729988a035e4d029abffd470318f2f338677aadcaae2d8fd83f4253e9476138c0043f66dcb0f006e5b33e19b6cf9fb4e3b7e6e4a88f8242ce65960dd518a1290183f7982cf5d6c5c760ce96b43f078ccf6c2d4c25772655576e326f0cb039adfcd7afc59fa925895f77b6677ef7050c66146ffd8852e950ef662af51241c5c6a5ddd2e37a03a5026969a30b724fb7c3654b1c299a3a60b9a63d2e55a5dda16657e97b38217dc897fbd1cc70ec3ff12b92b3c7803cdec95f2e9d794a5e1835aac9d1f34407619b767aa47cdd572973baa4ded101f42a69a40aa3366b5beba62219aa913416ae64562d93490d737"
ENV DB_URL="jdbc:postgresql://34.58.98.24:5432/makizapp"

# we tell docker that this container will allow connections on port 8080
EXPOSE 8080

RUN ./gradlew clean build
CMD ["./gradlew", ":SpringBootServer:bootRun", "-Dspring.profiles.active=prod"]
