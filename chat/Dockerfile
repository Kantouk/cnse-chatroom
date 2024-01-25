FROM maven:3.8-openjdk-17

# set the working directory to /app (this will also create the /app directory)
WORKDIR /app

ENV SPRING_PROFILE_ACTIVE=cloud

# copy the pom.xml into /app and the source-code to /app/src
COPY pom.xml .
COPY src ./src

# build the application with maven
RUN mvn clean -Pcloud install -DskipTests 

# expose the standard spring port
EXPOSE 8081

WORKDIR /app/target

# start the application
CMD ["java", "-jar", "chat--latest.jar"]
