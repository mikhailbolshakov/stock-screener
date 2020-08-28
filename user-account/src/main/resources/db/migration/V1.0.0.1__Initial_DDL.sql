-- move to another level where all system configurations are migrated at the very beginning
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create schema us_acc;

create table us_acc.user_account (
    id uuid primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    locked_account boolean not null default false
);

create table us_acc.user_phone (
    id uuid primary key,
    account_id uuid not null,
    type_id varchar(10) not null,
    phone_number varchar(100) not null,
    main boolean not null default false,
    confirmed boolean not null default false,
    valid_from timestamp not null,
    valid_to timestamp not null
);

alter table us_acc.user_phone add constraint c_fk_phone_account foreign key(account_id) references us_acc.user_account(id);

create index idx_phone_account_id on us_acc.user_phone(account_id);


create table us_acc.user_email (
    id uuid primary key,
    account_id uuid not null,
    email varchar(100) not null,
    main boolean not null default false,
    confirmed boolean not null default false,
    valid_from timestamp not null,
    valid_to timestamp not null
);

alter table us_acc.user_email add constraint c_fk_email_account foreign key(account_id) references us_acc.user_account(id);

create index idx_email_account_id on us_acc.user_email(account_id);







