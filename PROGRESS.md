# Expense Tracker — Progress Log

_Last updated: 6 Nov 2026 · Current phase: **2 — Setup** (Phase 1 Design ✅ complete)_

---

## Next task
**Phase 2, final task: Save the foundation to GitHub**
- Make sure `HelloController` is deleted (it was practice code)
- `git init` → first commit → create empty GitHub repo `expense-tracker` → push
- Add `DESIGN.md` and this file to the repo

---

## Approach
Each phase is **completed fully, then locked**. We only go back to fix real mistakes.
`DESIGN.md` is the blueprint — every phase builds what it describes.

## Phases

| # | Phase | Goal | Status | Suggested model |
|---|-------|------|--------|-----------------|
| 1 | Design | Requirements, entities, relationships, rules, API | ✅ Done | Opus |
| 2 | Setup | JDK, IntelliJ, Spring Boot project, Git/GitHub | 🔶 Git left | Sonnet |
| 3 | Database | PostgreSQL (Docker), JPA entities, Flyway schema from DESIGN.md | ⬜ | Sonnet |
| 4 | Core backend | Transactions, categories, budgets: controller → service → repository, validation, errors, tests | ⬜ | Sonnet; Opus for end-of-phase review |
| 5 | Summary | Monthly summary calculations | ⬜ | Sonnet |
| 6 | Security | Register, login, JWT, users only see their own data | ⬜ | Opus |
| 7 | Frontend | React app using the API | ⬜ | Sonnet |
| 8 | AI | Assistant using tool use over the user's data | ⬜ | Opus to design, Sonnet to build |
| 9 | Ship | Deployment, README, diagrams, API docs | ⬜ | Sonnet |

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
- [x] First endpoints built and tested (`/hello`, `/about` — practice, to be deleted)
- [x] **Phase 1 Design complete → `DESIGN.md`**
- [ ] First commit pushed to GitHub

## Concepts learned
- **Tools:** JDK vs IDE; LTS vs feature releases; `PATH` and `JAVA_HOME`; IntelliJ's per-project JDK; CLI flags (`-v` short vs `--version` long)
- **Spring Boot:** framework = prewritten plumbing; Maven + `pom.xml` = dependencies; Tomcat = built-in web server on port 8080; reading logs (look for ERROR / "Started")
- **Web:** request → response cycle; 404 = "not found" (server is up, nothing mapped); API vs endpoint; providing vs consuming an API
- **Java:** everything lives in a class; class = blueprint, object = instance; records for data-only classes; `BigDecimal` for money; code must live in `src/main/java`
- **Controller vs Service:** controller handles HTTP; service holds business logic and doesn't know HTTP exists
- **Design process:** requirements → entities (nouns: thing or detail?) → relationships (ask both directions) → rules → API
- **Data modeling:** primary key, foreign key, one-to-many, unique constraint, derived data ("store facts, calculate conclusions")
- **REST/API design:** resources, HTTP methods, `{id}` in path, query parameters, CRUD, GET has no body, never put user ID or passwords in URLs

## Still unclear / knowledge gaps
- Spring's hidden magic: how annotations like `@RestController` work, beans, dependency injection (introduced, not yet understood — comes up in Phase 4)
- Git workflow beyond basics (first commit still pending)
- One mix-up fixed during design: confused a *rule* (one budget per category per month) with a *relationship* (1-to-1). Watch for this again in Phase 3.

---

## Decisions made

| Decision | Choice | Reason |
|----------|--------|--------|
| Backend | Java 25 (LTS) + Spring Boot + Maven | Matches Lebanese job market |
| Database | PostgreSQL + Spring Data JPA + Flyway | Industry standard; versioned schema |
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
| Excluded | Microservices, Kubernetes, Redis, Kafka, GraphQL | Overkill for project size |

---

## Project structure
```
expense-tracker/
├── pom.xml
├── DESIGN.md
├── PROGRESS.md
└── src/main/java/com/nour/expense_tracker/
    └── ExpenseTrackerApplication.java
```

## Challenges & solutions (for README / interviews)
- **Installed a non-LTS Java (27) by mistake** → switched to Temurin 25 LTS; learned why companies standardize on LTS releases.
- **"Project JDK is not defined" in IntelliJ** → IntelliJ doesn't use `JAVA_HOME` automatically; set the project SDK explicitly.
- **Started coding before designing** → stopped and did requirements → entities → relationships → API first; every later decision now traces back to `DESIGN.md`.
