-- Inserir Categorias
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (1, 'books');
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (2, 'movies');
INSERT INTO CATEGORY (ID, DESCRIPTION) VALUES (3, 'vehicles');

-- Inserir Fornecedores (Suppliers)
INSERT INTO SUPPLIER (ID, NAME) VALUES (1, 'cara dos books');
INSERT INTO SUPPLIER (ID, NAME) VALUES (2, 'cara dos movies e vehicles');

-- Inserir Produtos
INSERT INTO PRODUCT (ID, NAME, SUPPLIER_ID, CATEGORY_ID, QUANTITY_AVAILABLE) VALUES (1, 'livro do batman', 1, 1, 4);
INSERT INTO PRODUCT (ID, NAME, SUPPLIER_ID, CATEGORY_ID, QUANTITY_AVAILABLE) VALUES (2, 'filme do batman', 2, 2, 12);
INSERT INTO PRODUCT (ID, NAME, SUPPLIER_ID, CATEGORY_ID, QUANTITY_AVAILABLE) VALUES (3, 'carro do batman', 2, 3, 2);
