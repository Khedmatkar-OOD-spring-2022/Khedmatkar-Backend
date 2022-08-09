drop table tickets_answers cascade;
drop table tickets cascade;

create table tickets
(
    dtype       varchar(31) not null,
    id          bigserial
        primary key,
    creation    timestamp,
    last_update timestamp,
    uuid        uuid
        constraint uk_h559x60if8ak48teo0sfvjg8t
            unique,
    version     integer,
    content     varchar(255),
    title       varchar(255),
    status      varchar(255),
    writer_id   bigint
        constraint fk734qeeqk6h1kjbx1c7vs9ux32
            references users
);

alter table tickets
    owner to postgres;

