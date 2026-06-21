CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120),
    stock INT,
    minimum_stock INT,
    unit VARCHAR(30),
    status VARCHAR(30)
);