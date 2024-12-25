CREATE TABLE users(
    id bigint not null auto_increment,
    username varchar(60) not null,
    password varchar(255) not null,
    role varchar(32) not null,
    email varchar(64) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    PRIMARY KEY(id),
    UNIQUE (username),
    UNIQUE(email)
);