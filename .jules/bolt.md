## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.
## 2026-05-21 - Unnecessary Entity save() Call within @Transactional
**Learning:** Calling `repository.save(entity)` on a detached entity within a `@Transactional` method causes JPA to execute `em.merge()`, resulting in an extra `SELECT` query. Relying on automatic dirty checking prevents this.
**Action:** When updating entities within `@Transactional` methods, simply mutate the entity and allow JPA to detect the changes automatically instead of explicitly calling `save()`.
