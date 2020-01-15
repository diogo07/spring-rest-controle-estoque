CREATE TABLE cliente (
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(70) NOT NULL,
	cpf VARCHAR(15),
	data_nascimento DATE
);