CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table config_entries
(
    id          bigserial    not null
        constraint config_entries_pkey
            primary key,
    creation    timestamp    not null default now(),
    last_update timestamp    not null default now(),
    uuid        uuid         not null
        constraint config_entries_uuid_uindex
            unique                    default uuid_generate_v4(),
    version     integer not null default 0,
    key         varchar(255) not null,
    value       varchar(255) not null
);

insert into config_entries (key, value)
values ('CONCURRENT_ONGOING_SERVICES_LIMIT', '10000');