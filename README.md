# Filmoteka Backend

Backend REST API for Filmoteka, an admin app for managing films, actors and directors.

The service stores film metadata in PostgreSQL. Poster image files are handled outside this app; this service stores only the poster file name.

## Tech stack

- Java 25
- Spring Boot 4
- Spring Web, Validation and Actuator
- Spring Data JPA
- PostgreSQL
- Flyway
- MapStruct
- OpenAPI / Swagger
- JUnit, Mockito and Testcontainers

## Requirements

- JDK 25
- PostgreSQL
- Docker, if you want to run the full test suite

## Run locally

Create the database:

```sql
CREATE DATABASE filmoteka;
```

The `filmoteka` schema and tables are created by Flyway migrations on startup.

Set database credentials:

```powershell
$env:FILMOTEKA_USERNAME = "your_username"
$env:FILMOTEKA_PASSWORD = "your_password"
```

For bash:

```bash
export FILMOTEKA_USERNAME=your_username
export FILMOTEKA_PASSWORD=your_password
```

Start the app:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The app runs at:

```text
http://localhost:8080
```

## API docs

```text
http://localhost:8080/swagger-ui.html
http://localhost:8080/api-docs
```

## Main endpoints

```text
GET    /api/v1/films
GET    /api/v1/films/{id}
POST   /api/v1/films
PUT    /api/v1/films/{id}
DELETE /api/v1/films/{id}

GET    /api/v1/actors/{id}
PUT    /api/v1/actors/{id}
DELETE /api/v1/actors/{id}

GET    /api/v1/directors/{id}
PUT    /api/v1/directors/{id}
DELETE /api/v1/directors/{id}
```

`GET /api/v1/films` supports filters for `title`, `yearFrom`, `yearTo`, `genres` and `countries`.

## Tests

```bash
./mvnw test
```

Some tests use Testcontainers, so Docker should be running.

## Notes

- PostgreSQL is expected on `localhost:5432`.
- The frontend dev origin `http://localhost:5173` is allowed by CORS.
- There is no authentication yet.
