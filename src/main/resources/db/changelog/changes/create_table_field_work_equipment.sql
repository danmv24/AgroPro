CREATE TABLE "field_work_equipment" (
    field_work_id BIGINT NOT NULL,
    equipment_id BIGINT NOT NULL,
    PRIMARY KEY (field_work_id, equipment_id),
    FOREIGN KEY (field_work_id) REFERENCES field_works(id) ON DELETE CASCADE,
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id)
);