CREATE TABLE "product_sales" (
    id BIGSERIAL PRIMARY KEY,
    product VARCHAR(60) NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(12, 2),
    sale_date DATE NOT NULL
);