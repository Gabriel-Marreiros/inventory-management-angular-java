CREATE TABLE roles (
  id binary(16) NOT NULL,
  created_at datetime(6) DEFAULT NULL,
  modified_at datetime(6) DEFAULT NULL,
  name varchar(255) NOT NULL,

  PRIMARY KEY (id),
  UNIQUE KEY UK_ofx66keruapi6vyqpv6f2or37 (name)
);