CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE brand (
    id uuid DEFAULT uuid_generate_v4 () UNIQUE,
    name VARCHAR (255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE model (
    id uuid DEFAULT uuid_generate_v4 () UNIQUE,
    name VARCHAR (255) NOT NULL,
    brand_id uuid NOT NULL,
    CONSTRAINT brand_to_model_fk
        FOREIGN KEY (brand_id)
            REFERENCES brand(id),
    PRIMARY KEY (id)
);

CREATE TABLE model_year (
    id uuid DEFAULT uuid_generate_v4 () UNIQUE,
    model_id uuid NOT NULL,
    year INT NOT NULL,
    kbb_id INT NOT NULL,
    CONSTRAINT model_to_model_year_fk
        FOREIGN KEY (model_id)
            REFERENCES model(id),
    PRIMARY KEY (id)
);

CREATE TABLE vehicle (
    id uuid DEFAULT uuid_generate_v4 () UNIQUE,
    ad_price  NUMERIC (19, 2),
    kbb_price  NUMERIC (19, 2),
    license_plate VARCHAR (50) UNIQUE,
    model_year_id uuid NOT NULL,
    CONSTRAINT model_year_to_vehicle_fk
        FOREIGN KEY (model_year_id)
            REFERENCES model_year(id),
    PRIMARY KEY (id)
);
