CREATE TABLE transactions (
                              id               BIGSERIAL PRIMARY KEY,
                              user_id          BIGINT NOT NULL REFERENCES users(id),
                              category_id      BIGINT NOT NULL REFERENCES categories(id),
                              type             VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
                              amount           NUMERIC(12, 2) NOT NULL CHECK (amount > 0),
                              description      VARCHAR(255),
                              date             DATE NOT NULL
);

CREATE TABLE budgets (
                         id               BIGSERIAL PRIMARY KEY,
                         user_id          BIGINT NOT NULL REFERENCES users(id),
                         category_id      BIGINT NOT NULL REFERENCES categories(id),
                         month            DATE NOT NULL,
                         amount           NUMERIC(12, 2) NOT NULL CHECK (amount > 0),
                         UNIQUE (user_id, category_id, month)
);