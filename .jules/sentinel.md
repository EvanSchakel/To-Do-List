## 2024-05-18 - Information Leakage in GlobalExceptionHandler
**Vulnerability:** Information leakage through unhandled exceptions. `GlobalExceptionHandler` was directly returning `ex.getMessage()` to the client when a generic `Exception` occurred.
**Learning:** In Spring Boot applications, returning full exception messages in global exception handlers can expose sensitive internal details such as database schemas, file paths, or third-party API keys if they are part of the exception string.
**Prevention:** Always log the full exception internally (using a framework like SLF4J) for debugging, and return a sanitized, generic error message (e.g., "An unexpected error occurred. Please try again later.") to the client.
