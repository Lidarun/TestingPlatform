CREATE SCHEMA IF NOT EXISTS public;

SET search_path TO public;

CREATE TABLE tb_courses
(
    id         BIGSERIAL NOT NULL,
    name       VARCHAR(255),
    state      BOOLEAN   NOT NULL,
    path_image TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE tb_questions
(
    id             BIGSERIAL    NOT NULL,
    correct_answer INTEGER,
    course_id      BIGINT       NOT NULL,
    answer_explain TEXT,
    question       TEXT,
    PRIMARY KEY (id),
    CONSTRAINT tb_questions_fk FOREIGN KEY (course_id) REFERENCES tb_courses (id)
);

CREATE TABLE tb_questions_options
(
    tb_questions_id BIGSERIAL NOT NULL,
    options         TEXT
);

CREATE TABLE user_role
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(255)
);

CREATE TABLE users
(
    user_id  BIGSERIAL PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT user_unique_email UNIQUE (email);
ALTER TABLE users
    ADD CONSTRAINT user_unique_username UNIQUE (username);

ALTER TABLE tb_questions_options
    ADD CONSTRAINT tb_questions_options_fk FOREIGN KEY (tb_questions_id) REFERENCES tb_questions (id);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk FOREIGN KEY (user_id) REFERENCES users (user_id);

INSERT INTO users (user_id, username, email, password)
VALUES (1, 'admin', 'admin@dpa.kg', '$2a$10$Q8Xf77QowRLT42by43xXL.M8jjrAKt6JMCM.x./Q.NjFaLaS8sBoe'),
       (2, 'lidarun', 'nur@g.com', '$2a$10$Q8Xf77QowRLT42by43xXL.M8jjrAKt6JMCM.x./Q.NjFaLaS8sBoe');

INSERT INTO user_role (user_id, role)
VALUES (1, 'ROLE_SUPER_ADMIN'),
       (2, 'ROLE_SUPER_ADMIN');