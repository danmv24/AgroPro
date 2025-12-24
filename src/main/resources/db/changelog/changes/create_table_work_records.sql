CREATE TABLE "work_records" (
    id SERIAL PRIMARY KEY,
    employee_id INT NOT NULL,
    work_date DATE,
    hours_worked DECIMAL(5, 2) NOT NULL CHECK (hours_worked >= 0),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);
