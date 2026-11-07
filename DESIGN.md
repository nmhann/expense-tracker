# Expense Tracker — Design

_Status: **locked** (v1, 6 Nov 2026). Changes only to fix real mistakes._

How this was designed: **requirements → entities → relationships → rules → API.**
Each step was decided from the one before it.

---

## 1. Requirements (MVP user stories)

1. A user can **register and log in**.
2. A user can **add an expense or income**, with an amount, date, description, and category.
3. A user can **edit and delete** their own transactions.
4. A user can **view their transactions**, filtered by date range and category.
5. A user can **set a monthly budget** for a category.
6. A user can **see a monthly summary**: total income, total expenses, spending per category, and comparison with last month.
7. A user can **ask the AI questions** about their own spending.

**Later (not MVP):** recurring transactions · CSV export · richer charts · custom categories
**Out of scope:** bank sync · receipt OCR · households · multi-currency

---

## 2. Entities

Found by taking the nouns from the requirements and asking: *is this a thing the app must remember, or a detail describing a thing?*

### User
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| email | String | Unique |
| passwordHash | String | Never store the plain password |

### Category
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| name | String | e.g. Food, Transport, Rent |

### Transaction
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| userId | Long | Foreign key → User |
| categoryId | Long | Foreign key → Category |
| type | `INCOME` / `EXPENSE` | |
| amount | BigDecimal | Always positive. `BigDecimal`, never `double` (no rounding errors) |
| description | String | |
| date | LocalDate | |

### Budget
| Field | Type | Notes |
|---|---|---|
| id | Long | Primary key |
| userId | Long | Foreign key → User |
| categoryId | Long | Foreign key → Category |
| month | YearMonth | A budget covers a whole month, e.g. `2026-11` |
| amount | BigDecimal | Always positive |

**Not an entity: Summary.** It is *derived data*, calculated from transactions on every request.
Rule of thumb: **store facts, calculate conclusions.** A stored summary would go stale when a transaction changes.

---

## 3. Relationships

Tested by asking in both directions ("can one A have many B? can one B have many A?").

| Relationship | Type |
|---|---|
| User → Transactions | One-to-many |
| User → Budgets | One-to-many |
| Category → Transactions | One-to-many |
| Category → Budgets | One-to-many |

A relationship is stored as a **foreign key** on the "many" side (e.g. `Transaction.userId`).

---

## 4. Business rules

1. **Categories are a fixed, shared list.** Every user picks from the same list (custom categories are "later").
2. **`type` is `INCOME` or `EXPENSE`**, nothing else.
3. **`amount` is always positive.** `type` decides whether money goes in or out.
4. **One budget per user, per category, per month.** Enforced as a unique constraint on (user, category, month).
5. **A user only ever sees and changes their own data.** The user is identified from their login, **never** from the URL.

---

## 5. API

REST style: URLs name *things* (plural nouns), the HTTP method is the verb, `{id}` targets one item, filters go after `?`. `GET` requests never have a body. Passwords never appear in URLs.

### Auth
| # | Action | Method & path | Body |
|---|---|---|---|
| 1 | Register | `POST /auth/register` | email, password |
| 2 | Log in | `POST /auth/login` | email, password |

### Transactions
| # | Action | Method & path | Body |
|---|---|---|---|
| 3 | Add | `POST /transactions` | amount, type, description, date, categoryId |
| 4 | View (filters optional) | `GET /transactions?from=&to=&categoryId=` | — |
| 5 | Edit | `PUT /transactions/{id}` | amount, type, description, date, categoryId |
| 6 | Delete | `DELETE /transactions/{id}` | — |

### Categories
| # | Action | Method & path | Body |
|---|---|---|---|
| 7 | List all | `GET /categories` | — |

### Budgets
| # | Action | Method & path | Body |
|---|---|---|---|
| 8 | Set | `POST /budgets` | categoryId, month, amount |
| 9 | View for a month | `GET /budgets?month=2026-11` | — |
| 10 | Change | `PUT /budgets/{id}` | categoryId, month, amount |
| 11 | Delete | `DELETE /budgets/{id}` | — |

### Summary
| # | Action | Method & path | Body |
|---|---|---|---|
| 12 | Monthly summary | `GET /summary?month=2026-11` | — |

### AI assistant
| # | Action | Method & path | Body |
|---|---|---|---|
| 13 | Ask a question | `POST /assistant/questions` | question |

The AI's internals (tool use over the user's own data) are designed in the AI phase.

---

## 6. Decided during the build (not part of this design)
- Exact response formats and error format
- HTTP status codes per endpoint
- Validation details (e.g. max description length)
