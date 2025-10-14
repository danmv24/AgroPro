CREATE TABLE "employees" (
    id SERIAL PRIMARY KEY,
    surname VARCHAR(60) NOT NULL,
    name VARCHAR(30) NOT NULL,
    patronymic VARCHAR(70) NOT NULL,
    salary DECIMAL(8, 2),
    hire_date TIMESTAMP
);