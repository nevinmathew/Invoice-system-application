
FROM openjdk:17-jdk-alpine3.14
VOLUME /tmp
ENV APP_NAME=myapp
ENV APP_HOME=/usr/app
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

COPY target/spring-boot-docker.jar $APP_HOME/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
