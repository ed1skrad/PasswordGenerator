CREATE TYPE Difficulty AS ENUM ('EASY', 'NORMAL', 'HARD');

CREATE TABLE generated_passwords (
    id SERIAL PRIMARY KEY,
    password VARCHAR NOT NULL,
    difficulty SMALLINT  NOT NULL
);
