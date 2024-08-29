create table user
(
    created_at  datetime(6)            null,
    id          bigint auto_increment
        primary key,
    modified_at datetime(6)            null,
    email       varchar(255)           null,
    password    varchar(255)           null,
    username    varchar(255)           null,
    user_role   enum ('ADMIN', 'USER') null,
    constraint UKob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table todo
(
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    todo_id     bigint auto_increment
        primary key,
    user_id     bigint       not null,
    contents    varchar(255) null,
    title       varchar(255) null,
    weather     varchar(255) null,
    constraint FK2ft3dfk1d3uw77pas3xqwymm7
        foreign key (user_id) references user (id)
);

create table comment
(
    comment_id  bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    todo_id     bigint       not null,
    user_id     bigint       not null,
    contents    varchar(255) null,
    constraint FK8kcum44fvpupyw6f5baccx25c
        foreign key (user_id) references user (id),
    constraint FKj60kiatr37jebhtjaju6d5nv2
        foreign key (todo_id) references todo (todo_id)
);

create table manager
(
    id      bigint auto_increment
        primary key,
    todo_id bigint not null,
    user_id bigint not null,
    constraint FKlx8n4x9vqj3lqv8cj9ienwrv6
        foreign key (user_id) references user (id),
    constraint FKquwlni4490qk12i1hiffa0evv
        foreign key (todo_id) references todo (todo_id)
);

