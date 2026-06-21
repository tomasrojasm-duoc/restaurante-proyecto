CREATE TABLE diner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(12),
    name VARCHAR(80),
    last_name VARCHAR(80),
    phone VARCHAR(20),
    address VARCHAR(150),
    email VARCHAR(120),
    birthday DATE
);