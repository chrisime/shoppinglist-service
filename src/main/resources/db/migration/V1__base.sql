CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user"
(
    id          uuid default uuid_generate_v4(),
    firstname   varchar(31) not null,
    lastname    varchar(31) not null,
    username    varchar(31) not null,
    email       varchar(63) not null,

    created_ts  timestamp default timezone('UTC', now()),
    modified_ts timestamp default timezone('UTC', now()),

    version     bigint,

    constraint pk_users primary key (id),
    constraint uq_users_username unique (username)
);


CREATE TABLE IF NOT EXISTS shopping_list
(
    id          bigserial,
    identifier  uuid        not null,
    name        varchar(31) not null,
    amount      smallint    not null,
    is_selected boolean     not null,

    user_id     uuid        not null,

    created_ts  timestamp default timezone('UTC'::text, now()),
    modified_ts timestamp default timezone('UTC'::text, now()),

    version     bigint,

    constraint pk_shopping_list primary key (id),
    constraint uq_shopping_list_uuid unique (identifier),
    constraint fk_shopping_list_users_id foreign key (user_id) references "user" (id)
);

CREATE OR REPLACE FUNCTION update_modified_ts_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.modified_ts = timezone('UTC'::text, now());
    RETURN NEW;
END;
$$ language 'plpgsql';

DROP TRIGGER IF EXISTS update_user_modified_ts_column ON "public"."user";
DROP TRIGGER IF EXISTS update_shopping_list_modified_ts_column ON "public"."shopping_list";

CREATE TRIGGER update_user_modified_ts_column
    BEFORE UPDATE
    ON "user"
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_ts_column();

CREATE TRIGGER update_shopping_list_modified_ts_column
    BEFORE UPDATE
    ON shopping_list
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_ts_column();
