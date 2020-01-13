CREATE TABLE categoria(
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
);


INSERT INTO categoria(nome) values ('Higiene e Limpeza');
INSERT INTO categoria(nome) values ('Alimentação');
INSERT INTO categoria(nome) values ('Saúde');
INSERT INTO categoria(nome) values ('Brinquedos');
INSERT INTO categoria(nome) values ('Material Escolar');
