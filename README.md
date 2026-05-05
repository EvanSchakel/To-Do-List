# Spring Boot To-Do Application

A clean, RESTful To-Do list API built with Java and Spring Boot.

## Features
* **CRUD Operations:** Create, Read, Update, and Delete tasks.
* **Input Validation:** Enforces valid data using Jakarta Validation (e.g., non-blank titles).
* **Exception Handling:** Consistent, structured JSON error responses via `@RestControllerAdvice`.
* **API Documentation:** Interactive OpenAPI/Swagger UI.
* **Testing:** Comprehensive unit and integration tests using JUnit 5, Mockito, and Spring Boot Test.
* **CI/CD:** Automated Maven build and test workflow via GitHub Actions.

## Tech Stack
* **Java 17**
* **Spring Boot 3** (Web, Data JPA, Validation)
* **H2 Database** (In-memory, for easy setup and testing)
* **Springdoc OpenAPI** (Swagger UI)
* **JUnit 5 / Mockito** (Testing)

## Getting Started

### Prerequisites
* Java 17 or higher
* Maven 3.8+

### Running Locally
1. Clone the repository.
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the API at `http://localhost:8080/api/tasks`.

## API Documentation

Once the application is running, you can explore and interact with the API using the auto-generated Swagger UI:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

*(Placeholder for Swagger UI Screenshot)*
`![Swagger UI Screenshot](placeholder-swagger-ui.png)`

## Project Structure

* `controller/`: REST endpoints managing HTTP requests and responses.
* `service/`: Core business logic and DTO mapping.
* `repository/`: Spring Data JPA interfaces for database access.
* `model/`: JPA Entity classes.
* `dto/`: Data Transfer Objects for API request/response payloads.
* `exception/`: Global exception handling and custom exception classes.