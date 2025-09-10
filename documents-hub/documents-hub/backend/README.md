# documents-hub Microservice

This microservice is responsible for managing policy documents and claim receipts for the insurance platform. It provides a secure and robust API for uploading, downloading, and tracking document metadata.

## Features

- **Upload PDF Documents:** Securely uploads PDF files to Google Cloud Storage (GCS).
- **Track Document Metadata:** Stores metadata for each document in a MySQL database, linking it to a user, a document type, and a business reference ID (e.g., a policy number).
- **Download Documents:** Provides an endpoint to download documents using their business reference, not cryptic filenames.
- **List User Documents:** Allows for fetching a complete list of all documents belonging to a specific user.
- **Database Schema Management:** Uses Flyway to manage and version the database schema, ensuring consistency across all environments.
- **API Documentation:** Provides an interactive Swagger UI for easy testing and exploration of the API.

---

## Technology Stack

- Java 21
- Spring Boot 3.2.5
- MySQL (for database)
- Google Cloud Storage (GCS) (for file storage)
- Maven (for dependency management)
- Flyway (for database migrations)
- Lombok (for reducing boilerplate code)
- Docker (for local development environment)

---

## Prerequisites

Before you begin, ensure you have the following installed on your local machine:

1.  **JDK 21:** Java Development Kit, version 21.
2.  **Maven:** For building the project and managing dependencies.
3.  **Docker & Docker Compose:** For running the local MySQL database.
4.  **Google Cloud SDK (`gcloud`):** For authenticating with your Google Cloud account.

---

## Local Development Setup

Follow these steps to get the `documents-hub` microservice running on your local machine.

### Step 1: Clone the Repository

Clone the project to your local machine if you haven't already.

### Step 2: Configure Google Cloud Authentication

This application needs to authenticate with Google Cloud to access the storage bucket. Run the following command in your terminal and follow the browser prompts to log in with your Google account.

```sh
gcloud auth application-default login
```

### Step 3: Configure Application Properties

Open the main configuration file located at `src/main/resources/application.properties`.

Ensure the following properties are set correctly:

- **GCS Bucket Name:** Set `gcs.bucket.name` to the name of your Google Cloud Storage bucket.
- **Database Credentials:** The default credentials for the local Dockerized database are `root` and `password`. These should match the values in the `docker-compose.yml` file.

### Step 4: Start the Local Database

The project includes a `docker-compose.yml` file to easily start a pre-configured MySQL database.

Open a terminal in the `backend` directory and run:

```sh
docker-compose up -d
```

- This command starts a MySQL container in the background.
- The database will be available at `localhost:3306`.
- Any data will be persisted in a Docker volume, so it will not be lost when you stop the container.

### Step 5: Run the Application

You can now run the Spring Boot application.

- **From your IDE (IntelliJ):** Simply open the `BackendApplication.java` file and click the green "Run" button.
- **From the command line:** Run the following Maven command:

```sh
mvn spring-boot:run
```

When the application starts for the first time, **Flyway will automatically connect to the Dockerized database and run all the migration scripts** in `src/main/resources/db/migration` to create the necessary tables.

---

## Testing the API

Once the application is running, you can explore and test the API using the interactive Swagger UI.

- **API Documentation URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Example Workflow:

1.  **Upload a Document:**
    - Use the `POST /api/documents/upload` endpoint.
    - Provide a `userId`, a `documentType` (e.g., `POLICY_DOCUMENT`), a `referenceId` (e.g., `POL-12345`), and choose a PDF file to upload.
    - The response will be a JSON object with the metadata of the saved document.

2.  **List User's Documents:**
    - Use the `GET /api/documents/user/{userId}` endpoint.
    - Provide the same `userId` you used for the upload.
    - The response will be a JSON array containing the metadata of all documents for that user.

3.  **Download a Document:**
    - Use the `GET /api/documents/download` endpoint.
    - Provide the `referenceId` and `documentType` you used for the upload.
    - This will trigger a download of the original file in your browser.

---

## Building for Production

To create a self-contained executable JAR file, run the following Maven command:

```sh
mvn clean package
```

To build a Docker image from the JAR, use the provided `Dockerfile`:

```sh
docker build -t documents-hub:latest .
```
