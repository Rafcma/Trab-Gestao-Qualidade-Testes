-- dados de livro de teste

INSERT INTO autor (id, nome, pais) VALUES
(1, 'Machado de Assis', 'Brasil'),
(2, 'José Saramago', 'Portugal');

INSERT INTO editora (id, nome, cidade) VALUES
(1, 'Companhia das Letras', 'São Paulo'),
(2, 'Porto Editora', 'Porto');

-- depos da primeira execucao roda
INSERT INTO livro (titulo, ano_publicacao, isbn, preco, autor_id, editora_id) VALUES
('Dom Casmurro', 1899, 'ISBN-001', 39.90, 1, 1),
('Memorial do Convento', 1982, 'ISBN-002', 49.90, 2, 2);
