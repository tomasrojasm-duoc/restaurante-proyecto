CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120),
    description VARCHAR(255),
    category VARCHAR(50),
    price INT,
    status VARCHAR(30)
);