CREATE TABLE "reports" (
    id BIGSERIAL PRIMARY KEY,
    report_type VARCHAR(30) NOT NULL,
    filename VARCHAR(255) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);