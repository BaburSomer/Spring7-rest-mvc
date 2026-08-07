drop table if exists categories;
drop table if exists beers_categories;

create table categories
(
    oid             varchar(36) NOT NULL PRIMARY KEY,
    name            varchar(50),
    created         timestamp,
    modified        timestamp   DEFAULT NULL,
    version         bigint      DEFAULT NULL
) ENGINE = InnoDB;

create table beers_categories
(
    beer_oid     varchar(36) NOT NULL,
    category_oid varchar(36) NOT NULL,
    primary key (beer_oid, category_oid),
    constraint pc_beer_oid_fk       FOREIGN KEY (beer_oid)      references beers (oid),
    constraint pc_category_oid_fk   FOREIGN KEY (category_oid)  references categories (oid)
) ENGINE = InnoDB;