FROM openjdk:11
COPY target/sys_botica.jar application.jar
EXPOSE 8080
ENV JAVA_OPTS="-Dspring.profiles.active=dev"
CMD ["java","-jar","application.jar"]