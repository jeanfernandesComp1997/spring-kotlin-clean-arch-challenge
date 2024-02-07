create table users (
    id varchar(36),
    name varchar(50),
    document varchar(200),
    birth_date date,
    email varchar(50),
    password varchar(200),
    type varchar(15),
    balance decimal(16, 4),
    primary key (id)
)