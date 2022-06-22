FROM openjdk:11
COPY *.jar application.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/application.jar"]