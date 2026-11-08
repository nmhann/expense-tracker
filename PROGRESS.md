# Expense Tracker — Progress Log

_Last updated: 8 Nov 2026 · Current phase: **3 — Database** (Phases 1 and 2 ✅ complete)_

---

## Next task
**Phase 3, Task 4: first Flyway migration**
- Add `flyway-core` and `flyway-database-postgresql` to `pom.xml` (no version numbers) and reload Maven
- Create folder `src/main/resources/db/migration`
- Create `V1__create_users_and_categories.sql` (two underscores) with the `users` and `categories` tables from `DESIGN.md`
- Run the app and check the tables exist
- Then V2: `transactions` and `budgets`

---

## Approach
Each phase is **completed fully, then locked**. We only go back to fix real mistakes.
`DESIGN.md` is the blueprint — every phase builds what it describes.
One task at a time. Commit after every finished task.

## Phases

| # | Phase | Goal | Status | Suggested model |
|---|-------|------|--------|-----------------|
| 1 | Design | Requirements, entities, relationships, rules, API | ✅ Done | Opus |
| 2 | Setup | JDK, IntelliJ, Spring Boot project, Git/GitHub | ✅ Done | Sonnet |
| 3 | Database | PostgreSQL (Docker), JPA entities, Flyway schema from DESIGN.md | 🔶 In progress (3 of ~6 tasks) | Sonnet |
| 4 | Core backend | Transactions, categories, budgets: controller → service → repository, validation, errors, tests | ⬜ | Sonnet; Opus for end-of-phase review |
| 5 | Summary | Monthly summary calculations | ⬜ | Sonnet |
| 6 | Security | Register, login, JWT, users only see their own data | ⬜ | Opus |
| 7 | Frontend | React app using the API | ⬜ | Sonnet |
| 8 | AI | Assistant using tool use over the user's data | ⬜ | Opus to design, Sonnet to build |
| 9 | Ship | Deployment, README, diagrams, API docs | ⬜ | Sonnet |

**Phase 3 tasks**

| # | Task | Status |
|---|------|--------|
| 1 | Run PostgreSQL in Docker | ✅ |
| 2 | Add a volume so data survives | ✅ |
| 3 | Connect Spring Boot to the database (JPA, driver, env variables) | ✅ |
| 4 | Flyway V1: `users` and `categories` tables | ⬜ next |
| 5 | Flyway V2: `transactions` and `budgets` tables (foreign keys, unique constraint) | ⬜ |
| 6 | JPA entities mapped to the tables, verify at startup | ⬜ |

**Model rules of thumb**
- Sonnet: daily work — explanations, small reviews, debugging.
- Opus: design decisions, stubborn bugs (stuck 30+ min), end-of-phase reviews.
- Haiku: quick syntax lookups.

**Start a new chat at the start of each phase.** Keep `DESIGN.md` and this file in project knowledge.

---

## Completed
- [x] Project brief written
- [x] Stack and architecture agreed
- [x] JDK 25 (Temurin LTS) installed, `JAVA_HOME` set
- [x] IntelliJ installed, project JDK configured
- [x] Spring Boot project generated (Maven, Spring Web) and running
- [x] First endpoints built and tested (`/hello`, `/about`, practice code, deleted)
- [x] **Phase 1 Design complete → `DESIGN.md`**
- [x] First commit pushed to GitHub (Phase 2 complete)
- [x] Docker Desktop installed (`docker --version` works)
- [x] PostgreSQL 17 container `expense-db` running (user `expense_user`, database `expense_tracker`, port 5432)
- [x] Named volume `expense-data` mounted at `/var/lib/postgresql/data` (data survives container deletion, verified: logs said "Skipping initialization")
- [x] Added `spring-boot-starter-data-jpa` and `postgresql` driver to `pom.xml`
- [x] Datasource configured in `application.properties`; credentials come from environment variables `DB_USER` / `DB_PASSWORD` set in the IntelliJ run configuration
- [x] App starts and connects (`HikariPool-1 - Start completed`, PostgreSQL 17.11 detected)
- [x] Set `spring.jpa.open-in-view=false` (recommended to remove a startup warning)

