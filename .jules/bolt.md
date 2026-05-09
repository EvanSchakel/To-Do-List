## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.

## 2026-05-09 - Avoid redundant SELECT before UPDATE in Spring Data JPA workflows
**Learning:** In a fetch-modify-save workflow, if the method is not annotated with `@Transactional`, the entity fetched via `findById` becomes detached. When `save()` is subsequently called, Hibernate executes a redundant `SELECT` query to re-attach the entity before issuing the `UPDATE` query.
**Action:** Always add `@Transactional` to service methods that fetch and then modify entities. This keeps the persistence context open, ensuring the entity remains attached and modified properties are automatically flushed (dirty checking) without needing a redundant `SELECT`.
