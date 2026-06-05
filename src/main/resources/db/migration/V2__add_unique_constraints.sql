ALTER TABLE film
    ADD CONSTRAINT uk_film_title_year UNIQUE (title, release_year);

ALTER TABLE actor
    ADD CONSTRAINT uk_actor_name UNIQUE (name);

ALTER TABLE director
    ADD CONSTRAINT uk_director_name UNIQUE (name);