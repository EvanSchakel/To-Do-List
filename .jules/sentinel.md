## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-08 - Fixed Mass Assignment/IDOR in Entity Creation
**Vulnerability:** The `createTask` method in `TaskService.java` accepted a raw `Task` entity from the controller and passed it directly to `taskRepository.save()`. Since Spring Data JPA's `save()` method performs an upsert, an attacker could include an `id` in the POST payload to overwrite an existing task instead of creating a new one.
**Learning:** Binding API payloads directly to database entities without sanitizing primary keys creates a severe Mass Assignment / Insecure Direct Object Reference (IDOR) vulnerability.
**Prevention:** Always explicitly nullify the entity ID (e.g., `entity.setId(null)`) before persisting a new record via Spring Data JPA, or use distinct Data Transfer Objects (DTOs) for request payloads that do not expose the `id` field.
