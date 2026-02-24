CREATE TABLE "expense_categories" (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    category_name VARCHAR(255) NOT NULL
)