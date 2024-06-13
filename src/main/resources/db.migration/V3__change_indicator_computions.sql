alter table indicator drop computation;
create table computation
(
    id          varchar(255) not null primary key,
    description varchar(512),
    label       varchar(128),
    indicator_id varchar(255),
    FOREIGN KEY (indicator_id) REFERENCES indicator(id)
);
