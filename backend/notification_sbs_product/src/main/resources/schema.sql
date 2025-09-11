CREATE TABLE IF NOT EXISTS order_notifications (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  customer_name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL,
  order_id VARCHAR(64) NOT NULL,
  product_name VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  total DECIMAL(12,2) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_order_notifications_email ON order_notifications(email);
CREATE INDEX idx_order_notifications_order_id ON order_notifications(order_id);
