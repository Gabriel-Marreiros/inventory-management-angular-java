INSERT INTO
    roles (id, name, created_at)
VALUES
    ('3c98a8ba-983a-4db8-9584-02d9b7578150', 'Administrador', NOW()),
    ('f7a02a55-4f2c-4382-b1f3-0ed2624f6f66', 'Usuário', NOW());


INSERT INTO
    users (id, name, email, password, phone_number, date_birth, active, role_id, created_at)
VALUES
    ('2fd231fd-7497-4ef1-b539-93bab5e5344d', 'Admin Teste', 'admin@email.com', '$2a$10$tGEdUgbMcseurTiy4/LkB.Z41u4Pgss9zgOMxZDN/LC0OAmLZ8nEW', '(11) 91234-5678', '2000-01-01', 1, '3c98a8ba-983a-4db8-9584-02d9b7578150', NOW()),
    ('718c5016-b159-410f-9e4b-babeaa10bf6a', 'Usuário Teste 1', 'usuario-1@email.com', '$2a$12$AWzxonIbKvswncg1Dfk2R.xL6W7iebNHKuEBZp2TJfJW1chEzgrya', '(11) 99874-1234', '1995-12-12', 1, 'f7a02a55-4f2c-4382-b1f3-0ed2624f6f66', NOW()),
    ('02c72eea-daa5-4ddc-8929-90398761bc4a', 'Usuário Teste 2', 'usuario-2@email.com', '$2a$12$hIlNuaBbDh/uCNkdSnTLxOIXY.PScKsbywk0lUjK7bY3C45kZg8lK', '(11) 96549-3211', '2002-07-01', 0, 'f7a02a55-4f2c-4382-b1f3-0ed2624f6f66', NOW()),
    ('2c259485-c6cb-4c8f-80ac-86c3e9490169', 'Usuário Teste 3', 'usuario-3@email.com', '$2a$12$oJ6oT4sjp3wfuI4Y.DiRxeixiQIWLbSa2Cja7gffrrIqYnUV3Uexu', '(11) 93216-7845', '1998-04-01', 1, 'f7a02a55-4f2c-4382-b1f3-0ed2624f6f66', NOW());


INSERT INTO
    categories (id, name, active, created_at)
VALUES
    ('e396a976-1a53-4a0a-871b-b1efde58c35a', 'Categoria Teste 1', 1, NOW()),
    ('e626f851-25d3-40be-bb69-018dcbced34a', 'Categoria Teste 2', 0, NOW()),
    ('d4992823-5dde-402f-9104-bbd59e226b4e', 'Categoria Teste 3', 1, NOW());


INSERT INTO
    products (id,active,brand,created_at,description,image,link,modified_at,name,price,quantity,sku,category_id,search_term)
