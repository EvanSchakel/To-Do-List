## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.
## 2024-05-10 - Avoid redundant SELECT on UPDATE in Spring Data JPA
**Learning:** If a service method calls `findById` and then mutates and calls `save` without a class or method-level `@Transactional` annotation, the entity becomes detached. When `save` is called, Hibernate's `merge` operation forces a redundant `SELECT` query to re-attach the entity before executing the `UPDATE`.
**Action:** Always wrap fetch-modify-save operations in a single `@Transactional` method to keep the entity managed, eliminating the redundant `SELECT` query.

## 2024-05-10 - Disable dirty checking on read-only transactions
**Learning:** Hibernate performs dirty checking by default, maintaining a memory snapshot of loaded entities. This introduces unnecessary overhead on pure read operations.
**Action:** Annotate read-only service methods with `@Transactional(readOnly = true)` to disable dirty checking, reducing memory usage and saving CPU cycles during transaction flush.
