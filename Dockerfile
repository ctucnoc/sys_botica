FROM openjdk:11
COPY "./target/*.jar" "botica-admin.jar"
EXPOSE 9090
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENTRYPOINT ["java","-jar","botica-admin.jar"]
