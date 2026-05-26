## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.
## 2024-05-22 - Spring Data JPA Entity Update Optimization

**Learning:** When using Spring Data JPA, calling `save()` on an entity obtained via `findById()` triggers a redundant `SELECT` query to re-attach the entity if the operation isn't wrapped in a method-level `@Transactional`. Even if wrapped, calling `save()` is redundant. Adding `@Transactional` to the service method allows JPA's automatic dirty checking to flush the UPDATE natively without `save()`.
**Action:** In Spring Data CRUD services, always use `@Transactional` on the update methods and rely on dirty checking instead of explicit `save()` calls to save a query.

## 2024-05-22 - Default Read-Only Transactions

**Learning:** Adding `@Transactional(readOnly = true)` to a service class disables Hibernate dirty checking for all read queries, saving memory and CPU.
**Action:** Apply `@Transactional(readOnly = true)` at the class level for data access services and override with standard `@Transactional` for write methods (`create`, `update`, `delete`).

## 2024-05-23 - Disable Open Session In View (OSIV)
**Learning:** By default, Spring Boot sets `spring.jpa.open-in-view=true`. This anti-pattern keeps the database connection (Hibernate Session) open during the entire view rendering and JSON serialization phase in the controller layer. Under high load or slow network conditions, this easily leads to database connection pool exhaustion as connections are held significantly longer than necessary.
**Action:** Always set `spring.jpa.open-in-view=false` in `application.properties` for REST APIs. Ensure all required entity associations are properly initialized within the `@Transactional` service layer before returning data to the controller.

## 2026-05-24 - Enable HTTP Response Compression
**Learning:** For Spring Boot REST APIs returning potentially large JSON arrays (like a growing list of tasks), network bandwidth and client-side processing can become a bottleneck. Spring Boot provides out-of-the-box support for HTTP response compression.
**Action:** When a service returns large payloads, enable `server.compression.enabled=true` in `application.properties` with appropriate MIME types and a minimum response size threshold (e.g., 1024 bytes) to optimize network transfer times.

## 2026-05-26 - Enable ETag Support
**Learning:** ShallowEtagHeaderFilter automatically calculates an MD5 hash of the response body and returns a 304 Not Modified if the client's If-None-Match header matches. This saves bandwidth and client-side processing, particularly for repeated API calls for static or infrequently changed data (e.g. GET /api/tasks).
**Action:** In Spring Boot applications, enable ETag support globally by adding a ShallowEtagHeaderFilter bean in a configuration class.
