create table question
(
    question_id        bigint  not null,
    accepted_answer_id bigint  not null,
    answer_count       integer not null,
    closed_date        bigint  not null,
    creation_date      bigint  not null,
    is_answered        boolean,
    last_activity_date bigint  not null,
    last_edit_date     bigint  not null,
    link               varchar(510),
    score              integer not null,
    title              varchar(510),
    view_count         integer not null,
    primary key (question_id)
);

create table question_tags
(
    question_question_id bigint not null,
    tags                 varchar(255)
);


alter table if exists question_tags
    add constraint question_tags_id_fk
        foreign key (question_question_id)
            references question;