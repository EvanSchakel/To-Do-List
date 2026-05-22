## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-22 - Mass Assignment/IDOR via Spring Data JPA `save()`
**Vulnerability:** In `TaskService.createTask`, an attacker could supply an `id` field in the POST request body. Spring Data JPA's `save()` method defaults to an upsert if an ID is present, potentially allowing an attacker to overwrite existing records instead of creating a new one.
**Learning:** Spring Data JPA does not distinguish between `create` and `update` when using `save()`; the presence of an identifier determines the operation. Blindly saving an entity constructed from an incoming HTTP request allows mass assignment and IDOR.
**Prevention:** Always ensure the `id` field is explicitly cleared (e.g., `entity.setId(null)`) for new entities before calling `save()`, or use Data Transfer Objects (DTOs) mapped securely to entities.
