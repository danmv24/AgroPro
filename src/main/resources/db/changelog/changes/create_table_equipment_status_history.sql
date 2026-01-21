CREATE TABLE "machinery_status_history" (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    status_id INT NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id),
    FOREIGN KEY (status_id) REFERENCES status_codes(status_id)
);