CREATE TABLE "work_equipment" (
    id BIGSERIAL PRIMARY KEY,
    work_id BIGINT NOT NULL,
    equipment_id BIGINT NOT NULL,
    UNIQUE (work_id, equipment_id),
    FOREIGN KEY (work_id) REFERENCES works(id),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);