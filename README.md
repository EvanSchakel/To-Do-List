# To-Do-List API

A simple To-Do List REST API built with Java and Spring Boot.

## Architecture

This project is a backend REST API structured following standard Spring Boot patterns:
- **Controllers** (`com.example.todolist.controller`): Handles incoming HTTP requests and responses.
- **Services** (`com.example.todolist.service`): Contains core business logic.
- **Repositories** (`com.example.todolist.repository`): Handles data persistence to the H2 database via Spring Data JPA.
- **Models** (`com.example.todolist.model`): JPA entities representing the schema.

The project uses an in-memory H2 database, which is seeded with demo data upon startup.

## Prerequisites

- Java 17
- Maven

## How to Run Locally

To start the application locally, run the following command from the root directory:

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.

You can also access the H2 database console locally at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, User: `sa`, Password: `<empty>`).

## How to Test

To run the unit and integration tests for the project:

```bash
mvn clean test
```

## API Examples

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
