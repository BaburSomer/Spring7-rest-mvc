ALTER TABLE beer_orders
ADD CONSTRAINT fk_beer_orders_customer
    FOREIGN KEY (customer_oid)
    REFERENCES customers(oid);