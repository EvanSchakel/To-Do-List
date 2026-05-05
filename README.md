# To-Do List Application

![Java CI](https://github.com/example/todolist/actions/workflows/ci.yml/badge.svg)

A professional, robust To-Do List API built with Java and Spring Boot. This application provides a full set of CRUD operations for managing tasks, complete with data validation, global exception handling, and auto-generated OpenAPI documentation.

## Features

- **Create, Read, Update, Delete (CRUD)** tasks.
- **Data Validation:** Enforces constraints (e.g., non-blank titles, max lengths).
- **Global Exception Handling:** Standardized, consistent API error responses.
- **In-Memory Database:** Uses H2 for rapid development and testing without external dependencies.
- **Automated Testing:** Comprehensive unit and integration test coverage.
- **API Documentation:** Interactive Swagger UI provided by Springdoc OpenAPI.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web (REST APIs)
- Spring Data JPA (Data Access)
- H2 Database (In-memory SQL Database)
- Hibernate Validator (Input Validation)
- JUnit 5 / Mockito (Testing)
- Springdoc OpenAPI (Swagger UI)

## Screenshots / API Previews

*(Replace with actual screenshots of Swagger UI or frontend application)*

![Swagger UI Placeholder](https://via.placeholder.com/800x400?text=Swagger+UI+Screenshot+Here)
![API Response Placeholder](https://via.placeholder.com/800x200?text=JSON+API+Response+Screenshot+Here)

## Getting Started

### Prerequisites

- Java 17
- Maven 3.6+

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/example/todolist.git
   cd todolist
   ```

2. Build and run using Maven:
   ```bash
   mvn spring-boot:run
   ```

3. The application will start at `http://localhost:8080`.

## API Documentation (Swagger)

Once the application is running, you can explore and test the APIs using the auto-generated Swagger UI.

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## H2 Database Console

To inspect the database directly:

1. Navigate to: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. Use the default configuration:
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **Username:** `sa`
   - **Password:** *(leave blank)*

## Running Tests

To run the automated test suite:

```bash
mvn test
```
