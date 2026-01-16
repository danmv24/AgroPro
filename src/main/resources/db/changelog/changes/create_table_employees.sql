CREATE TABLE "employees" (
    employee_id SERIAL PRIMARY KEY,
    surname VARCHAR(60) NOT NULL,
    name VARCHAR(30) NOT NULL,
    patronymic VARCHAR(70) NOT NULL,
    position_id INT,
    payment_type VARCHAR(15) NOT NULL CHECK (payment_type IN ('FIXED', 'HOURLY')),
    salary DECIMAL(8, 2) NOT NULL CHECK (salary > 0),
    hire_date DATE NOT NULL,
    FOREIGN KEY (position_id) REFERENCES positions(position_id)
);