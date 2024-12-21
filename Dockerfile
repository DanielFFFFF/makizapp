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
FROM eclipse-temurin:17-jdk-alpine as runner
WORKDIR /app
COPY . /app
USER root

# Move the frontend files to the SpringBootServer
COPY --from=frontend-builder /app/WebView/site/dist/site /app/SpringBootServer/src/main/resources/static/

# use our env variables in docker
ENV JWT_SECRET = ""
ENV DB_PASSWORD = ""
ENV DB_URL = ""

# we tell docker that this container will allow connections on port 8080
EXPOSE 8080

RUN ./gradlew clean build
CMD ["./gradlew", ":SpringBootServer:bootRun"]
