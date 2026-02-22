CREATE TABLE "equipment_status_history" (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    status VARCHAR(40) NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);