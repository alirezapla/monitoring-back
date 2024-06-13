alter table indicator drop computation;
create table computation
(
    id          varchar(255) not null primary key,
    description varchar(512),
    label       varchar(128),
    indicator_id varchar(255),
    FOREIGN KEY (indicator_id) REFERENCES indicator(id)
);
create index computation_indicator_id_fk on computation (indicator_id);
