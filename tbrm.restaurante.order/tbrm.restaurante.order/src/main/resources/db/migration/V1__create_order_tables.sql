CREATE TABLE restaurant_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diner_id BIGINT,
    table_id BIGINT,
    waiter_id BIGINT,
    date VARCHAR(10),
    status VARCHAR(30),
    total INT
);

CREATE TABLE order_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_item_id BIGINT,
    quantity INT,
    unit_price INT,
    subtotal INT,
    order_id BIGINT,
    CONSTRAINT fk_order_detail_order
        FOREIGN KEY (order_id)
        REFERENCES restaurant_order(id)
);