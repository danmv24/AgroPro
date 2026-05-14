CREATE TABLE "work_material_usage" (
    id BIGSERIAL PRIMARY KEY,
    work_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    quantity DECIMAL(12, 2) NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL,
    total_cost Decimal(14, 2) NOT NULL,
    FOREIGN KEY (material_id) REFERENCES materials(id),
    FOREIGN KEY (work_id) REFERENCES works(id)
);