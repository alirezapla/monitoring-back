create table document
(
    id                 varchar(255) not null primary key,
    created_date       date         null,
    created_by         varchar(128) null,
    updated_date       date         null,
    updated_by         varchar(128) null,
    is_hided           boolean,
    is_deleted         boolean,
    description        varchar(512),
    name               varchar(128),
    doc_table_id       varchar(255),
    computing_table_id varchar(255)
);

create table computing_table
(
    id                      varchar(255) not null primary key,
    created_date            date         null,
    created_by              varchar(128),
    updated_date            date         null,
    updated_by              varchar(128),
    is_hided                boolean,
    is_deleted              boolean,
    name                    varchar(128),
    computing_table_item_id varchar(256)
);

create table doc_table
(
    id           varchar(255) not null primary key,
    created_date date         null,
    created_by   varchar(128),
    updated_date date         null,
    updated_by   varchar(128),
    is_hided     boolean,
    is_deleted   boolean,
    name         varchar(128),
    indicator_id varchar(255)
);
create table indicator
(
    id             varchar(255) not null primary key,
    created_date   date         null,
    created_by     varchar(128),
    updated_date   date         null,
    updated_by     varchar(128),
    is_hided       boolean,
    is_deleted     boolean,
    name           varchar(128),
    record_order   int,
    computation_id varchar(255)
);
create table computation
(
    id          varchar(255) not null primary key,
    label       varchar(64),
    description varchar(512)

);
create table computing_table_item
(
    id          varchar(255) not null primary key,
    command     varchar(256),
    description varchar(512)

);


create table user_credentials
(
    id          varchar(255) not null primary key,
    create_date date         null,
    update_date date         null,
    firstname   varchar(255) null,
    lastname    varchar(255) null,
    password    varchar(255) not null,
    username    varchar(255) not null
);

create table user_authority
(
    user_credential_id varchar(255) not null,
    user_authority     varchar(255) not null,
    primary key (user_credential_id, user_authority)
);

alter table user_credentials
    add constraint UK_8pcilw7ay5rs8c4dtrtik21pw unique (username);
alter table user_authority
    add constraint FK9eonwn7vhem2ho9n3oikh4c9s foreign key (user_credential_id) references user_credentials (id);
alter table document
    add constraint document_doc_table_fk foreign key (doc_table_id) references doc_table (id);
alter table document
    add constraint document_computing_table_fk foreign key (computing_table_id) references computing_table (id);
alter table computing_table
    add constraint computing_table_computing_table_iteme_fk foreign key (computing_table_item_id) references computing_table_item (id);
alter table doc_table
    add constraint doc_table_indicator_fk foreign key (indicator_id) references indicator (id);
alter table indicator
    add constraint indicator_computation_fk foreign key (computation_id) references computation (id);
