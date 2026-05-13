## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-13 - Fixed Mass Assignment / IDOR Vulnerability in Task Creation
**Vulnerability:** The `createTask` method in `TaskService.java` accepted a `Task` object from the request body and passed it directly to `taskRepository.save(task)`. If a malicious user included an existing `id` in the JSON payload, Spring Data JPA would perform an upsert (update) instead of an insert, potentially overwriting an existing task belonging to someone else.
**Learning:** This is a Mass Assignment / Insecure Direct Object Reference (IDOR) vulnerability. It occurs when a framework binds request parameters directly to an entity model, and the save operation relies on the presence of an ID to determine whether to insert or update.
**Prevention:** To prevent this, explicitly nullify the primary key field (e.g., `task.setId(null)`) on incoming payload entities *before* calling `save()` for creation endpoints, ensuring the database always creates a new record.
