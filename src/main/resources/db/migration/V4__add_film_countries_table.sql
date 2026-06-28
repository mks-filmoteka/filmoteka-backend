CREATE TABLE film_countries
(
    film_id     BIGINT          NOT NULL,
    country     VARCHAR(100)    NOT NULL,
    CONSTRAINT fk_film_countries_film
        FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE
);

INSERT INTO film_countries (film_id, country)
SELECT id,
       CASE country
           WHEN 'USA' THEN 'UNITED_STATES'
           WHEN 'United Kingdom' THEN 'UNITED_KINGDOM'
           WHEN 'New Zealand' THEN 'NEW_ZEALAND'
           WHEN 'Australia' THEN 'AUSTRALIA'
           END
FROM film;

ALTER TABLE film DROP COLUMN country;