CREATE TABLE "expenses" (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    expense_date DATE NOT NULL,
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES expense_categories(id)
)