## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.
## 2024-05-22 - Spring Data JPA Entity Update Optimization

**Learning:** When using Spring Data JPA, calling `save()` on an entity obtained via `findById()` triggers a redundant `SELECT` query to re-attach the entity if the operation isn't wrapped in a method-level `@Transactional`. Even if wrapped, calling `save()` is redundant. Adding `@Transactional` to the service method allows JPA's automatic dirty checking to flush the UPDATE natively without `save()`.
**Action:** In Spring Data CRUD services, always use `@Transactional` on the update methods and rely on dirty checking instead of explicit `save()` calls to save a query.

## 2024-05-22 - Default Read-Only Transactions

**Learning:** Adding `@Transactional(readOnly = true)` to a service class disables Hibernate dirty checking for all read queries, saving memory and CPU.
**Action:** Apply `@Transactional(readOnly = true)` at the class level for data access services and override with standard `@Transactional` for write methods (`create`, `update`, `delete`).
