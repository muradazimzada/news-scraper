
# News Parser Application

This repository contains two main projects:
1. **news-parser-server**: A Spring Boot application that serves as the backend.
2. **new-parser-interface**: A JavaFX-based user interface that interacts with the Spring Boot backend.

## Requirements
- **Java 21** or higher
- **Gradle** (or use the provided Gradle wrapper)
- **Docker & Docker Compose**

## Setup

### Step 1: Start MySQL Database using Docker Compose

The MySQL database is required for the Spring Boot backend. You can start it by running the following command:

\`\`\`bash
docker-compose up -d
\`\`\`

This will start the MySQL service in a Docker container. The \`docker-compose.yml\` file is already configured to set up the necessary MySQL database.

### Step 2: Run the Spring Boot Project (news-parser-server)

After starting the database, navigate to the \`news-parser-server\` directory and start the Spring Boot application.

Using Gradle Wrapper:

\`\`\`bash
cd news-parser-server
./gradlew bootRun
\`\`\`

If you have Gradle installed globally:

\`\`\`bash
gradle bootRun
\`\`\`

This will start the backend API server that scrapes news data and interacts with the MySQL database.

### Step 3: Run the JavaFX Project (new-parser-interface)

Once the Spring Boot server is running, you can start the JavaFX-based UI application, which interacts with the API provided by the Spring Boot server.

Navigate to the \`new-parser-interface\` directory:

Using Gradle Wrapper:

\`\`\`bash
cd ../new-parser-interface
./gradlew run
\`\`\`

If you have Gradle installed globally:

\`\`\`bash
gradle run
\`\`\`

This will launch the JavaFX application that allows users to view and interact with the news articles.

## Application Structure

### 1. **news-parser-server**
- A Spring Boot application that scrapes news articles from external sources, stores them in a MySQL database, and exposes RESTful APIs for the client.
- Located in the \`news-parser-server/\` directory.

### 2. **new-parser-interface**
- A JavaFX-based client that fetches and displays news articles from the Spring Boot backend.
- Located in the \`new-parser-interface/\` directory.

## Additional Information

- The Spring Boot application uses Gradle for build and dependency management. You can use the provided Gradle wrapper (\`./gradlew\`) or a globally installed Gradle.
- The JavaFX project is also built using Gradle.

### Directory Structure

\`\`\`bash
.
├── .gitignore
├── docker-compose.yml
├── README.md
├── news-parser-server/        # Spring Boot application (backend)
│   └── ...                    # Server files
└── new-parser-interface/      # JavaFX application (frontend)
    └── ...                    # Client files
\`\`\`

## Running Tests

To run tests for the Spring Boot backend, use:

\`\`\`bash
./gradlew test
\`\`\`

For any additional tests in the JavaFX project, use:

\`\`\`bash
./gradlew test
\`\`\`

## Troubleshooting

- Ensure Docker and Docker Compose are properly installed on your system.
- Make sure you are using Java 21 or higher to run the projects.
- If you encounter any port conflicts, update the \`docker-compose.yml\` and Spring Boot \`application.properties\` files to adjust the ports.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
