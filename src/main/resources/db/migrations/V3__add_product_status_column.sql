-- V3__add_product_status_column

ALTER TABLE products
ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE products
ADD COLUMN deleted_at TIMESTAMP;

CREATE INDEX idx_products_active ON products (active);