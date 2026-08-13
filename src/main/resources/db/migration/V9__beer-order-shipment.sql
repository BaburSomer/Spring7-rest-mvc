drop table if exists beer_order_shipments;

create table beer_order_shipments
(
    oid             varchar(36) NOT NULL PRIMARY KEY,
    bo_oid          varchar(36) NOT NULL,
    tracking_number varchar(50),
    created         timestamp,
    modified        timestamp   DEFAULT NULL,
    version         bigint      DEFAULT NULL,
    CONSTRAINT bo_fk FOREIGN KEY (bo_oid) REFERENCES beer_orders (oid),
    CONSTRAINT bo_oid_unique UNIQUE (bo_oid)
) ENGINE = InnoDB; 

ALTER TABLE beer_orders
    ADD COLUMN bos_oid VARCHAR(36);

ALTER TABLE beer_orders
    ADD CONSTRAINT bos_fk FOREIGN KEY (bos_oid) REFERENCES beer_order_shipments (oid);