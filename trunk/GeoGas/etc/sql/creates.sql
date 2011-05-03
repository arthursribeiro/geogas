CREATE TABLE Country(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE Region(
	id SERIAL PRIMARY KEY,
	countryId INTEGER REFERENCES Country(id) NOT NULL,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE State(
	id SERIAL PRIMARY KEY,
	regionId INTEGER REFERENCES Region(id) NOT NULL,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE City(
	id SERIAL PRIMARY KEY,
	stateId INTEGER REFERENCES State(id) NOT NULL,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE Neighborhood(
	id SERIAL PRIMARY KEY,
	cityId INTEGER REFERENCES City(id) NOT NULL,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE GasStation(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	priceGasoline DOUBLE PRECISION,
	priceGas DOUBLE PRECISION,
	priceDiesel DOUBLE PRECISION,
	priceAlcohol DOUBLE PRECISION,
	latitude DOUBLE PRECISION NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	CHECK(priceGasoline IS NOT NULL OR priceGas IS NOT NULL OR priceDiesel IS NOT NULL OR priceAlcohol IS NOT NULL)
);

--- CREATES SPATIAL ---

SELECT AddGeometryColumn('country','geom',29100,'MULTIPOLYGON',2);

SELECT AddGeometryColumn('region','geom',29100,'MULTIPOLYGON',2);

SELECT AddGeometryColumn('state','geom',29100,'MULTIPOLYGON',2);

SELECT AddGeometryColumn('city','geom',29100,'MULTIPOLYGON',2);

SELECT AddGeometryColumn('neighborhood','geom',29100,'MULTIPOLYGON',2);

SELECT AddGeometryColumn('gasstation','location',29100,'POINT',2);