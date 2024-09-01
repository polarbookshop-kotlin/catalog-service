create table book
(
    id                 bigserial primary key not null,
    author             varchar(255)          not null,
    isbn               varchar(13) unique    not null,
    price              float8                not null,
    title              varchar(255)          not null,
    version            integer               not null,
    created_date       timestamp             not null,
    last_modified_date timestamp             not null
)