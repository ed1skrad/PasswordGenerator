CREATE TYPE difficulty_enum AS ENUM ('EASY', 'NORMAL', 'HARD');

CREATE TABLE generated_password (
                                    id SERIAL PRIMARY KEY,
                                    password VARCHAR(255),
                                    difficulty difficulty_enum
);

ALTER TABLE generated_password ALTER COLUMN difficulty SET DATA TYPE smallint USING CASE
    WHEN difficulty = 'EASY' THEN 0
    WHEN difficulty = 'NORMAL' THEN 1
    WHEN difficulty = 'HARD' THEN 2
    ELSE NULL
END;
