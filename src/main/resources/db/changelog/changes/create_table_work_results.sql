CREATE TABLE "work_results" (
    id BIGSERIAL PRIMARY KEY,
    work_id BIGINT NOT NULL UNIQUE,
    fuel_used NUMERIC(8, 2) NOT NULL,
    seeds_used NUMERIC(12, 2),
    yield NUMERIC(14, 2),
    fertilizer_type VARCHAR(100),
    fertilizers_used NUMERIC(12, 2),
    FOREIGN KEY (work_id) REFERENCES works(id)
)