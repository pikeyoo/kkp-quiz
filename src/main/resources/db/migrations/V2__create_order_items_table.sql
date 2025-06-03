CREATE TABLE order_items (
id SERIAL PRIMARY KEY,
order_id INT REFERENCES orders(id) ON DELETE CASCADE,
product_name VARCHAR(100),
quantity INT,
price DECIMAL(10, 2)
);