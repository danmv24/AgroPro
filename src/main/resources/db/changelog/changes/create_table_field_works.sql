CREATE TABLE "field_works" (
    field_work_id BIGSERIAL PRIMARY KEY,
    work_type_id INT NOT NULL,
    field_id INT NOT NULL,
    planned_start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    planned_end_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) DEFAULT 'PLANNED',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    FOREIGN KEY (work_type_id) REFERENCES work_types(work_type_id),
    FOREIGN KEY (field_id) REFERENCES fields(field_id)
);