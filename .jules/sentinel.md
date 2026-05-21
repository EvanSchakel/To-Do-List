## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2026-05-21 - Fixed Mass Assignment/IDOR vulnerability in Task creation
**Vulnerability:** The application accepted user-provided `id` fields in the JSON payload during task creation. Because Spring Data JPA's `save()` method acts as an upsert (inserting if `id` is null, and updating if `id` exists), this allowed malicious users to overwrite existing tasks they shouldn't have access to by supplying an existing `id`.
**Learning:** In Spring Data JPA, always assume `save()` could perform an update instead of an insert if the entity `id` is populated by the client. Direct object binding from `@RequestBody` to entities can lead to mass assignment/IDOR.
**Prevention:** Explicitly nullify the `id` field (e.g., `entity.setId(null)`) of incoming payload entities before calling Spring Data JPA's `save()` method when intending to strictly perform a creation. Alternatively, use DTOs that do not include the `id` field for creation payloads.
