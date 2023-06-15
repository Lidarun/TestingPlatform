CREATE SCHEMA IF NOT EXISTS public;

SET search_path TO public;

CREATE TABLE tb_questions
(
    id             SERIAL PRIMARY KEY,
    answer_explain VARCHAR(2048),
    correct_answer INTEGER       NOT NULL,
    question       VARCHAR(2048) NOT NULL
);

CREATE TABLE tb_questions_options
(
    tb_questions_id INTEGER NOT NULL,
    options         VARCHAR(2048)
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
    ADD CONSTRAINT user_role_kf FOREIGN KEY (user_id) REFERENCES users (user_id);

INSERT INTO users (user_id, username, email, password)
VALUES (1, 'admin', 'admin@dpa.kg', '$2a$10$Q8Xf77QowRLT42by43xXL.M8jjrAKt6JMCM.x./Q.NjFaLaS8sBoe'),
       (2, 'lidarun', 'nur@g.com', '$2a$10$Q8Xf77QowRLT42by43xXL.M8jjrAKt6JMCM.x./Q.NjFaLaS8sBoe');

INSERT INTO user_role (user_id, role)
VALUES (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');
