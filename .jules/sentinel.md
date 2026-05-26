## 2024-05-08 - Fixed Overly Permissive CORS Configuration
**Vulnerability:** The application was configured with `allowedOrigins("*")` in `WebConfig.java`, which allows any website to make requests to the API.
**Learning:** This architectural gap means the API could be vulnerable to cross-origin attacks because the browser would allow any origin to access the resources. We need to define exactly which origins should have access.
**Prevention:** Avoid hardcoding `*` for CORS origins. Instead, inject the allowed origins using application properties (e.g., `@Value("${cors.allowed-origins}")`), and configure a safe default restrict value (like `http://localhost:3000` for local dev) in `application.properties`. For production environments, override this environment variable.

## 2024-05-22 - Mass Assignment/IDOR via Spring Data JPA `save()`
**Vulnerability:** In `TaskService.createTask`, an attacker could supply an `id` field in the POST request body. Spring Data JPA's `save()` method defaults to an upsert if an ID is present, potentially allowing an attacker to overwrite existing records instead of creating a new one.
**Learning:** Spring Data JPA does not distinguish between `create` and `update` when using `save()`; the presence of an identifier determines the operation. Blindly saving an entity constructed from an incoming HTTP request allows mass assignment and IDOR.
**Prevention:** Always ensure the `id` field is explicitly cleared (e.g., `entity.setId(null)`) for new entities before calling `save()`, or use Data Transfer Objects (DTOs) mapped securely to entities.

## 2024-05-26 - Missing Anti-Caching Security Headers
**Vulnerability:** The application was not setting `Cache-Control`, `Pragma`, or `Expires` headers in its HTTP responses.
**Learning:** Without explicit anti-caching directives, browsers and intermediate proxies might cache sensitive JSON data returned by the REST API, potentially exposing it to unauthorized users who access the same browser or through compromised proxies.
**Prevention:** Always implement a filter (like `SecurityHeadersFilter`) that adds `Cache-Control: no-store, no-cache, must-revalidate, max-age=0`, `Pragma: no-cache`, and `Expires: 0` headers to ensure defense-in-depth protection against unintended data caching.
