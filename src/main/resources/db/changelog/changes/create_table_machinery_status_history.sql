CREATE TABLE "machinery_status_history" (
    id BIGSERIAL PRIMARY KEY,
    machinery_id BIGINT NOT NULL,
    status_id INT NOT NULL,
    changed_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (machinery_id) REFERENCES machineries(machinery_id),
    FOREIGN KEY (status_id) REFERENCES status_codes(status_id)
);