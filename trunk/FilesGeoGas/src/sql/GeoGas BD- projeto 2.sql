CREATE TABLE Usuario(
	id_usuario SERIAL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	facebook_id VARCHAR(255) NOT NULL,
	cpf BIGINT,
	idade INTEGER,
	chave_facebook VARCHAR(255) NOT NULL
);

CREATE TABLE Entidade(
	id_entidade SERIAL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	endereco VARCHAR(255) NOT NULL,
	bairro VARCHAR(255),
	cidade VARCHAR(255),
	estado VARCHAR(255),
	pais VARCHAR(255),
	cep VARCHAR(255),
	data_criacao TIMESTAMP NOT NULL
);

CREATE TABLE TipoEntidade(
	id_tipo_entidade INTEGER PRIMARY KEY,
	tipo_entidade VARCHAR(255) NOT NULL
);

CREATE TABLE Entidade_TipoEntidade(
	id_tipo_entidade INTEGER REFERENCES TipoEntidade(id_tipo_entidade) NOT NULL,
	id_entidade INTEGER REFERENCES Entidade(id_entidade) NOT NULL,
	CONSTRAINT pk_entidade_tipoentidade PRIMARY KEY (id_tipo_entidade,id_entidade)
);

CREATE TABLE PostoCombustivel(
	id_posto_combustivel INTEGER REFERENCES Entidade(id_entidade) NOT NULL,
	autorizacao VARCHAR(255),
	cnpjCpf VARCHAR(255) NOT NULL,
	razaoSocial VARCHAR(255),
	nomeFantasia VARCHAR(255),
	numeroDespacho VARCHAR(255), 
	bandeira VARCHAR(255),
	tipoPosto VARCHAR(255),
	latitude DOUBLE PRECISION,
	longitude DOUBLE PRECISION,
	CONSTRAINT pk_gasstation PRIMARY KEY (id_posto_combustivel)
);

CREATE TABLE TipoCombustivel(
	id_tipo_combustivel INTEGER PRIMARY KEY,
	tipo_combustivel VARCHAR(255)
);

CREATE TABLE PostoCombustivel_TipoCombustivel_ANP(
	id_posto_combustivel INTEGER REFERENCES PostoCombustivel(id_posto_combustivel),
	id_tipo_combustivel INTEGER REFERENCES TipoCombustivel(id_tipo_combustivel),
	preco FLOAT NOT NULL,
	data TIMESTAMP NOT NULL,
	CONSTRAINT pk_postocombustivel_tipocombustivel PRIMARY KEY (id_posto_combustivel, id_tipo_combustivel)
);

CREATE TABLE PostoCombustivel_TipoCombustivel_Usuario(
	id_posto_combustivel INTEGER REFERENCES PostoCombustivel(id_posto_combustivel),
	id_tipo_combustivel INTEGER REFERENCES TipoCombustivel(id_tipo_combustivel),
	id_usuario INTEGER REFERENCES Usuario(id_usuario),
	preco FLOAT NOT NULL,
	data TIMESTAMP NOT NULL,
	CONSTRAINT pk_postocombustivel_tipocombustivel_usuario PRIMARY KEY (id_posto_combustivel, id_tipo_combustivel,id_usuario)
);

CREATE TABLE AvaliacaoANP(
	id_avaliacao_anp INTEGER PRIMARY KEY,
	avaliacao VARCHAR(255) NOT NULL
);

CREATE TABLE Avaliacao_PostoCombustivel_ANP(
	id_posto_combustivel INTEGER REFERENCES PostoCombustivel(id_posto_combustivel),
	id_avaliacao_anp INTEGER REFERENCES AvaliacaoANP(id_avaliacao_anp),
	data TIMESTAMP NOT NULL,
	CONSTRAINT pk_avaliacao_postocombustivel_anp PRIMARY KEY (id_posto_combustivel, id_avaliacao_anp)
);

CREATE TABLE Avaliacao_Entidade_Usuario(
	id_entidade INTEGER REFERENCES Entidade(id_entidade),
	id_usuario INTEGER REFERENCES Usuario(id_usuario),
	nota INTEGER NOT NULL,
	data TIMESTAMP NOT NULL,
	CONSTRAINT pk_avaliacao_postocombustivel_usuario PRIMARY KEY (id_entidade, id_usuario)
);


CREATE TABLE Denuncia(
	id_denuncia SERIAL PRIMARY KEY,
	reclamacao VARCHAR(255) NOT NULL
);

CREATE TABLE Entidade_Denuncia(
	id_entidade INTEGER REFERENCES Entidade(id_entidade),
	id_denuncia INTEGER REFERENCES Denuncia(id_denuncia),
	CONSTRAINT pk_entidade_denuncia PRIMARY KEY (id_entidade, id_denuncia)
);

SELECT AddGeometryColumn('postocombustivel','geom',4326,'POINT',2);


CREATE TABLE Lingua(
	id_lingua INTEGER NOT NULL,
	desc_lingua VARCHAR(255),
	CONSTRAINT pk_lingua PRIMARY KEY (id_lingua)
);

INSERT INTO Lingua(id_lingua,desc_lingua) VALUES (1,'português');

CREATE TABLE Traducao(
	coluna_banco VARCHAR(255) NOT NULL,
	traducao VARCHAR(255) NOT NULL,
	id_lingua INTEGER REFERENCES Lingua(id_lingua),
	CONSTRAINT pk_traducao PRIMARY KEY (coluna_banco,traducao)
);

