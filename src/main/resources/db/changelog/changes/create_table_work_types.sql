CREATE TABLE "work_types" (
    work_type_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);