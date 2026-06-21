CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    diner_id BIGINT,
    table_id BIGINT,
    date VARCHAR(10)
);