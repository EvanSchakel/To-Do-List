## 2024-05-07 - Avoid SELECT before DELETE in Spring Data JPA
**Learning:** By default, Spring Data JPA's `deleteById` and manual "check existence then delete" workflows cause an unnecessary SELECT query before the actual DELETE query. In a high-throughput system, this N+1 like behavior is a performance bottleneck.
**Action:** Use a custom `@Modifying` JPQL `@Query("DELETE FROM Entity e WHERE e.id = :id")` that returns the number of affected rows to achieve a deletion in a single database roundtrip, as long as JPA lifecycle callbacks (`@PreRemove`) or entity-level cascades are not required.
## 2024-05-17 - Read-Only Transactions for Better Performance
**Learning:** By default, Spring Data JPA services run transactions with dirty checking enabled. This means Hibernate tracks changes to entities during read operations, which is unnecessary and consumes CPU and memory.
**Action:** Always apply `@Transactional(readOnly = true)` at the class level of Spring Data JPA services to disable dirty checking for read operations, and override it with `@Transactional` on specific methods that modify data.
