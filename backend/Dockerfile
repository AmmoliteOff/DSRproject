FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/DSRproject.jar /usr/local/lib/backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/backend.jar"]

#FROM maven:3.8.3-openjdk-17
#WORKDIR /app
#COPY . /app
#RUN mvn package
#EXPOSE 8082
#CMD ["mvn", "spring-boot:run"]