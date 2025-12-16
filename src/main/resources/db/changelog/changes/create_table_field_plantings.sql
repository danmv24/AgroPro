CREATE TABLE "field_plantings" (
    id SERIAL PRIMARY KEY,
    field_id INT NOT NULL,
    crop_id INT NOT NULL,
    planting_year INT NOT NULL,
    FOREIGN KEY (field_id) REFERENCES fields(field_id),
    FOREIGN KEY (crop_id) REFERENCES crops(crop_id),
    UNIQUE (field_id, planting_year)
)