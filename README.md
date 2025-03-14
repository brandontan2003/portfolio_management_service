# Portfolio Management Service

## Description
This is a simple CRUD application that uses the REST APIs for managing a portfolio website. It uses the Spring Boot Framework for the API and the data is stored using MySQL.

## Technologies Used
- **Spring Boot**: A Java-based framework used for building production-ready, stand-alone, and microservice-oriented web applications.
- **MySQL**: An open-source relational database management system widely used for storing and managing structured data.
- **Java**: A versatile, platform-independent programming language utilized for developing the API and backend logic.

## Pre-requisites
Before you begin, ensure that you have the following tools and services installed:
1. **Java**  
   Ensure that you have **Java 17** installed on your system. You can verify this by running:
   ```bash
   java -version
   ```
2. **Docker** is used for containerizing the application and managing services through Docker Compose. You can check if Docker is installed with:
   ```bash
   docker --version
   ```

## Setup Instructions
1. Download Java 17 and verify Gradle installation
   1. Download Java from [Oracle Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [AdoptOpenJDK](https://adoptium.net/).  
      Verify the installation by running:
        ```bash
        java -version
        ```
        Ensure it outputs Java 17.
   2. **Gradle**: Verify Gradle is installed by running:
        ```bash
        gradle -v
        ```
        Alternatively, use the Gradle wrapper provided in the project (`./gradlew`).
2. Link Gradle project
   1. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse). 
   2. Import it as a Gradle project using the build.gradle file.
3.  Build the Project
    Run the following command to build and verify the project:
    ```bash
    ./gradlew build
    ```

---

## Running on Docker
1. Open terminal
2. Run ``./gradlew clean build``
3. Run ``docker compose down``
4. Run ``docker compose up -d --build``
