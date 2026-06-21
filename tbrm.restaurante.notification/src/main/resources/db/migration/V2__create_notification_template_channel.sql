CREATE TABLE notification_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    title VARCHAR(120),
    message_template VARCHAR(255),
    status VARCHAR(30)
);

CREATE TABLE notification_channel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80),
    description VARCHAR(255),
    status VARCHAR(30)
);