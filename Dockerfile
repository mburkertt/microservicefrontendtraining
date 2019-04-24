FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
ADD ${JAR_FILE} myapp.jar
RUN sh -c 'touch /myapp.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]