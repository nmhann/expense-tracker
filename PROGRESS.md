# Expense Tracker — Progress Log

_Last updated: 9 Nov 2026 · Current phase: **3 — Database** (Phases 1 and 2 ✅ complete)_

---

## Next task
**Phase 3, Task 5: Flyway V2 — `transactions` and `budgets` tables**
- Create `src/main/resources/db/migration/V2__create_transactions_and_budgets.sql`
- Foreign keys: `user_id` → `users(id)`, `category_id` → `categories(id)` (type must be `BIGINT`)
- `CHECK` constraints for the business rules: transaction type is `INCOME`/`EXPENSE`; amounts are positive
- Budgets: `UNIQUE (user_id, category_id, month)` (rule 4 in `DESIGN.md`)
- Amounts as `NUMERIC(12, 2)`; dates as `DATE`; budget `month` stored as `DATE` (first day of the month — my proposal, challenge it)
- Send the SQL for review **before** running the app. Once V2 runs, never edit it.
- Verify with: `docker exec -it expense-db psql -U expense_user -d expense_tracker -c "\d transactions"`

**Also open:** tick/commit Task 4 if not yet pushed (`git status` first).

**Thinking question for Task 6:** `categories` is a fixed shared list, but the table is empty. Who inserts Food, Transport, Rent?

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
| 3 | Database | PostgreSQL (Docker), JPA entities, Flyway schema from DESIGN.md | 🔶 In progress (4 of 7 tasks) | Sonnet |
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
| 4 | Flyway V1: `users` and `categories` tables | ✅ |
| 5 | Flyway V2: `transactions` and `budgets` tables (foreign keys, checks, unique constraint) | ⬜ next |
| 6 | Seed the fixed category list (Flyway V3 inserting Food, Transport, Rent, …) | ⬜ |
| 7 | JPA entities mapped to the tables, verify at startup | ⬜ |

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
- [x] Set `spring.jpa.open-in-view=false` (startup warning gone)
- [x] Added `spring-boot-starter-flyway` and `flyway-database-postgresql` to `pom.xml` (Spring Boot 4 needs the starter, plain `flyway-core` alone is not enough)
- [x] **Flyway V1 applied:** `users` and `categories` tables created (`Successfully applied 1 migration ... now at version v1`)

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
- **Flyway & migrations:** a migration is one numbered SQL file (`V1__name.sql`, two underscores) making one change; Flyway runs them in order and records them in `flyway_schema_history`; never edit a migration that already ran (checksum), add a new one instead
- **SQL basics:** translating a design table to `CREATE TABLE` (entity → plural table, `camelCase` → `snake_case`); `BIGSERIAL PRIMARY KEY` (already unique, so no extra `UNIQUE`); `NOT NULL` vs `UNIQUE` are independent; `VARCHAR` lengths
- **Checking the database:** `docker exec -it expense-db psql -U expense_user -d expense_tracker -c "\d users"`
- **How the app reaches Docker:** no direct link; just a port (`-p 5432:5432` + `localhost:5432` in the JDBC URL), so the container must be running

## Still unclear / knowledge gaps
- Spring's hidden magic: how annotations like `@RestController` work, beans, dependency injection (introduced, not yet understood — comes up in Phase 4)
- Git workflow beyond basics (commit habit started; branches not yet used)
- Rule vs relationship mix-up from design (one budget per category per month is a *rule*, not a 1-to-1 relationship). Watch for this when writing the budgets table.
- What JPA/Hibernate actually do in detail (introduced; becomes concrete in Task 7)
- Foreign keys, `CHECK` constraints and combined `UNIQUE` (explained with an example, not yet written by me — V2 is the practice)

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
| Flyway dependencies | `spring-boot-starter-flyway` + `flyway-database-postgresql` | Spring Boot 4 moved Flyway auto-configuration into its own starter |
| `password_hash` length | `VARCHAR(255)` | Headroom if the hashing algorithm changes |
| Budget `month` column | `DATE` holding the first day of the month (proposed, confirm in V2) | Postgres has no year-month type |
| `open-in-view` | `false` | Avoids hidden late queries; easier to set now than later |
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
        ├── application.properties
        └── db/migration/
            └── V1__create_users_and_categories.sql
```
_Coming in Task 5:_ `V2__create_transactions_and_budgets.sql`

## Challenges & solutions (for README / interviews)
- **Installed a non-LTS Java (27) by mistake** → switched to Temurin 25 LTS; learned why companies standardize on LTS releases.
- **"Project JDK is not defined" in IntelliJ** → IntelliJ doesn't use `JAVA_HOME` automatically; set the project SDK explicitly.
- **Started coding before designing** → stopped and did requirements → entities → relationships → API first; every later decision now traces back to `DESIGN.md`.
- **Multi-line `docker run` broke when pasted into Command Prompt** → the line break split the command; use one line (or `^` continuation in cmd).
- **`failed to connect to the docker API`** → Docker Desktop's engine wasn't running; the `docker` CLI only talks to it.
- **`database "___" does not exist`** → a leftover placeholder in the datasource URL; fixed by reading the first `FATAL` line instead of the 100-line stack trace.
- **Made data survive container deletion** → added a named volume and proved it from the logs ("Skipping initialization").
- **Flyway dependency differs in Spring Boot 4** → plain `flyway-core` isn't enough; used `spring-boot-starter-flyway` plus the Postgres module. Lesson: check what the framework version expects, and verify from the logs (`Successfully applied 1 migration`).
- **Redundant `PRIMARY KEY UNIQUE`** caught in review → a primary key is already unique; extra constraints create duplicate indexes.