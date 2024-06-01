CREATE TABLE products (
  id binary(16) NOT NULL,
  active bit(1) NOT NULL,
  brand varchar(255) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  description text DEFAULT NULL,
  image varchar(255) DEFAULT NULL,
  link varchar(255) DEFAULT NULL,
  modified_at datetime(6) DEFAULT NULL,
  name varchar(255) NOT NULL,
  price decimal(38,2) NOT NULL,
  quantity int NOT NULL,
  sku varchar(255) NOT NULL,
  category_id binary(16) NOT NULL,
  search_term text DEFAULT NULL,

  PRIMARY KEY (id),
  UNIQUE KEY UK_fhmd06dsmj6k0n90swsh8ie9g (sku),
  KEY FKog2rp4qthbtt2lfyhfo32lsw9 (category_id),
  CONSTRAINT FKog2rp4qthbtt2lfyhfo32lsw9 FOREIGN KEY (category_id) REFERENCES categories (id)
);