--- TABLEA USUARIO ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('usuario','Usuário',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_usuario','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('nome','Nome',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('facebook_id','Facebook ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('cpf','CPF',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('idade','Idade',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('chave_facebook','Facebook KEY',1);


--- TABELA ENTIDADE ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('entidade','Entidade',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_entidade','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('nome','Nome',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('endereco','Endereço',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('bairro','Bairro',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('cidade','Cidade',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('estado','Estado',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('pais','País',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('cep','CEP',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('data_criacao','Data de Criação',1);


--- TABELA TIPOENTIDADE ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('tipoentidade','Tipo de Entidade',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_tipo_entidade','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('tipo_entidade','Tipo de Entidade',1);

--- TABELA Entidade_TipoEntidade ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('Entidade_TipoEntidade','Mapeamento de Entidade para o Tipo de Entidade',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_tipo_entidade','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_entidade','ID',1);


--- TABLEA POSTOCOMBUSTIVEL ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('postocombustivel','Posto de Combustível',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_posto_combustivel','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('autorizacao','Autoriação',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('cnpjcpf','CNPJ/CPF',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('razaosocial','Razão Social',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('nomefantasia','Nome Fantasia',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('numerodespacho','Número de Despacho',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('bandeira','Bandeira',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('tipoposto','Tipo do Posto',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('latitude','Latitude',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('longitude','Longitude',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('geom','Geometria',1);


--- TABELA TIPOCOMBUSTIVEL ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('tipocombustivel','Tipo de Combustível',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_tipo_combustivel','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('tipo_combustivel','Tipo',1);


--- TABELA POSTOCOMBUSTIVEL_TIPOCOMBUSTIVEL_ANP ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('postocombustivel_tipocombustivel_anp','Mapeamento de preços dos combustíveis (ANP)',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_posto_combustivel','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_tipo_combustivel','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('preco','Preço',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('data','Data',1);


--- TABELA POSTOCOMBUSTIVEL_TIPOCOMBUSTIVEL_USUARIO ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('postocombustivel_tipocombustivel_usuario','Mapeamento de preços dos combustíveis (Usuários)',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_posto_combustivel','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_tipo_combustivel','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_usuario','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('preco','Preço',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('data','Data',1);


--- TABELA AVALIACAOANP ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('avaliacaoanp','Avaliação da ANP',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_avaliacao_anp','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('avaliacao','Avaliação',1);


--- TABELA AVALIACAO_POSTOCOMBUSTIVEL_ANP ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('avaliacao_postocombustivel_anp','Avaliação dos Postos de Combustíveis (ANP)',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_posto_combustivel','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_avaliacao_anp','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('data','Data',1);



--- TABELA AVALIACAO_ENTIDADE_USUARIO --- 
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('avaliacao_entidade_usuario','Avaliação de Local (Usuário)',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_entidade','ID',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_usuario','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('nota','Nota',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('data','Data',1);



--- TABELA DENUNCIA ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('denuncia','Denúncia',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_denuncia','ID',1);
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('reclamacao','Reclamação',1);


--- TABELA ENTIDADE_DENUNCIA ---
INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('entidade_denuncia','Mapeamento dos Locais para as Denúncias',1);
-- Já tem INSERT INTO Traducao(coluna_banco,traducao,id_lingua) VALUES ('id_denuncia','ID',1);



/*
 * VIEWS PARA O GEOSERVER
 */

CREATE OR REPLACE VIEW entidade_postos AS SELECT DISTINCT * FROM entidade e INNER JOIN postocombustivel p ON e.id_entidade = p.id_posto_combustivel ;

CREATE OR REPLACE VIEW ultimos_anp AS
SELECT DISTINCT id_posto_combustivel, id_tipo_combustivel, MAX(data) as data FROM postocombustivel_tipocombustivel_anp
GROUP BY id_posto_combustivel, id_tipo_combustivel;


CREATE OR REPLACE VIEW ultimos_precos_anp AS
SELECT DISTINCT u.id_posto_combustivel, t.tipo_combustivel, p.preco, p.data
FROM postocombustivel_tipocombustivel_anp p INNER JOIN 
	ultimos_anp u ON p.id_posto_combustivel = u.id_posto_combustivel 
			AND p.data = u.data AND p.id_tipo_combustivel = u.id_tipo_combustivel
	INNER JOIN tipocombustivel t ON t.id_tipo_combustivel = p.id_tipo_combustivel;



CREATE OR REPLACE VIEW ultimos_usuario AS
SELECT DISTINCT id_posto_combustivel, id_tipo_combustivel, MAX(data) as data 
FROM postocombustivel_tipocombustivel_usuario
GROUP BY id_posto_combustivel, id_tipo_combustivel;


CREATE OR REPLACE VIEW ultimos_precos_usuario AS
SELECT DISTINCT u.id_posto_combustivel, t.tipo_combustivel, p.preco, p.data
FROM postocombustivel_tipocombustivel_usuario p INNER JOIN 
	ultimos_usuario u ON p.id_posto_combustivel = u.id_posto_combustivel 
			AND p.data = u.data AND p.id_tipo_combustivel = u.id_tipo_combustivel
	INNER JOIN tipocombustivel t ON t.id_tipo_combustivel = p.id_tipo_combustivel;

