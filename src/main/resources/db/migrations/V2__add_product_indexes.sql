-- V2__add_product_indexes

CREATE INDEX idx_product_price ON products (price);

CREATE INDEX idx_product_name ON products (name);
