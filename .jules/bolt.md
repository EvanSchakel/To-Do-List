## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.

## 2026-05-12 - Avoid SELECT before UPDATE in Spring Data JPA fetch-modify-save
**Learning:** During a typical fetch-modify-save workflow (e.g., `repository.findById(id).map(entity -> { entity.set...; return repository.save(entity); })`), if the service method lacks `@Transactional`, the entity becomes detached after `findById()`. The subsequent `save()` call acts as a `merge` on a detached entity, triggering a redundant `SELECT` query to fetch the current state before the `UPDATE`.
**Action:** Always add `@Transactional` to service methods performing fetch-modify-save workflows to keep the entity attached to the Persistence Context, which prevents the redundant `SELECT` and allows Hibernate's dirty checking to issue an `UPDATE` automatically or optimally upon flush.
