CREATE TABLE "equipment" (
    id SERIAL PRIMARY KEY,
    equipment_name VARCHAR(100) NOT NULL,
    equipment_type VARCHAR(40) NOT NULL,
    inventory_number INT NOT NULL UNIQUE,
    current_status VARCHAR(40) NOT NULL,
    purchase_date DATE NOT NULL
);