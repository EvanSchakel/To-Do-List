## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.
## 2024-05-11 - Add `@Transactional` to fetch-modify-save operations
**Learning:** In Spring Data JPA, if a service method fetches an entity, modifies it, and saves it, omitting `@Transactional` causes the entity to become detached. The subsequent `save` will then trigger an unnecessary `SELECT` query to merge the detached entity before issuing the `UPDATE`. Adding `@Transactional` keeps the entity managed and avoids the redundant `SELECT`.
**Action:** Add `@Transactional` to service methods that perform fetch-modify-save operations to avoid redundant `SELECT` queries before updates.
