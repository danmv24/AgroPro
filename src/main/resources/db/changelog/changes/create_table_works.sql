CREATE TABLE "works" (
    id BIGSERIAL PRIMARY KEY,
    work_type VARCHAR(40) NOT NULL,
    field_id INT NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL,
    end_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) DEFAULT 'PLANNED',
    description TEXT,
    FOREIGN KEY (field_id) REFERENCES fields(id)
);