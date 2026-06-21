CREATE TABLE kitchen_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    status VARCHAR(30),
    estimated_time INT,
    observations VARCHAR(255)
);