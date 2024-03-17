CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       email VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(120) NOT NULL
);

CREATE TYPE difficulty_enum AS ENUM ('EASY', 'NORMAL', 'HARD');

CREATE TABLE generated_password (
                                    id SERIAL PRIMARY KEY,
                                    password VARCHAR(255),
                                    difficulty difficulty_enum,
                                    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
                            user_id INT,
                            role_id INT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

ALTER TABLE generated_password ALTER COLUMN difficulty TYPE varchar(255);
