## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-11 - Mass Assignment IDOR on Task Creation
**Vulnerability:** The `createTask` API allowed clients to overwrite existing `Task` entities by providing a JSON body with a specific `id`.
**Learning:** Spring Data JPA's `save()` method behaves as an "upsert". If an entity object constructed from HTTP request body contains an existing database ID, JPA will update the existing record instead of inserting a new one, leading to Mass Assignment/IDOR.
**Prevention:** Explicitly nullify the `id` field of inbound entities in the service layer before calling `repository.save()` during creation flows, or use separate DTO (Data Transfer Object) classes without `id` fields for incoming requests.
