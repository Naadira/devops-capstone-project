# Use an official Maven image to build the application
FROM maven:3.8.5-openjdk-17-slim AS build
# Set the working directory inside the container
WORKDIR /app
# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src
# Build the application
RUN mvn clean package
# Use an official OpenJDK runtime as a parent image for the final image
FROM openjdk:17-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy the built jar file from the build stage to the container
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar
# Expose the application's port (updated to 9090)
EXPOSE 9090
# Run the application
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
