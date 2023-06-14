create table tb_questions
(
    id             integer       not null auto_increment,
    answer_explain varchar(2048),
    correct_answer integer       not null,
    question       varchar(2048) not null,
    primary key (id)
);

create table tb_questions_options
(
    tb_questions_id integer not null,
    options         varchar(2048)
);

create table user_role
(
    user_id bigint not null,
    role    varchar(255)
);

create table users
(
    user_id  bigint       not null auto_increment,
    email    varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (user_id)
);

alter table users
    add constraint user_unique_email unique (email);

alter table users
    add constraint user_unique_username unique (username);

alter table tb_questions_options
    add constraint tb_questions_options_fk foreign key (tb_questions_id) references tb_questions (id);

alter table user_role
    add constraint user_role_kf foreign key (user_id) references users (user_id);

-- INSERT INTO users(user_id, username, email, password)
-- values (1, admin, admin@dpa.kg, admindpa);
--
-- INSERT INTO user_role(user_id, role)
-- values (1, ROLE_ADMIN),
--        (1, ROLE_USER);