CREATE TABLE "positions" (
    position_id SERIAL PRIMARY KEY,
    position_name VARCHAR(50) NOT NULL UNIQUE
);