## Concepts learned
- **Tools:** JDK vs IDE; LTS vs feature releases; `PATH` and `JAVA_HOME`; IntelliJ's per-project JDK; CLI flags (`-v` short vs `--version` long)
- **Spring Boot:** framework = prewritten plumbing; Maven + `pom.xml` = dependencies; Tomcat = built-in web server on port 8080; reading logs (look for ERROR / "Started")
- **Web:** request → response cycle; 404 = "not found"; API vs endpoint; providing vs consuming an API
- **Java:** everything lives in a class; class = blueprint, object = instance; records; `BigDecimal` for money; code lives in `src/main/java`
- **Controller vs Service:** controller handles HTTP; service holds business logic and doesn't know HTTP exists
- **Design process:** requirements → entities → relationships → rules → API
- **Data modeling:** primary key, foreign key, one-to-many, unique constraint, derived data ("store facts, calculate conclusions")
- **REST/API design:** resources, HTTP methods, `{id}` in path, query parameters, CRUD, GET has no body, never put user ID or passwords in URLs
- **Docker:** image = template, container = running copy, volume = storage that outlives the container; `docker run` flags (`--name`, `-e`, `-p`, `-v`, `-d`); `docker ps`, `logs`, `stop`, `start`; the CLI is only a remote control, Docker Desktop's engine must be running
- **Config & secrets:** environment variables keep secrets out of code and Git; `${DB_USER}` placeholders in `application.properties`
- **Database connection:** JDBC URL anatomy (`jdbc:postgresql://host:port/database`); driver vs JPA vs Hibernate vs Hikari connection pool
- **Debugging logs:** find the *first* `FATAL` / `Caused by` line; the long stack trace after it is just consequences

## Still unclear / knowledge gaps
- Spring's hidden magic: how annotations like `@RestController` work, beans, dependency injection (introduced, not yet understood — comes up in Phase 4)
- Git workflow beyond basics (commit habit started; branches not yet used)
- Rule vs relationship mix-up from design (one budget per category per month is a *rule*, not a 1-to-1 relationship). Watch for this when writing the budgets table.
- What JPA/Hibernate actually do in detail (introduced; becomes concrete in Task 6)

---

## Decisions made

| Decision | Choice | Reason |
|----------|--------|--------|
| Backend | Java 25 (LTS) + Spring Boot 4.1.2 + Maven | Matches Lebanese job market |
| Database | PostgreSQL 17 + Spring Data JPA + Flyway | Industry standard; versioned schema |
| Frontend | React (Vite, JavaScript; TypeScript later) | Most common pairing with Spring; limits new concepts |
| Auth | Spring Security + JWT | Standard for separate frontend + API |
| Testing | JUnit 5, Mockito, Spring Boot Test | Standard Java testing stack |
| AI | Claude API, official Java SDK, tool use | AI calls safe backend functions; never writes SQL or sees other users' data |
| Local tooling | Docker only for running Postgres | Minimal, useful exposure |
| Data model | Income + expense = one `Transaction` with `type` | Less duplication, simpler API |
| Amounts | Always positive; `type` gives direction | One source of truth, fewer bugs |
| Categories | Fixed shared list (MVP) | Custom categories are "later" |
| Summary | Calculated, never stored | Stored copies go stale |
| Currency | One currency per user (MVP) | Multi-currency is future work |
| Package | `com.nour.expense_tracker` | Reverse-domain naming convention |
| DB persistence | Docker named volume `expense-data` | Data survives container removal |
| Secrets | Environment variables via IntelliJ run config (not committed) | Passwords never reach GitHub |
| Flyway timing | Added in Task 4, not with JPA | Flyway fails at startup with no migration scripts |
| Table naming | `users` (plural), `snake_case` columns | `user` is a reserved word in Postgres |
| Excluded | Microservices, Kubernetes, Redis, Kafka, GraphQL | Overkill for project size |

---

## Project structure
```
expense-tracker/
├── pom.xml
├── DESIGN.md
├── PROGRESS.md
└── src/main/
    ├── java/com/nour/expense_tracker/
    │   └── ExpenseTrackerApplication.java
    └── resources/
        └── application.properties
```
_Coming in Task 4:_ `src/main/resources/db/migration/V1__create_users_and_categories.sql`

## Challenges & solutions (for README / interviews)
- **Installed a non-LTS Java (27) by mistake** → switched to Temurin 25 LTS; learned why companies standardize on LTS releases.
- **"Project JDK is not defined" in IntelliJ** → IntelliJ doesn't use `JAVA_HOME` automatically; set the project SDK explicitly.
- **Started coding before designing** → stopped and did requirements → entities → relationships → API first; every later decision now traces back to `DESIGN.md`.
- **Multi-line `docker run` broke when pasted into Command Prompt** → the line break split the command; use one line (or `^` continuation in cmd).
- **`failed to connect to the docker API`** → Docker Desktop's engine wasn't running; the `docker` CLI only talks to it.
- **`database "___" does not exist`** → a leftover placeholder in the datasource URL; fixed by reading the first `FATAL` line instead of the 100-line stack trace.
- **Made data survive container deletion** → added a named volume and proved it from the logs ("Skipping initialization").