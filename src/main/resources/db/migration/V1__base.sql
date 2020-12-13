CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE shopping_list
(
    id          bigserial,
    identifier  uuid        not null,
    name        varchar(31) not null,
    amount      smallint    not null,
    is_selected boolean     not null,
    ctime       bigint      not null,
    mtime       bigint      not null,
    version     bigint,

    constraint pk_shopping_list primary key (id),
    constraint uq_shopping_list_uuid unique (identifier)
);
