CREATE TABLE "employees" (
    id SERIAL PRIMARY KEY,
    surname VARCHAR(60) NOT NULL,
    name VARCHAR(30) NOT NULL,
    patronymic VARCHAR(70) NOT NULL,
    position VARCHAR(40) NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    salary DECIMAL(8, 2) NOT NULL CHECK (salary > 0),
    hire_date DATE NOT NULL,
    gender VARCHAR(10) NOT NULL
);