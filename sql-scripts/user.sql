CREATE TABLE user_data (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    vorname VARCHAR(20) NOT NULL,
    nachname VARCHAR(20) NOT NULL,
    strasse VARCHAR(30) NOT NULL,
    strassennr VARCHAR(20) NOT NULL,
    plz INT NOT NULL,
    stadt VARCHAR(30) NOT NULL,
    password_hash BYTEA NOT NULL,
    password_salt BYTEA NOT NULL
);

