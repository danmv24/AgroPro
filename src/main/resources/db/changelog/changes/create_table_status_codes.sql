CREATE TABLE "status_codes" (
    status_id BIGSERIAL PRIMARY KEY,
    status_code VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(50) NOT NULL UNIQUE
);