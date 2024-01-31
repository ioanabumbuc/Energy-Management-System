FROM openjdk:17
ENV PORT 8080
EXPOSE 8080
COPY target/users.jar users.jar
ENTRYPOINT ["java","-jar", "/users.jar"]
