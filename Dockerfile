FROM tomcat:latest
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/ProjectShare.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]

#FROM openjdk:11.0.8-jdk-slim
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]