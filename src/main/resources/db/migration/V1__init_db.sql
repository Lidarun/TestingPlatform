CREATE SCHEMA IF NOT EXISTS public;

SET search_path TO public;

CREATE TABLE IF NOT EXISTS tb_courses
(
    id  BIGSERIAL NOT NULL,
    name       VARCHAR(255),
    key        VARCHAR(255),
    state      BOOLEAN   NOT NULL,
    access     BOOLEAN   NOT NULL,
    path_image TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_modules
(
    id        BIGSERIAL NOT NULL,
    name      VARCHAR(255),
    state     BOOLEAN   NOT NULL,
    course_id BIGINT    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (course_id) REFERENCES tb_courses(id)
);

CREATE TABLE IF NOT EXISTS tb_questions
(
    id             BIGSERIAL NOT NULL,
    correct_answer INTEGER,
    module_id      BIGINT    NOT NULL,
    answer_explain TEXT,
    question       TEXT,
    PRIMARY KEY (id),
    CONSTRAINT tb_questions_fk FOREIGN KEY (module_id) REFERENCES tb_modules (id)
);

CREATE TABLE IF NOT EXISTS tb_questions_options
(
    tb_questions_id BIGSERIAL NOT NULL,
    options         TEXT,
    FOREIGN KEY (tb_questions_id) references tb_questions (id)
);

CREATE TABLE IF NOT EXISTS tb_results
(
    id           BIGSERIAL NOT NULL,
    answer_index BIGINT    NOT NULL,
    module_id    BIGINT    NOT NULL,
    question_id  BIGINT    NOT NULL,
    user_id      BIGINT    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tb_users
(
    user_id   BIGSERIAL NOT NULL,
    email     VARCHAR(255),
    full_name VARCHAR(255),
    password  VARCHAR(255),
    username  VARCHAR(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS tb_users_courses
(
    course_id BIGINT NOT NULL,
    user_id   BIGINT NOT NULL,
    PRIMARY KEY (course_id, user_id),
    FOREIGN KEY (course_id) REFERENCES tb_courses (id),
    FOREIGN KEY (user_id) REFERENCES tb_users (user_id)
);


CREATE TABLE IF NOT EXISTS tb_users_roles
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(255) CHECK (role IN ('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_TEACHER', 'ROLE_USER')),
    FOREIGN KEY (user_id) REFERENCES tb_users (user_id)
);


ALTER TABLE tb_users
    ADD CONSTRAINT user_unique_email UNIQUE (email);
ALTER TABLE tb_users
    ADD CONSTRAINT user_unique_username UNIQUE (username);

ALTER TABLE tb_questions_options
    ADD CONSTRAINT tb_questions_options_fk FOREIGN KEY (tb_questions_id) REFERENCES tb_questions (id);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT user_role_fk FOREIGN KEY (user_id) REFERENCES tb_users (user_id);

INSERT INTO tb_users (user_id, username, email, full_name, password)
VALUES (1, 'admin', 'admin@dpa.kg', 'Лорд Сноу', '$2a$10$Q8Xf77QowRLT42by43xXL.M8jjrAKt6JMCM.x./Q.NjFaLaS8sBoe'),
       (2, 'lidarun', 'nur@g.com', 'Лорд Севера','$2a$10$Q8Xf77QowRLT42by43xXL.M8jjrAKt6JMCM.x./Q.NjFaLaS8sBoe');

INSERT INTO tb_users_roles (user_id, role)
VALUES (1, 'ROLE_SUPER_ADMIN'),
       (2, 'ROLE_SUPER_ADMIN');