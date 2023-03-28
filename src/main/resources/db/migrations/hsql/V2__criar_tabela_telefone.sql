CREATE SEQUENCE telefone_id_seq AS BIGINT STARTNWITH 1 INCREMENT BY 1;
CREATE TABLE telefone (
	id BIGINT GENERATED BY DEFAULT AS SEQUENCE telefone_id_seq PRIMARY KEY,
	ddd VARCHAR(2) NOT NULL,
	numero VARCHAR(9) NOT NULL,
	id_pessoa BIGINT MOT NULL.
	FOREIGN KEY(id_pessoa) REFERENCES pessoa(id)
);