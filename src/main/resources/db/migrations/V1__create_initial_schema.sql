-- Flyway Migration V1: Create users and products tables

CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE products (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image VARCHAR(255) NOT NULL
);

INSERT INTO products (name, price, image)
SELECT
    'Product ' || s AS name,
    (random() * 990 + 10)::NUMERIC(10, 2) AS price,
    'https://placehold.co/600x400' AS image
FROM
    generate_series(1, 500) AS s;
