CREATE TABLE IF NOT EXISTS xxx_user (
    id INT8 PRIMARY KEY,
    email VARCHAR(50),
    password VARCHAR,
    role VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS film (
    id INT8 PRIMARY KEY,
    code VARCHAR(10),
    type VARCHAR(50),
    description TEXT
);

CREATE TABLE IF NOT EXISTS rating (
    id INT8 PRIMARY KEY,
    user_id INT8,
    film_id INT8,
    value INT,
    comment TEXT
);

ALTER TABLE rating ADD CONSTRAINT user_id_rating_fkey FOREIGN KEY (user_id) REFERENCES xxx_user(id) ON DELETE CASCADE;

ALTER TABLE rating ADD CONSTRAINT film_id_rating_fkey FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE;