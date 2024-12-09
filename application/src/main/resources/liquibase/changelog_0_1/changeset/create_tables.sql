CREATE TABLE car_showrooms (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
    id SMALLINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE cars (
    id UUID PRIMARY KEY,
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    production_year SMALLINT NOT NULL,
    price NUMERIC(12, 2) NOT NULL,

    car_showroom_id UUID REFERENCES car_showrooms(id),
    category_id SMALLINT NOT NULL REFERENCES categories(id)
);

CREATE TABLE clients (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    registration_date TIMESTAMP NOT NULL
);

CREATE TABLE client_contacts (
    content VARCHAR(255) NOT NULL,
    client_id UUID NOT NULL REFERENCES clients(id)
);

CREATE TABLE clients_to_cars (
    client_id UUID,
    car_id UUID,

    PRIMARY KEY(client_id, car_id)
);

CREATE TABLE reviews (
    id UUID PRIMARY KEY,
    content VARCHAR(1000) NOT NULL,
    rating SMALLINT NOT NULL,

    client_id UUID NOT NULL REFERENCES clients(id),
    car_id UUID NOT NULL REFERENCES cars(id)
);

CREATE UNIQUE INDEX client_contacts_all_uq_idx
    ON client_contacts
    (client_id, content);