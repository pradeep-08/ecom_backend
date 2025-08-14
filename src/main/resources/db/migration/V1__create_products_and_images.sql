-- Products table
CREATE TABLE products (
  id BIGINT NOT NULL AUTO_INCREMENT,
  active BIT(1) DEFAULT b'1',
  description VARCHAR(1000) DEFAULT NULL,
  name VARCHAR(255) DEFAULT NULL,
  price DECIMAL(38,2) DEFAULT NULL,
  stock INT NOT NULL,
  category VARCHAR(255) DEFAULT NULL,
  image_url VARCHAR(255) DEFAULT NULL,
  sku VARCHAR(255) DEFAULT NULL,
  date_created DATETIME NOT NULL,
  date_updated DATETIME DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Product images table with composite PK (matches @ElementCollection mapping)
CREATE TABLE product_images (
  product_id BIGINT NOT NULL,
  image_url VARCHAR(255) NOT NULL,
  PRIMARY KEY (product_id, image_url),
  CONSTRAINT fk_product_images_product
    FOREIGN KEY (product_id) REFERENCES products(id)
    ON DELETE CASCADE
) ENGINE=InnoDB;