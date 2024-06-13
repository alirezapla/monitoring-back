create table document
(
    id           varchar(255) not null primary key,
    created_date date         null,
    created_by   varchar(128) null,
    updated_date date         null,
    updated_by   varchar(128) null,
    is_hided     boolean,
    is_deleted   boolean,
    description  varchar(512),
    name         varchar(128)
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
    document_id  varchar(256)
);
create table indicator
(
    id           varchar(255) not null primary key,
    created_date date         null,
    created_by   varchar(128),
    updated_date date         null,
    updated_by   varchar(128),
    translation_fa varchar(128),
    translation_en varchar(128),
    description_fa varchar(512),
    description_en varchar(512),
    data_type varchar(64),
    indicator_type varchar(64),
    unit varchar(64),
    is_hided     boolean,
    is_deleted   boolean,
    name         varchar(128),
    record_order int,
    doc_table_id varchar(256),
    computation  JSONB
);

create table computing_table_items
(
    id           varchar(255) not null primary key,
    created_date date         null,
    created_by   varchar(128),
    updated_date date         null,
    updated_by   varchar(128),
    is_hided     boolean,
    is_deleted   boolean,
    command      varchar(256),
    description  varchar(512),
    document_id  varchar(256)


);


create table user_credentials
(
    id          varchar(255) not null primary key,
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
alter table doc_table
    add constraint doc_table_document_fk foreign key (document_id) references document (id);
alter table computing_table_items
    add constraint document_computing_table_items_fk foreign key (document_id) references document (id);
alter table indicator
    add constraint doc_table_indicator_fk foreign key (doc_table_id) references doc_table (id);

INSERT INTO user_credentials(id, username, password)
VALUES ('9153ee25-9767-4d5a-bfc7-42db1ca0accf', 'admin',
        '$2a$12$DKBWuNEGrVj0QS97YQBUdO6qIGyxq1PKQe1ygGKUG6S3T7zEt516y');

INSERT INTO user_authority(user_credential_id, user_authority)
VALUES ('9153ee25-9767-4d5a-bfc7-42db1ca0accf', 'AUTHORITY_ADMIN');