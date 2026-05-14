CREATE TABLE "users" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    employee_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);