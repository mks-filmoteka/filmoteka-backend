ALTER TABLE film
    ALTER COLUMN release_year SET NOT NULL,
    ALTER COLUMN description TYPE VARCHAR(1000),
    ALTER COLUMN description SET NOT NULL;

ALTER TABLE film_genres
    ADD CONSTRAINT uk_film_genres_film_id_genre UNIQUE (film_id, genre);

ALTER TABLE film_countries
    ADD CONSTRAINT uk_film_countries_film_id_country UNIQUE (film_id, country);

CREATE INDEX idx_film_genres_genre_film_id ON film_genres (genre, film_id);
CREATE INDEX idx_film_countries_country_film_id ON film_countries (country, film_id);
CREATE INDEX idx_film_actor_actor_id ON film_actor (actor_id);
CREATE INDEX idx_film_director_director_id ON film_director (director_id);
