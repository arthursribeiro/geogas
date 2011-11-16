CREATE TABLE Usuario(
	nome VARCHAR(255) NOT NULL,
	facebook_id VARCHAR(255) NOT NULL,
	cpf character varying(255),
	data_nascimento time without time zone,
	chave_facebook VARCHAR(255),
	CONSTRAINT pk_usuario PRIMARY KEY (facebook_id)
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
	pricegasoline double precision,
	pricealcohol double precision,
	pricediesel double precision,
	pricegas double precision,
	pricegasoline_user double precision,
	pricealcohol_user double precision,
	pricediesel_user double precision,
	pricegas_user double precision,
	autuacoes integer DEFAULT 0,
	denuncias integer DEFAULT 0,
	img VARCHAR(255) DEFAULT 'posto.jpg',
	CONSTRAINT pk_gasstation PRIMARY KEY (id_posto_combustivel)
);

CREATE TABLE AvaliacaoANP(
	id_avaliacao_anp INTEGER PRIMARY KEY,
	avaliacao VARCHAR(255) NOT NULL
);

CREATE TABLE Avaliacao_Entidade_Usuario(
	id_entidade INTEGER REFERENCES Entidade(id_entidade),
	id_usuario VARCHAR(255) REFERENCES Usuario(facebook_id),
	nota INTEGER NOT NULL,
	data TIMESTAMP NOT NULL,
	CONSTRAINT pk_avaliacao_postocombustivel_usuario PRIMARY KEY (id_entidade, id_usuario)
);


