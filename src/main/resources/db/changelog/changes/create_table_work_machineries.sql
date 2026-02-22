CREATE TABLE "work_machineries" (
    id BIGSERIAL PRIMARY KEY,
    work_id BIGINT NOT NULL,
    machinery_id BIGINT NOT NULL,
    UNIQUE (work_id, machinery_id),
    FOREIGN KEY (work_id) REFERENCES works(id),
    FOREIGN KEY (machinery_id) REFERENCES machineries(id)
);