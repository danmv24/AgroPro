CREATE TABLE "field_plantings" (
    id SERIAL PRIMARY KEY,
    field_id INT NOT NULL,
    crop_type VARCHAR(50) NOT NULL,
    planting_date DATE NOT NULL,
    harvest_date DATE,
    FOREIGN KEY (field_id) REFERENCES fields(id),
    UNIQUE (field_id, planting_date)
)