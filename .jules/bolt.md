## 2024-05-08 - [Optimize Delete Operation]
**Learning:** Default Spring Data JPA `deleteById` triggers unnecessary SELECT queries before deletion, which is a common anti-pattern for performance in this project.
**Action:** Use a custom `@Modifying` JPQL `@Query` for direct DELETE operations, and remember to annotate the service method with `@Transactional` for it to execute correctly.