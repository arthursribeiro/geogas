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
	tipo VARCHAR(255)
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