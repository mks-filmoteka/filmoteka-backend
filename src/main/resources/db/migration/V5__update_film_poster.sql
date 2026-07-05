ALTER TABLE film RENAME COLUMN poster_url TO poster_name;

UPDATE film SET poster_name = NULL WHERE poster_name IS NOT NULL;