CREATE TABLE Denuncia(
	id_denuncia SERIAL PRIMARY KEY,
	reclamacao VARCHAR(255) NOT NULL,
	id_usuario VARCHAR(255),
	CONSTRAINT fk_usuario_denuncia FOREIGN KEY (id_usuario) REFERENCES usuario (facebook_id)
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

CREATE TABLE historico_precos_anp(
	id SERIAL NOT NULL,
	id_posto_combustivel integer NOT NULL,
	pricegasoline double precision,
	pricealcohol double precision,
	pricediesel double precision,
	pricegas double precision,
	data TIMESTAMP,
	CONSTRAINT fk_postocombustivel FOREIGN KEY (id_posto_combustivel) REFERENCES PostoCombustivel(id_posto_combustivel)
);

CREATE TABLE historico_precos_usuario(
	id SERIAL NOT NULL,
	id_posto_combustivel integer NOT NULL,
	id_usuario VARCHAR NOT NULL,
	pricegasoline double precision,
	pricealcohol double precision,
	pricediesel double precision,
	pricegas double precision,
	data TIMESTAMP,
	CONSTRAINT fk_postocombustivel_usuario FOREIGN KEY (id_posto_combustivel) REFERENCES PostoCombustivel(id_posto_combustivel),
	CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(facebook_id)
);

CREATE TABLE Autuacoes_ANP(
	id_posto_combustivel integer NOT NULL,
	autuacao VARCHAR(255),
	data TIMESTAMP,
	CONSTRAINT pk_autuacoes PRIMARY KEY (id_posto_combustivel,data),
	CONSTRAINT fk_postocombustivel FOREIGN KEY (id_posto_combustivel) REFERENCES PostoCombustivel(id_posto_combustivel)
);

CREATE TABLE layers(
	label VARCHAR(255) NOT NULL,
	prop_name VARCHAR(255) NOT NULL,
	islike_value VARCHAR(255),
	min_value DOUBLE PRECISION,
	max_value DOUBLE PRECISION,
	folder VARCHAR(255) NOT NULL,
	has_user_prop INTEGER DEFAULT 0
);

INSERT INTO layers(folder,prop_name,label,islike_value,min_value,max_value,has_user_prop) VALUES
('Bandeira','bandeira','BRANCA','%BRANCA%',null,null,0),
('Bandeira','bandeira','PETROBRAS','PETROBRAS%',null,null,0),
('Bandeira','bandeira','SHELL','SHELL%',null,null,0),
('Bandeira','bandeira','SETTA','%SETTA%',null,null,0),
('Bandeira','bandeira','DISLUB','DISLUB%',null,null,0),
('Bandeira','bandeira','ALESAT','ALESAT%',null,null,0),
('Bandeira','bandeira','COSAN','COSAN%',null,null,0),
('Bandeira','bandeira','ELLO','ELLO%',null,null,0),
('Bandeira','bandeira','FAN','FAN%',null,null,0),
('Bandeira','bandeira','FEDERAL','FEDERAL%',null,null,0),
('Bandeira','bandeira','IPP','IPP%',null,null,0),
('Bandeira','bandeira','ALVO','ALVO%',null,null,0),
('Bandeira','bandeira','SATELITE','SATELITE%',null,null,0),
('Bandeira','bandeira','SP','SP%',null,null,0),
('Bandeira','bandeira','TEMAPE','TEMAPE%',null,null,0),
('Bandeira','bandeira','TOTAL','TOTAL%',null,null,0),

('Tipo Comb.','pricegasoline','Gasolina',null,0,1000,1),
('Tipo Comb.','pricealcohol','Alcool',null,0,1000,1),
('Tipo Comb.','pricediesel','Diesel',null,0,1000,1),
('Tipo Comb.','pricegas','Gas',null,0,1000,1),

('Aval. ANP','autuacoes','Autuados',null,1,1000000,0),
('Aval. ANP','autuacoes','Nao Autuados',null,-1,0,0),

('Aval. Usuarios','denuncias','Com denuncias',null,1,1000000,0),
('Aval. Usuarios','denuncias','Sem denuncias',null,-1,0,0);


CREATE OR REPLACE FUNCTION update_postocombustivel_anp() RETURNS TRIGGER AS $update_postocombustivel_anp$
BEGIN
UPDATE postocombustivel SET pricegasoline = NEW.pricegasoline,
				pricealcohol = NEW.pricealcohol,
				pricediesel = NEW.pricediesel,
				pricegas = NEW.pricegas
			WHERE id_posto_combustivel = NEW.id_posto_combustivel;
RETURN NEW;
END;
$update_postocombustivel_anp$ LANGUAGE plpgsql;

CREATE TRIGGER update_postocombustivel_anp
    BEFORE INSERT OR UPDATE ON historico_precos_anp
    FOR EACH ROW EXECUTE PROCEDURE update_postocombustivel_anp();


CREATE OR REPLACE FUNCTION update_postocombustivel_usuario() RETURNS TRIGGER AS $update_postocombustivel_usuario$
BEGIN
UPDATE postocombustivel SET pricegasoline_user = NEW.pricegasoline,
				pricealcohol_user = NEW.pricealcohol,
				pricediesel_user = NEW.pricediesel,
				pricegas_user = NEW.pricegas
			WHERE id_posto_combustivel = NEW.id_posto_combustivel;
RETURN NEW;
END;
$update_postocombustivel_usuario$ LANGUAGE plpgsql;

CREATE TRIGGER update_postocombustivel_usuario
    BEFORE INSERT OR UPDATE ON historico_precos_usuario
    FOR EACH ROW EXECUTE PROCEDURE update_postocombustivel_usuario();
    
CREATE OR REPLACE FUNCTION update_autuacoes_anp() RETURNS TRIGGER AS $update_autuacoes_anp$
BEGIN
UPDATE postocombustivel SET autuacoes = autuacoes+1
			WHERE id_posto_combustivel = NEW.id_posto_combustivel;
RETURN NEW;
END;
$update_autuacoes_anp$ LANGUAGE plpgsql;

CREATE TRIGGER update_autuacoes_anp
    BEFORE INSERT OR UPDATE ON Autuacoes_ANP
    FOR EACH ROW EXECUTE PROCEDURE update_autuacoes_anp();
    
CREATE OR REPLACE FUNCTION update_denuncias_usuarios() RETURNS TRIGGER AS $update_denuncias_usuarios$
BEGIN
UPDATE postocombustivel SET denuncias = denuncias+1
			WHERE id_posto_combustivel = NEW.id_posto_combustivel;
RETURN NEW;
END;
$update_denuncias_usuarios$ LANGUAGE plpgsql;

CREATE TRIGGER update_denuncias_usuarios
    BEFORE INSERT OR UPDATE ON Denuncia
    FOR EACH ROW EXECUTE PROCEDURE update_denuncias_usuarios();
    
CREATE OR REPLACE VIEW gasstation AS 
 SELECT p.nomefantasia, p.razaosocial, 

	p.pricegasoline, p.pricegasoline_user, 
	p.pricealcohol, p.pricealcohol_user, 
	p.pricediesel, p.pricediesel_user, 
	p.pricegas, p.pricegas_user, 

	p.bandeira, 

	p.geom, p.latitude, p.longitude, 

	p.autuacoes, p.denuncias,

	p.img
   FROM postocombustivel p;
   
   
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
 * UPDATES DAS IMGS PARA O BANCO (rodar de vez em quando)
 */

UPDATE postocombustivel SET img='alesat.jpg' WHERE bandeira LIKE 'ALESAT%';
UPDATE postocombustivel SET img='alvo.jpg' WHERE bandeira LIKE 'ALVO%';
UPDATE postocombustivel SET img='branca.jpg' WHERE bandeira LIKE '%BRANCA%';
UPDATE postocombustivel SET img='cosan.jpg' WHERE bandeira LIKE 'COSAN%';
UPDATE postocombustivel SET img='dislub.jpg' WHERE bandeira LIKE 'DISLUB%';
UPDATE postocombustivel SET img='ello.jpg' WHERE bandeira LIKE 'ELLO%';
UPDATE postocombustivel SET img='fan.jpg' WHERE bandeira LIKE 'FAN%';
UPDATE postocombustivel SET img='federal.jpg' WHERE bandeira LIKE 'FEDERAL%';
UPDATE postocombustivel SET img='ipp.jpg' WHERE bandeira LIKE 'IPP%';
UPDATE postocombustivel SET img='petrobras.jpg' WHERE bandeira LIKE 'PETROBRAS%';
UPDATE postocombustivel SET img='satelite.jpg' WHERE bandeira LIKE 'SATELITE%';
UPDATE postocombustivel SET img='setta.jpg' WHERE bandeira LIKE 'SETTA%';
UPDATE postocombustivel SET img='shell.jpg' WHERE bandeira LIKE 'SHELL%';
UPDATE postocombustivel SET img='sp.jpg' WHERE bandeira LIKE 'SP%';
UPDATE postocombustivel SET img='temape.jpg' WHERE bandeira LIKE 'TEMAPE%';
UPDATE postocombustivel SET img='total.jpg' WHERE bandeira LIKE 'TOTAL%';
