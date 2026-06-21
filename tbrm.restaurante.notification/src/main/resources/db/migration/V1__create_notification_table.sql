CREATE TABLE notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient VARCHAR(120),
    type VARCHAR(50),
    message VARCHAR(255),
    status VARCHAR(30),
    created_at VARCHAR(10)
);