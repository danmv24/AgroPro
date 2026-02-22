CREATE TABLE "machineries" (
    id BIGSERIAL PRIMARY KEY,
    machinery_name VARCHAR(100) NOT NULL,
    machinery_type VARCHAR(40) NOT NULL,
    inventory_number INT NOT NULL UNIQUE,
    license_plate VARCHAR(15) NOT NULL UNIQUE,
    current_status VARCHAR(40) NOT NULL,
    purchase_date DATE NOT NULL
);