# To-Do-List REST API

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A robust and scalable To-Do list application built with Java and Spring Boot. This repository serves as a backend REST API for managing tasks.

## Features

* Create, read, update, and delete tasks (CRUD operations).
* Mark tasks as complete or incomplete.
* Simple and clean RESTful endpoints.

## Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Build Tool:** Maven
* **Database:** H2 (In-memory database)
* **Data Access:** Spring Data JPA

## Prerequisites

Before you begin, ensure you have met the following requirements:

* Java Development Kit (JDK) 17 or higher
* Maven 3.6.0 or higher

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/To-Do-List.git
   cd To-Do-List
   ```

2. Build the project:

   ```bash
   mvn clean compile
   ```

3. Run the tests:

   ```bash
   mvn test
   ```

## Usage

To start the application locally:

```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`.

## API Endpoints

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/todos` | Retrieve all tasks |
| `GET` | `/api/todos/{id}` | Retrieve a task by ID |
| `POST` | `/api/todos` | Create a new task |
| `PUT` | `/api/todos/{id}` | Update an existing task |
| `DELETE` | `/api/todos/{id}` | Delete a task |

## Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details on how to submit pull requests to this project.

Please also read our [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) to understand the expectations we have for those participating in the project.

## License

This project is licensed under the terms of the MIT License. See the [LICENSE](LICENSE) file for details.
