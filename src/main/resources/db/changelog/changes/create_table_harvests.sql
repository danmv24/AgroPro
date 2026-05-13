CREATE TABLE "harvests" (
    id SERIAL PRIMARY KEY,
    work_id BIGINT NOT NULL,
    gross_harvest DECIMAL(14, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (work_id) REFERENCES works(id)
);