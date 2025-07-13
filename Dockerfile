# -------- Build Stage --------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy the built JAR from your system
# (This is just a placeholder if you want to build in the container later)
# In our case, we skip this stage and use the pre-built jar

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy JAR to container
COPY todo-api.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

