## 2026-05-04 - Eliminate Redundant Database Reads in Controllers
**Learning:** Layered architectures (Controller -> Service -> Repository) can easily hide redundant database operations. Using `existsById()` or streamlining Service logic to return `Optional` can eliminate N+1 like query patterns during updates/deletes where both the controller and the service end up fetching the same entity by its ID.
**Action:** Audit Spring MVC controllers passing IDs to services to ensure they are not making pre-emptive `findById` checks when the service layer can simply handle the operation and return an `Optional` or boolean indicating success.

## 2026-05-04 - Spring Data JPA Optimization Caveats
**Learning:** Optimizing redundant `findById` calls by using `existsById` or returning an unmanaged entity from a service layer can still trigger secondary `SELECT` statements via Hibernate's `merge()` during `save()`, or Spring Data's default implementation of `deleteById`.
**Action:** When streamlining service methods to prevent pre-emptive controller lookups, use `@Transactional` to ensure entities remain managed during updates, and use `@Modifying` queries for deletions to definitively prevent hidden `SELECT` queries.
