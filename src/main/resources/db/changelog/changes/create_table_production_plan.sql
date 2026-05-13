CREATE TABLE "production_plan" (
    id BIGSERIAL PRIMARY KEY,
    winter_wheat_area DECIMAL(8, 2) NOT NULL,
    spring_wheat_area DECIMAL(8, 2) NOT NULL,
    spring_barley_area DECIMAL(8, 2) NOT NULL,
    sunflower_area DECIMAL(8, 2) NOT NULL,
    apple_area DECIMAL(8, 2) NOT NULL,
    winter_wheat_quantity_sale DECIMAL(10, 2) NOT NULL,
    spring_wheat_quantity_sale DECIMAL(10, 2) NOT NULL,
    spring_barley_quantity_sale DECIMAL(10, 2) NOT NULL,
    sunflower_quantity_sale DECIMAL(10, 2) NOT NULL,
    apple_quantity_sale DECIMAL(10, 2) NOT NULL,
    total_cost DECIMAL(12, 2) NOT NULL,
    total_revenue DECIMAL(12, 2) NOT NULL,
    max_profit DECIMAL(12, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);