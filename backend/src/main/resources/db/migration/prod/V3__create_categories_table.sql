CREATE TABLE categories (
  id binary(16) NOT NULL,
  active bit(1) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  modified_at datetime(6) DEFAULT NULL,
  name varchar(255) NOT NULL,

  PRIMARY KEY (id),
  UNIQUE KEY UK_t8o6pivur7nn124jehx7cygw5 (name)
);