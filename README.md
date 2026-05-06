# To-Do List API

![Java 17](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-brightgreen)
![H2 Database](https://img.shields.io/badge/Database-H2-blue)
![Maven](https://img.shields.io/badge/Build-Maven-orange)
![License](https://img.shields.io/badge/License-MIT-green)
![CI/CD](https://github.com/example/todolist/actions/workflows/ci.yml/badge.svg)

A clean, robust, and professional To-Do List REST API built with Java and Spring Boot. It provides a simple yet comprehensive foundation for task management applications, demonstrating best practices in API design, data validation, and architecture.

## 🚀 Features

- **CRUD Operations**: Create, read, update, and delete tasks.
- **Data Validation**: Built-in Jakarta validation to ensure data integrity.
- **In-Memory Database**: Uses an H2 database for quick setup and testing, seeded with initial demo data.
- **Swagger Documentation**: Integrated OpenAPI documentation (Swagger UI) for easy API exploration and testing.
- **Error Handling**: Centralized global exception handling to provide consistent JSON error responses.

## 🏗️ Architecture

This project is structured following standard Spring Boot patterns:
- **Controllers** (`com.example.todolist.controller`): Handles incoming HTTP requests and responses.
- **Services** (`com.example.todolist.service`): Contains core business logic.
- **Repositories** (`com.example.todolist.repository`): Handles data persistence to the H2 database via Spring Data JPA.
- **Models** (`com.example.todolist.model`): JPA entities representing the database schema.

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.8+

## 💻 How to Run Locally

To start the application locally, run the following command from the root directory:

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.

You can also access the **H2 database console** locally at `http://localhost:8080/h2-console`:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: *(leave blank)*

## 🧪 How to Test

To run the unit and integration tests for the project:

```bash
mvn clean test
```

## 📖 API Documentation (Swagger UI)

This API includes automatically generated OpenAPI documentation.

Once the application is running, you can access the Swagger UI in your browser:
**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

The OpenAPI raw JSON can be found at:
**[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)**

## 🛠️ API Examples

### Get all tasks
```bash
curl -X GET http://localhost:8080/api/tasks
```

### Create a task
```bash
curl -X POST http://localhost:8080/api/tasks \
     -H "Content-Type: application/json" \
     -d '{"title": "Buy Groceries", "description": "Milk, Eggs, Bread"}'
```

### Update a task
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
     -H "Content-Type: application/json" \
     -d '{"title": "Sample Task 1 Updated", "description": "Updated desc", "completed": true}'
```

### Delete a task
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```
