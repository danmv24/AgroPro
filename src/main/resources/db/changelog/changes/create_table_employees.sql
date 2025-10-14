CREATE TABLE "employees" (
    employee_id SERIAL PRIMARY KEY,
    surname VARCHAR(60) NOT NULL,
    name VARCHAR(30) NOT NULL,
    patronymic VARCHAR(70) NOT NULL,
    position_id INT,
    salary DECIMAL(8, 2),
    hire_date TIMESTAMP,
    FOREIGN KEY (position_id) REFERENCES positions(position_id)
);