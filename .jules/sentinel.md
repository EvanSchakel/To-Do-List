## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-20 - Prevent Mass Assignment / IDOR on Task Creation
**Vulnerability:** The `createTask` endpoint allowed users to explicitly set the `id` of a new Task in the JSON payload.
**Learning:** Spring Data JPA's `save()` method behaves as an upsert (save or update). If an entity passed to `save()` has an existing ID, it performs an update instead of a fresh insert. This led to a mass assignment vulnerability where users could overwrite existing tasks by passing their IDs during task creation.
**Prevention:** Explicitly nullify the identifier field (e.g., `entity.setId(null)`) for new entities in the service layer before persisting them using Spring Data JPA's `save()` to force an insert operation.
