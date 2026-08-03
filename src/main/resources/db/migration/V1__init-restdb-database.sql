DROP TABLE IF EXISTS beers;
DROP TABLE IF EXISTS customers;

CREATE TABLE beers (
  `oid` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `style` tinyint NOT NULL,
  `upc` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `quantity_on_hand` int DEFAULT NULL,
  `version` int DEFAULT NULL,
  `created` datetime(6) DEFAULT NULL,
  `updated` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  CONSTRAINT `beers_chk_1` CHECK ((`style` between 0 and 9))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE customers (
  `oid` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `version` int DEFAULT NULL,
  `created` datetime(6) DEFAULT NULL,
  `updated` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;