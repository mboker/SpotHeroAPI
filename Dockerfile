FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG WAR_FILE
COPY ${WAR_FILE} app.war
COPY wait-for-it.sh .
RUN apk update && apk add bash
RUN ["chmod", "+x", "./wait-for-it.sh"]
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]

