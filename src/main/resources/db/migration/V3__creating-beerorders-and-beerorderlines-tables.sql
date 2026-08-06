DROP TABLE IF EXISTS beer_orders;
DROP TABLE IF EXISTS beer_order_lines;

CREATE TABLE beer_orders (
  `oid` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `customer_oid` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `version` bigint DEFAULT NULL,
  `created` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  CONSTRAINT fk_beer_orders_customer FOREIGN KEY (`customer_oid`) REFERENCES customers (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE beer_order_lines (
  `oid` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_oid`   varchar(36) DEFAULT NULL,
  `beer_oid` varchar(36)    DEFAULT NULL,
  `quantity`     int        DEFAULT NULL,
  `quantity_allocated` int  DEFAULT NULL,
  `version` bigint DEFAULT NULL,
  `created` datetime (6) DEFAULT NULL,
  `modified` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  CONSTRAINT fk_orderlines_order FOREIGN KEY (`order_oid`) REFERENCES beer_orders (`oid`),
  CONSTRAINT fk_orderlines_beer FOREIGN KEY (`beer_oid`) REFERENCES beers (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;