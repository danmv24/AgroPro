CREATE TABLE "field_work_employees" (
     field_work_id BIGINT NOT NULL,
     employee_id BIGINT NOT NULL,
     PRIMARY KEY (field_work_id, employee_id),
     FOREIGN KEY (field_work_id) REFERENCES field_works(field_work_id) ON DELETE CASCADE,
     FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);