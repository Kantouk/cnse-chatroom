FROM openjdk:17-alpine

# set the working directory to /app (this will also create the /app directory)
WORKDIR /app

# copy the pom.xml into /app and the source-code to /app/src
COPY pom.xml .
COPY src ./src

# build the application with maven
RUN mvn clean install

# expose the standard spring port
EXPOSE 8080

# start the application
CMD ["java", "-jar", "target/your-application.jar"]
