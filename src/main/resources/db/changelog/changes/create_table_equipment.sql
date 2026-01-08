CREATE TABLE "equipment" (
    equipment_id SERIAL PRIMARY KEY,
    equipment_name VARCHAR(100) NOT NULL,
    equipment_type_id INT NOT NULL,
    inventory_number INT NOT NULL UNIQUE,
    status_id INT NOT NULL,
    FOREIGN KEY (equipment_type_id) REFERENCES equipment_types(id),
    FOREIGN KEY (status_id) REFERENCES status_codes(status_id)
);