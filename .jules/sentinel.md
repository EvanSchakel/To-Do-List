## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2026-05-18 - Fixed Mass Assignment / IDOR in Entity Creation
**Vulnerability:** In Spring Data JPA, `save()` functions as an upsert. If an entity is accepted directly from a user request (e.g., in a POST payload) and it contains an ID, Spring Data JPA will overwrite an existing database entry with that ID instead of creating a new one.
**Learning:** This is a classic Mass Assignment or Insecure Direct Object Reference (IDOR) vulnerability. When entities map directly to request payloads, we must ensure sensitive fields (like IDs or permissions) cannot be maliciously set by the user.
**Prevention:** Explicitly nullify the `id` field (e.g., `entity.setId(null)`) of incoming payload entities before calling Spring Data JPA's `save()` method during creation operations. Alternatively, use DTOs (Data Transfer Objects) instead of mapping directly to entities.
