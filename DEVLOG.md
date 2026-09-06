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
- Created missing JPA Repositories and Domain models (RuleOfThreeGoal, BptSession).
- Refactored the User entity to implement Spring Security's UserDetails and strictly mapped fields to the database schema (e.g., password_hash, BPT times).
- Wired up ApplicationConfig to inject the BCrypt password encoder and UserDetailsService.
- Configured the SecurityFilterChain (SecurityConfig) to enforce stateless JWT authentication while leaving /api/v1/auth/** open.
- Implemented Auth DTOs, AuthenticationService (password hashing, DB saving, JWT generation), and AuthenticationController.
- Successfully tested end-to-end registration via Postman and generated a valid, cryptographically signed JWT.

**Blocked / struggled:**
- IntelliJ indexing cache became corrupted, causing hallucinated compiler errors (resolved via IDE Cache Invalidation).
- Spring Security 6+ deprecations: DaoAuthenticationProvider strictness no longer allows empty constructors, requiring a refactor to constructor parameter injection.
- App boot crashed due to Hibernate schema validation; the bpt_sessions table in PostgreSQL was missing the created_at column inherited from BaseEntity. Resolved via manual SQL ALTER TABLE and for future encounters, resolved by altering application properties spring jpa ddl-auto to update.

**Next Sunday:**
- Implement a secured test endpoint to verify the JWT filter intercepts and validates tokens correctly.
- Build the BptSession Service layer to handle the core business logic of creating and reading energy/focus tracking sessions.
- Expose the REST API controller for the BPT feature

**Notes:**
- Breaking down the repository creation into distinct, granular tickets kept the Kanban board metrics clean and accurate.
- The strict Hibernate schema validation check was a great safety net—catching the missing DB column before it could cause silent data corruption in production. The authentication engine is now officially locked, loaded, and completely stateless.

---

## Week 4 - September 06, 2026

**Shipped:**
- Implemented DemoController as a secured test endpoint to successfully verify that the JWT filter intercepts, validates, and admits/rejects requests properly.
- Built the BptSession Data Transfer Objects (BptSessionRequest, BptSessionResponse) to strictly control the data flowing in and out of the API.
- Developed the BptSessionService layer to map DTOs to the BptSession database entity and handle database saves/retrievals using java.util.UUID.
- Exposed the BptSessionController REST API, utilizing Spring Security’s Principal object to securely extract the user's identity from the JWT wristband rather than trusting the frontend.
- Implemented Auth DTOs, AuthenticationService (password hashing, DB saving, JWT generation), and AuthenticationController.
- Upgraded the Biological Prime Time tracking granularity by removing the rigid 1-5 database constraints in PostgreSQL and shifting the domain model to a 1-10 scale.

**Blocked / struggled:**
- Server threw a 500 SignatureException when hitting the test endpoint. Root cause: The JWT secret key regenerates on server boot, making last week's token invalid. Resolved by re-authenticating.
- Hit a NoResourceFoundException on the new DemoController. Root cause: A microscopic typo in the mapping annotation (/ap1/v1/demo instead of /api/v1/demo).
- Domain model mapping mismatch: The Service layer tried to use setUser() but the entity was mapped using a primitive foreign key (userId). Resolved by switching to setUserId() and adding missing startTime and endTime fields to the BptSession Java entity.
- Encountered a 409 Data Integrity Conflict when saving a BPT session. PostgreSQL's hardcoded check constraints (bpt_sessions_energy_rating_check) rejected ratings higher than 5. A SQL script fix failed due to DBeaver transaction rollbacks, so it was resolved by visually deleting the constraints directly in the DBeaver UI.

**Next Sunday:**
- Add logs/ and *.log to .gitignore to prevent server logs from polluting the repository.
- Implement BPT Session Date Filtering (Issue #13) so the frontend can query specific date ranges for graphs.
- Build the Rule of Three Service Layer (Issue #14) for daily priority tracking.
- Expose the Rule of Three REST API (Issue #15) to allow creating and toggling daily goals.

**Notes:**
- Relying on the Principal object for user identification is a massive security win—it completely prevents users from spoofing IDs in the JSON body.
- The 409 Conflict error was a great example of the database doing exactly what it was supposed to do. Hardcoded SQL constraints are a fantastic last line of defense against invalid data, even if it takes a minute to figure out why they tripped!
- DBeaver's visual constraint editor is a lifesaver when raw SQL scripts get tangled up in partial rollbacks.
<!--
Copy the block above for each new week. Keep entries to ~4 short bullets max —
if an entry is taking more than 5 minutes to write, it's too long.

Monthly rollup (optional, do this in README.md, not here):
"As of [Month]: [1-2 sentence summary of where the project stands]"
-->