VALUES
     ('45103b60-2385-42bf-9539-e7e5adf6c27f', 1, 'Marca Teste 1', '2024-01-01 13:00:00', 'Descrição do produto de teste 1', NULL, NULL, NULL, 'Produto Teste 1', 199.90, 1, 'ER6G51REGR', 'e396a976-1a53-4a0a-871b-b1efde58c35a', 'Marca Teste 1Produto Teste 1ER6G51REGR199.901'),
     ('5d5f293b-f73d-4cd3-8a1a-58e07b640cfc', 0, 'Marca Teste 1', '2024-01-02 13:15:00', 'Descrição do produto de teste 2', NULL, NULL, NULL, 'Produto Teste 2', 299.90, 2, '65SD4F65SD', 'e396a976-1a53-4a0a-871b-b1efde58c35a', 'Marca Teste 1Produto Teste 265SD4F65SD299.902'),
     ('8621ca72-605d-49c3-8688-4e6d499adb6d', 1, 'Marca Teste 1', '2024-01-03 13:30:00', 'Descrição do produto de teste 3', NULL, NULL, NULL, 'Produto Teste 3', 399.90, 3, 'JTR46T54JY', 'e396a976-1a53-4a0a-871b-b1efde58c35a', 'Marca Teste 1Produto Teste 3JTR46T54JY399.903'),
     ('21af7633-c0b7-43e5-abb0-fc5809efe63c', 0, 'Marca Teste 1', '2024-01-04 13:45:00', 'Descrição do produto de teste 4', NULL, NULL, NULL, 'Produto Teste 4', 499.90, 4, '8YU7O9UY87', 'e396a976-1a53-4a0a-871b-b1efde58c35a', 'Marca Teste 1Produto Teste 48YU7O9UY87499.904'),

     ('e110812a-0ff5-4bdb-a11f-3a3b127dc83f', 1, 'Marca Teste 2', '2024-02-10 14:00:00', 'Descrição do produto de teste 5', NULL, NULL, NULL, 'Produto Teste 5', 1000.00, 5, 'EW4G6W5E41', 'e626f851-25d3-40be-bb69-018dcbced34a', 'Marca Teste 2Produto Teste 5EW4G6W5E411000.005'),
     ('fe255c60-b1a5-42ec-8476-faf69ba2270f', 0, 'Marca Teste 2', '2024-02-11 14:15:00', 'Descrição do produto de teste 6', NULL, NULL, NULL, 'Produto Teste 6', 2000.00, 6, 'XC21B32CX1', 'e626f851-25d3-40be-bb69-018dcbced34a', 'Marca Teste 2Produto Teste 6XC21B32CX12000.006'),
     ('00c25b4a-5475-4ae1-8b3d-691dfce6d98c', 0, 'Marca Teste 2', '2024-02-12 14:30:00', 'Descrição do produto de teste 7', NULL, NULL, NULL, 'Produto Teste 7', 3000.00, 7, '4TM5656YMT', 'e626f851-25d3-40be-bb69-018dcbced34a', 'Marca Teste 2Produto Teste 74TM5656YMT3000.007'),
     ('245159e6-cec2-4dd2-a77e-377e95f947bf', 0, 'Marca Teste 2', '2024-02-13 14:45:00', 'Descrição do produto de teste 8', NULL, NULL, NULL, 'Produto Teste 8', 4000.00, 8, 'X5C1B31X3C', 'e626f851-25d3-40be-bb69-018dcbced34a', 'Marca Teste 2Produto Teste 8X5C1B31X3C4000.008'),

     ('a4d61115-4bc2-4286-82b9-70d54774f5d4', 0, 'Marca Teste 3', '2024-03-20 15:00:00', 'Descrição do produto de teste 9', NULL, NULL, NULL, 'Produto Teste 9', 123.99, 9, 'A6W5465WAF4', 'd4992823-5dde-402f-9104-bbd59e226b4e', 'Marca Teste 3Produto Teste 9A6W5465WAF4123.999'),
     ('207d52ba-c612-4f5d-bc99-01c5224d5904', 1, 'Marca Teste 3', '2024-03-21 15:15:00', 'Descrição do produto de teste 10', NULL, NULL, NULL, 'Produto Teste 10', 456.99, 10, '5Y1UM561M4U', 'd4992823-5dde-402f-9104-bbd59e226b4e', 'Marca Teste 3Produto Teste 105Y1UM561M4U456.9910'),
     ('cc61afc5-cd9a-4abc-b07c-718b9cf1f21e', 1, 'Marca Teste 3', '2024-03-22 15:30:00', 'Descrição do produto de teste 11', NULL, NULL, NULL, 'Produto Teste 11', 789.99, 11, '1LYU561UY56', 'd4992823-5dde-402f-9104-bbd59e226b4e', 'Marca Teste 3Produto Teste 111LYU561UY56789.9911'),
     ('7f76c0f0-cc16-400c-b51c-d25c910e327d', 1, 'Marca Teste 3', '2024-03-23 15:45:00', 'Descrição do produto de teste 12', NULL, NULL, NULL, 'Produto Teste 12', 999.99, 12, 'ERG465ER44R', 'd4992823-5dde-402f-9104-bbd59e226b4e', 'Marca Teste 3Produto Teste 12ERG465ER44R999.9912');
