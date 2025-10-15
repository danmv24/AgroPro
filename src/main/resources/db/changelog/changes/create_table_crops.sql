CREATE TABLE "crops" (
    crop_id SERIAL PRIMARY KEY,
    crop_name VARCHAR(100) NOT NULL UNIQUE
);