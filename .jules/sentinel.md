## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2026-05-12 - Prevent Mass Assignment / IDOR on Task Creation
**Vulnerability:** The API endpoint `POST /api/tasks` did not explicitly clear the `id` field of the incoming `Task` object.
**Learning:** Spring Data JPA's `save()` method performs an "upsert" (update if ID exists, insert if new). An attacker could send a POST request to `/api/tasks` with an `id` field populated, which would cause `save()` to overwrite an existing task with that ID instead of creating a new one (Mass Assignment / IDOR vulnerability).
**Prevention:** Always explicitly set the ID to `null` before calling `save()` when creating a new entity, e.g., `task.setId(null);`, to ensure that a new database record is always generated.
