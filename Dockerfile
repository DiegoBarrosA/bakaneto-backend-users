# Dockerfile for bakaneto-backend-users

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

# Build the application, skipping tests
RUN mvn package -DskipTests

# -----------------------------------------------------
# Use Java runtime as a base image for the final image
# Using Jammy Jellyfish (Ubuntu 22.04 based) JRE
FROM eclipse-temurin:17-jre-jammy

# Install libaio required by Oracle client libraries
# Clean up apt cache afterwards to keep image size down
RUN apt-get update && \
    apt-get install -y --no-install-recommends libaio1 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# --- IMPORTANT: Wallet Handling ---
# Option 1 (Recommended): Mount wallet via Podman/Kubernetes volume (as configured in podman-kube.yaml).
#                        Do NOT copy the wallet into the image here.
# Option 2 (Not Recommended): Copy wallet into the image. See note below.

# Set TNS_ADMIN environment variable so Oracle client finds wallet/config
# This path should match the volume mount path in podman-kube.yaml OR the COPY destination if using Option 2.
ENV TNS_ADMIN=/app/wallet

# --- IF USING Option 2 (Copy wallet into image - uncomment lines below and remove volume mount from podman-kube.yaml) ---
# RUN mkdir -p /app/wallet
# COPY src/main/resources/wallet /app/wallet
# RUN chmod -R 600 /app/wallet
# --- End of Option 2 specific lines ---

# Copy the application JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on (matches podman-kube.yaml)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]