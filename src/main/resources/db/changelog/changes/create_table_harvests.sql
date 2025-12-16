CREATE TABLE "harvests" (
    id SERIAL PRIMARY KEY,
    field_id INT NOT NULL,
    harvest_date DATE NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (field_id) REFERENCES fields(field_id)
)