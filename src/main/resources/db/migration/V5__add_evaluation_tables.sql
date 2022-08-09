create table answer_contents
(
    id           bigserial
        primary key,
    creation     timestamp,
    last_update  timestamp,
    uuid         uuid
        constraint uk_fkvnqypk4v1mbwvygos4ggpo7
            unique,
    version      integer,
    content_type varchar(255)
);

alter table answer_contents
    owner to postgres;

create table double_choice_answers
(
    answer_choice varchar(255),
    id            bigint not null
        primary key
        constraint fkic94fr928okx9do982wpth1ut
            references answer_contents
);

alter table double_choice_answers
    owner to postgres;

create table feedbacks
(
    id bigint not null
        primary key
        constraint fkrvpt581i0r4fk8a63dgj21x8u
            references tickets
);

alter table feedbacks
    owner to postgres;

create table multiple_choice_answers
(
    id bigint not null
        primary key
        constraint fk1ery7h8piee1txl11rygu2lag
            references answer_contents
);

alter table multiple_choice_answers
    owner to postgres;

create table multiple_choice_answer_answer_choices
(
    multiple_choice_answer_id bigint not null
        constraint fk3mqlbd5uh98000r35p7uor5ts
            references multiple_choice_answers,
    answer_choices            integer
);

alter table multiple_choice_answer_answer_choices
    owner to postgres;

create table question_contents
(
    id            bigserial
        primary key,
    creation      timestamp,
    last_update   timestamp,
    uuid          uuid
        constraint uk_gx7450wt5937bcu472adcyklu
            unique,
    version       integer,
    content_type  varchar(255),
    question_text varchar(255)
);

alter table question_contents
    owner to postgres;

create table double_choice_questions
(
    choice1 varchar(255),
    choice2 varchar(255),
    id      bigint not null
        primary key
        constraint fk77iego1a0kkf2u00jo3ogmifh
            references question_contents
);

alter table double_choice_questions
    owner to postgres;

create table multiple_choice_questions
(
    is_single_selection boolean,
    id                  bigint not null
        primary key
        constraint fk4pge80p38buvif6ankwexl1rw
            references question_contents
);

alter table multiple_choice_questions
    owner to postgres;

create table multiple_choice_question_choices
(
    multiple_choice_question_id bigint not null
        constraint fkp5h2pay1hlcgqgwp7xwt1hlnm
            references multiple_choice_questions,
    choices                     varchar(255)
);

alter table multiple_choice_question_choices
    owner to postgres;

create table questions
(
    id            bigserial
        primary key,
    creation      timestamp,
    last_update   timestamp,
    uuid          uuid
        constraint uk_nhs7x0aa4xjreccx7wpo0bc45
            unique,
    version       integer,
    answerer_type varchar(255),
    content_id    bigint
        constraint fk27pxfwrm5rvocpu10l0w65b10
            references question_contents
);

alter table questions
    owner to postgres;

create table answers
(
    id                 bigserial
        primary key,
    creation           timestamp,
    last_update        timestamp,
    uuid               uuid
        constraint uk_412ifc72yywh3y0lq6h9o20rf
            unique,
    version            integer,
    answerer_id        bigint
        constraint fks5qkvw5mqepgcp8fylsh5ufqt
            references users,
    content_id         bigint
        constraint fk5syfn987tdkf6b3gpvcjso2ob
            references answer_contents,
    question_id        bigint
        constraint fk3erw1a3t0r78st8ty27x6v3g1
            references questions,
    service_request_id bigint
        constraint fkd8dfk7e79t2n42qaglg8wfg2l
            references service_requests
);

alter table answers
    owner to postgres;

create table score_answers
(
    score integer,
    id    bigint not null
        primary key
        constraint fk2yxbqe1ia49musyskp8g2jqmj
            references answer_contents
);

alter table score_answers
    owner to postgres;

create table score_questions
(
    max_score integer,
    min_score integer,
    id        bigint not null
        primary key
        constraint fk74bqkq120d76wx7fcv6k0s4ta
            references question_contents
);

alter table score_questions
    owner to postgres;

create table technical_issue
(
    status varchar(255),
    id     bigint not null
        primary key
        constraint fkh580e9q4kwhppjp1gm82j95wp
            references tickets
);

alter table technical_issue
    owner to postgres;

create table technical_issue_answers
(
    technical_issue_id bigint not null
        constraint fkaie6d3sf2c7qcjlq1jnm1l3pr
            references technical_issue,
    answers_id         bigint not null
        constraint uk_mbndbelvlmgc8a1hkes7cd5i1
            unique
        constraint fk4bksuxt61b5i0lu3resnshiin
            references tickets
);

alter table technical_issue_answers
    owner to postgres;

create table text_answers
(
    text varchar(255),
    id   bigint not null
        primary key
        constraint fk8dq4xuw9k06nerxgsngnsts5m
            references answer_contents
);

alter table text_answers
    owner to postgres;

create table text_questions
(
    answer_word_length integer,
    id                 bigint not null
        primary key
        constraint fkor0y3yfoh85gatkglr1emav6v
            references question_contents
);

alter table text_questions
    owner to postgres;

