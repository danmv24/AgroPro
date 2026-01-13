CREATE TABLE "machineries" (
    machinery_id BIGSERIAL PRIMARY KEY,
    machinery_name VARCHAR(100) NOT NULL,
    machinery_type_id INT NOT NULL,
    inventory_number INT NOT NULL UNIQUE,
    license_plate VARCHAR(15) NOT NULL UNIQUE,
    status_id INT NOT NULL,
    FOREIGN KEY (machinery_type_id) REFERENCES machinery_types(id),
    FOREIGN KEY (status_id) REFERENCES status_codes(status_id)
);