CREATE TABLE users (
                                 id            BIGSERIAL PRIMARY KEY,
                                 email         VARCHAR(255) NOT NULL UNIQUE,
                                 password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
                                 id            BIGSERIAL PRIMARY KEY,
                                 name          VARCHAR(100) NOT NULL UNIQUE
);