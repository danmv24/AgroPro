CREATE TABLE "field_work_machineries" (
    field_work_id BIGINT NOT NULL,
    machinery_id BIGINT NOT NULL,
    PRIMARY KEY (field_work_id, machinery_id),
    FOREIGN KEY (field_work_id) REFERENCES field_works(field_work_id) ON DELETE CASCADE,
    FOREIGN KEY (machinery_id) REFERENCES machineries(machinery_id)
);