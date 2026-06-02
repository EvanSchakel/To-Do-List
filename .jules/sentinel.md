## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-22 - Mass Assignment/IDOR via Spring Data JPA `save()`
**Vulnerability:** In `TaskService.createTask`, an attacker could supply an `id` field in the POST request body. Spring Data JPA's `save()` method defaults to an upsert if an ID is present, potentially allowing an attacker to overwrite existing records instead of creating a new one.
**Learning:** Spring Data JPA does not distinguish between `create` and `update` when using `save()`; the presence of an identifier determines the operation. Blindly saving an entity constructed from an incoming HTTP request allows mass assignment and IDOR.
**Prevention:** Always ensure the `id` field is explicitly cleared (e.g., `entity.setId(null)`) for new entities before calling `save()`, or use Data Transfer Objects (DTOs) mapped securely to entities.

## 2024-06-25 - Mass Assignment on JPA updatable=false Fields
**Vulnerability:** Audit fields like `createdAt` were configured with `@Column(updatable = false)` in the `Task` entity. However, during the initial POST request to create a task, an attacker could supply a custom `createdAt` value (e.g., `1990-01-01T00:00:00`) in the JSON payload. Spring Data JPA's `save()` method would persist this backdated timestamp because `updatable = false` only restricts subsequent updates, not the initial insert statement.
**Learning:** JPA's `updatable = false` is not a sufficient defense against Mass Assignment/IDOR during entity creation. If an API exposes an entity directly to a `@RequestBody` and passes it to `save()`, all fields provided in the payload will be mapped and inserted, bypassing intent.
**Prevention:** Explicitly overwrite sensitive auto-generated fields (like IDs, audit timestamps, and roles) in the service layer immediately before calling `save()` (e.g., `task.setCreatedAt(LocalDateTime.now());`), or implement Data Transfer Objects (DTOs) that do not include these fields.

## 2024-05-28 - Hardcoded Database Credentials in Properties
**Vulnerability:** Default database credentials (`sa` and empty password) were hardcoded in `application.properties`. While these are standard for local H2 development, they violate the boundary against committing any form of secrets or credentials. If the application later switched to a production database dialect, developers might inadvertently commit real credentials in this file.
**Learning:** Spring Boot's property placeholder resolution provides a secure way to externalize credentials while maintaining seamless local development. You can specify environment variables with fallback defaults.
**Prevention:** Always use environment variable placeholders for credentials in configuration files. Provide safe defaults for non-sensitive data (`${DB_USERNAME:sa}`), but *crucially*, leave password fallbacks completely empty (`${DB_PASSWORD:}`) to ensure no placeholder secret is ever committed.

## 2024-06-25 - Information Leakage via Unhandled Framework Exceptions
**Vulnerability:** The application was exposing internal exception details or logging excessive stack traces (e.g., `HttpMessageNotReadableException`, `MethodArgumentTypeMismatchException`) resulting in 500 Internal Server Errors when clients sent malformed data.
**Learning:** Spring Boot's default error handling can inadvertently leak internal class names or overwhelm server logs with unhandled client-side errors, violating the "fail securely" principle and potentially providing attackers with system insight.
**Prevention:** Explicitly handle framework-level client exceptions in `@RestControllerAdvice`. Return generic, sanitized 400 Bad Request or 404 Not Found JSON responses and log the details locally as warnings, not errors.
