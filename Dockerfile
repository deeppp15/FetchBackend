# Stage 1: Build the application with Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the working directory
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package

# Stage 2: Run the application with OpenJDK
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Argument for the JAR file produced by Maven
ARG JAR_FILE=target/*.jar

# Copy the JAR file from the build stage
COPY --from=build /app/${JAR_FILE} app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
