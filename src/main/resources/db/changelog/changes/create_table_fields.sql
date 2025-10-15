CREATE TABLE "fields" (
    field_id SERIAL PRIMARY KEY,
    field_number INT NOT NULL UNIQUE,
    area DECIMAL(8, 2) NOT NULL,
    crop_id INT,
    FOREIGN KEY (crop_id) REFERENCES crops(crop_id)
);