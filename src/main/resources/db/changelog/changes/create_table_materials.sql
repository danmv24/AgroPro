CREATE TABLE "materials" (
    id BIGSERIAL PRIMARY KEY,
    material_name VARCHAR(60) NOT NULL,
    material_type VARCHAR(40) NOT NULL,
    current_price DECIMAL(10, 2) NOT NULL
);