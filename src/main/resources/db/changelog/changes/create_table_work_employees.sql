CREATE TABLE "work_employees" (
     id BIGSERIAL PRIMARY KEY,
     work_id BIGINT NOT NULL,
     employee_id BIGINT NOT NULL,
     UNIQUE (work_id, employee_id),
     FOREIGN KEY (work_id) REFERENCES works(id),
     FOREIGN KEY (employee_id) REFERENCES employees(id)
);