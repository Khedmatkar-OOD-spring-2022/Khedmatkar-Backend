create table specialties
(
    id bigserial not null
        constraint specialties_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_mnftwkepa9av88gj1hb4gy9y6
            unique,
    version integer,
    name varchar(255),
    parent_id bigint
        constraint fk66tiawjf35tb866dkexh19geu
            references specialties
);

alter table specialties owner to postgres;

create table users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_6km2m9i3vjuy36rnvkgj1l61s
            unique,
    version integer,
    email varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    type varchar(255)
);

alter table users owner to postgres;

create table admins
(
    id bigint not null
        constraint admins_pkey
            primary key
        constraint fkanhsicqm3lc8ya77tr7r0je18
            references users
);

alter table admins owner to postgres;

create table admin_permissions
(
    admin_id bigint not null
        constraint fk7vyuc2dwnyqi0v44ab5n5faca
            references admins,
    permission varchar(255)
);

alter table admin_permissions owner to postgres;

create table customers
(
    id bigint not null
        constraint customers_pkey
            primary key
        constraint fkpog72rpahj62h7nod9wwc28if
            references users
);

alter table customers owner to postgres;

create table specialists
(
    id bigint not null
        constraint specialists_pkey
            primary key
        constraint fkiau8ybic92gu8g87fo4evn8o5
            references users
);

alter table specialists owner to postgres;

create table certificates
(
    id bigserial not null
        constraint certificates_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_oo7t6tdubc9aic19lipflu47p
            unique,
    version integer,
    status varchar(255) not null,
    specialist_id bigint
        constraint fk1ur5cwg0g5inbbwl6atrxga56
            references specialists,
    specialty_id bigint
        constraint fk7ifa4p69ecvp6cneegv7trk4x
            references specialties
);

alter table certificates owner to postgres;

create table service_requests
(
    id bigserial not null
        constraint service_requests_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_byllp58dwf5am8csv4oc4he7r
            unique,
    version integer,
    address varchar(255),
    description varchar(255),
    reception_date timestamp,
    status varchar(255),
    accepted_specialist_id bigint
        constraint fkpon98mvtoo45psmq3bbfmk95p
            references specialists,
    customer_id bigint
        constraint fkdc3ls1bqnn3dop9b6eeyxplsc
            references customers,
    specialty_id bigint
        constraint fk5mxofpqx1pl5a307tpw1oe10i
            references specialties
);

alter table service_requests owner to postgres;

create table service_request_specialists
(
    id bigserial not null
        constraint service_request_specialists_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_q3iayhij4hi5u45aqrfn9qq4s
            unique,
    version integer,
    status varchar(255),
    service_request_id bigint
        constraint fkfahusgdc3p0jjbas2bhsnkix4
            references service_requests,
    specialist_id bigint
        constraint fkiu3h10gjl13gvk3uvclyn08ot
            references specialists
);

alter table service_request_specialists owner to postgres;

create table chats
(
    id bigserial not null
        constraint chats_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_neju4rt5qdk8rjtd1ehpmbtmr
            unique,
    version integer,
    status varchar(255),
    service_request_specialist_id bigint
        constraint fk27kc85a300eoh0umddrxafh6o
            references service_request_specialists,
    usera_id bigint
        constraint fkhnqd0iku19dodg311b20dsfc5
            references users,
    userb_id bigint
        constraint fk22qq6qvt63wk2p47wejepre9c
            references users
);

alter table chats owner to postgres;

create table messages
(
    id bigserial not null
        constraint messages_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_453ojydcs3mn8ob9it7aykamc
            unique,
    version integer,
    text varchar(255),
    chat_id bigint
        constraint fk64w44ngcpqp99ptcb9werdfmb
            references chats,
    sender_id bigint
        constraint fk4ui4nnwntodh6wjvck53dbk9m
            references users
);

alter table messages owner to postgres;

create table tickets
(
    dtype varchar(31) not null,
    id bigserial not null
        constraint tickets_pkey
            primary key,
    creation timestamp,
    last_update timestamp,
    uuid uuid
        constraint uk_h559x60if8ak48teo0sfvjg8t
            unique,
    version integer,
    content varchar(255),
    title varchar(255),
    status varchar(255),
    writer_id bigint
        constraint fk734qeeqk6h1kjbx1c7vs9ux32
            references users
);

alter table tickets owner to postgres;

create table tickets_answers
(
    technical_issue_id bigint not null
        constraint fk4nocqei3fxya5r5dcymao31u6
            references tickets,
    answers_id bigint not null
        constraint uk_n00o48yf57exi6lglhe32s76p
            unique
        constraint fkb48lmvjs68jexbyqixrj7mv1c
            references tickets
);

alter table tickets_answers owner to postgres;

