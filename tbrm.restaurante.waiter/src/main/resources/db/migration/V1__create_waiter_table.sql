CREATE TABLE waiter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(12),
    name VARCHAR(80),
    last_name VARCHAR(80),
    phone VARCHAR(20),
    email VARCHAR(120),
    shift VARCHAR(30),
    status VARCHAR(30)
);