# Dev Log — PrimeLock AI

Weekly progress notes, updated most Sundays. Kept short on purpose — this is a running record, not a report.

---

## Week 1 — August 09, 2026

**Shipped:**
- Spun up isolated Docker Infrastructure (with PostgreSQL w/ pgvector & Redis)
- Mapped up database schema, prioritizing is_deleted soft deletes and HNSSW vector indexing
- Scaffolded the Spring Boot core engine and pushed the initials files to GitHub

**Blocked / struggled:**
- Local Windows Port Collisions with native database installations
- Spring Boot boot-up crashes caused by unquoted special characters

**Next Sunday:**
- Map the Java JPA entities, set up the data access repositories, and start the security layer.

**Notes:**
- Pivoted strategy: Decided to delay the Python AI microservice (v2.0) to focus entirely on building a rock-solid Spring Boot MVP first.

---

## Week 2 — August 16, 2026

**Shipped:**
- Mapped JPA domain models (User, RuleOfThree, BptSession) utilizing a DRY BaseEntity mapped superclass.
- Implemented a @RestControllerAdvice Global Exception Handler with rolling SLF4J file logging
- Scaffolded JWT Authentication architecture (JwtService & JwtAuthenticationFilter).
- Configured an enterprise GitHub Kanban board with strict YAML issue templates.

**Blocked / struggled:**
- IntelliJ resource caching hid the newly generated log files (bypassed via a hard Maven clean/recompile).

**Next Sunday:**
- Wire up the SecurityFilterChain, build the Auth controllers (login/register), and implement the Service layer business logic.

**Notes:**
- Banning Lombok's @Data on database entities was a major architectural win. The YAML issue templates are forcing me to actually scope the problem before writing code.

---

## Week 3 — August 23, 2026

**Shipped:**
-Created missing JPA Repositories and Domain models (RuleOfThreeGoal, BptSession).
-Refactored the User entity to implement Spring Security's UserDetails and strictly mapped fields to the database schema (e.g., password_hash, BPT times).
-Wired up ApplicationConfig to inject the BCrypt password encoder and UserDetailsService.
-Configured the SecurityFilterChain (SecurityConfig) to enforce stateless JWT authentication while leaving /api/v1/auth/** open.
-Implemented Auth DTOs, AuthenticationService (password hashing, DB saving, JWT generation), and AuthenticationController.
-Successfully tested end-to-end registration via Postman and generated a valid, cryptographically signed JWT.

**Blocked / struggled:**
-IntelliJ indexing cache became corrupted, causing hallucinated compiler errors (resolved via IDE Cache Invalidation).
-Spring Security 6+ deprecations: DaoAuthenticationProvider strictness no longer allows empty constructors, requiring a refactor to constructor parameter injection.
-App boot crashed due to Hibernate schema validation; the bpt_sessions table in PostgreSQL was missing the created_at column inherited from BaseEntity. Resolved via manual SQL ALTER TABLE and for future encounters,
resolved by altering application properties spring jpa ddl-auto to update.

**Next Sunday:**
-Implement a secured test endpoint to verify the JWT filter intercepts and validates tokens correctly.
-Build the BptSession Service layer to handle the core business logic of creating and reading energy/focus tracking sessions.
-Expose the REST API controller for the BPT feature

**Notes:**
- Breaking down the repository creation into distinct, granular tickets kept the Kanban board metrics clean and accurate.
- -The strict Hibernate schema validation check was a great safety net—catching the missing DB column before it could cause silent data corruption in production. The authentication engine is now officially locked, loaded, and completely stateless.

---

<!--
Copy the block above for each new week. Keep entries to ~4 short bullets max —
if an entry is taking more than 5 minutes to write, it's too long.

Monthly rollup (optional, do this in README.md, not here):
"As of [Month]: [1-2 sentence summary of where the project stands]"
-->
