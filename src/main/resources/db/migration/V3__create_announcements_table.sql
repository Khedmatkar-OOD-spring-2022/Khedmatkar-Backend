create table announcements
(
    id          bigserial not null
        constraint announcements_pkey
            primary key,
    creation    timestamp not null default now(),
    last_update timestamp not null default now(),
    uuid        uuid      not null
        constraint announcements_uuid_uindex
            unique                 default uuid_generate_v4(),
    version     integer   not null default 0,
    user_id     bigint    not null
        constraint announcements_users_fk
            references users,
    subject     VARCHAR(255),
    message     varchar(1024)
);