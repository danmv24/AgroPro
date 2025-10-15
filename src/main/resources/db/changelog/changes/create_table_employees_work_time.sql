CREATE TABLE "employees_work_time" (
    id SERIAL PRIMARY KEY,
    employee_id INT,
    work_date TIMESTAMP,
    hours_worked DECIMAL(5, 2) NOT NULL CHECK (hours_worked >= 0)
);
