## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-16 - Mass Assignment (IDOR) via JPA `save()` during Entity Creation
**Vulnerability:** A malicious user could update existing tasks by supplying an `id` field in the JSON payload of a POST (create) request.
**Learning:** Spring Data JPA's `CrudRepository.save()` method performs an 'upsert'—if the entity passed has a non-null ID that exists in the database, it performs an update instead of an insert. This inherently causes an Insecure Direct Object Reference (IDOR) / Mass Assignment vulnerability if the incoming HTTP payload is mapped directly to the entity and saved.
**Prevention:** Always explicitly nullify primary key fields (e.g., `entity.setId(null)`) on incoming payload objects in the service layer before saving them for 'create' operations, or use distinct Data Transfer Objects (DTOs) for requests.
