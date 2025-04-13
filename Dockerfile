# Use a Maven image to build the application
FROM maven:3.9-eclipse-temurin-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download all dependencies (this step is cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src/ ./src/

# Build the application
RUN mvn package -DskipTests

# Use Java runtime as a base image for the final image
FROM eclipse-temurin:17-jre-jammy

# Install libaio for Oracle client
RUN apt-get update && apt-get install -y libaio1 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Create wallet directory and set environment variable
RUN mkdir -p /app/wallet
ENV TNS_ADMIN=/app/wallet

# Copy wallet from source to runtime image
COPY src/main/resources/wallet /app/wallet
RUN chmod -R 600 /app/wallet

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
