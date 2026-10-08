# Voting Service

Spring Boot REST API for managing and evaluating parliamentary votings.

## Technologies

- Java 21
- Spring Boot 4.1.1
- Gradle
- Spring Data JPA / Hibernate
- H2 in-memory database
- Docker / Spring Boot Buildpacks

## Running the application

### Using Gradle

Windows:

```bash
.\gradlew bootRun
```

Linux/macOS:

```bash
./gradlew bootRun
```

The application starts on:

```text
http://localhost:8080
```

## Building the application

Windows:

```bash
.\gradlew clean build
```

Linux/macOS:

```bash
./gradlew clean build
```

## Docker

The Docker image can be created using the Spring Boot Gradle plugin:

```bash
.\gradlew bootBuildImage
```

The generated image is:

```text
bgachip/voting-service:0.0.1-SNAPSHOT
```

Run the application using Docker:

```bash
docker run --rm -p 8080:8080 bgachip/voting-service:0.0.1-SNAPSHOT
```

## API

### Create voting

```http
POST /szavazasok/szavazas
```

Creates and stores a new voting.

### Get representative vote

```http
GET /szavazasok/szavazat?szavazas={szavazasId}&kepviselo={kepviselo}
```

Returns the vote of a representative for a voting.

### Get voting result

```http
GET /szavazasok/eredmeny?szavazas={szavazasId}
```

Calculates and returns the result of a voting.

### Get daily votings

```http
GET /szavazasok/napi-szavazasok?datum={date}
```

Returns all votings and their results for the specified date.

Example date:

```text
2026-10-07
```

### Get representative participation average

```http
GET /szavazasok/kepviselo-reszvetel-atlag?kezdet={from}&veg={to}
```

Returns the average voting participation for the specified period. Presence votings are excluded.

Date-time parameters use ISO 8601 format.

### Get special procedure statistics

```http
GET /szavazasok/kulonleges-eljarasok-szama?kezdet={from}&veg={to}
```

Returns accepted and rejected voting counts for special procedures in the specified period.

## H2 Database

The application uses an in-memory H2 database.

H2 Console:

```text
http://localhost:8080/h2-console
```

Connection settings:

```text
JDBC URL: jdbc:h2:mem:votingdb
Username: sa
Password:
```

The database is recreated when the application is restarted.