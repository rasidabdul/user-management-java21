# User Management Microservice

A RESTful microservice for managing user data, built with **Java 21** and **Spring Boot 3.3.5**, backed by **MongoDB**.

## Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 (LTS) |
| Framework | Spring Boot 3.3.5 |
| Database | MongoDB |
| Build | Gradle 8.13 |
| API Docs | SpringDoc OpenAPI 2.6.0 |
| Unit Tests | Spock Framework 2.4-M4 |
| API Tests | Karate Framework 1.4.1 |
| Integration | TestContainers 1.20.4 |

## Java 21 Highlights

- **Virtual Threads** — enabled via `spring.threads.virtual.enabled: true`
- **Records** — [`UserResponse`](src/main/java/com/abdul/usermanagement/dto/UserResponse.java) and [`ErrorResponse`](src/main/java/com/abdul/usermanagement/exception/ErrorResponse.java) are immutable records
- **Stream.toList()** — modern collection operations without `Collectors`

## Architecture

```
UserController  →  UserService  →  UserRepository  →  MongoDB
```

Key source files:

- [`UserController.java`](src/main/java/com/abdul/usermanagement/controller/UserController.java) — REST endpoints, caching, correlation ID tracking
- [`UserService.java`](src/main/java/com/abdul/usermanagement/service/UserService.java) — business logic, CRUD, search
- [`UserRepository.java`](src/main/java/com/abdul/usermanagement/repository/UserRepository.java) — Spring Data MongoDB with regex queries
- [`User.java`](src/main/java/com/abdul/usermanagement/model/User.java) — domain entity with MongoDB auditing
- [`GlobalExceptionHandler.java`](src/main/java/com/abdul/usermanagement/exception/GlobalExceptionHandler.java) — centralized error handling
- [`application.yml`](src/main/resources/application.yml) — all runtime configuration

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users` | List users (paginated) |
| `GET` | `/api/users/{id}` | Get user by ID |
| `GET` | `/api/users/search` | Search by `firstName` / `lastName` |
| `POST` | `/api/users` | Create user |
| `PUT` | `/api/users/{id}` | Update user |
| `DELETE` | `/api/users/{id}` | Delete user |

## Quick Start

**1. Start MongoDB**
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

**2. Run the application**
```bash
./gradlew bootRun
```

App runs at `http://localhost:8080/api`  
Swagger UI: `http://localhost:8080/api/swagger-ui.html`

## Testing

```bash
./gradlew test                        # all tests
./gradlew test --tests "*Spec"        # Spock unit tests
./gradlew test --tests "*KarateTest"  # Karate API tests
```

## User Model

| Field | Required | Notes |
|-------|----------|-------|
| `id` | auto | MongoDB ObjectId |
| `firstName` | ✅ | not blank |
| `lastName` | — | optional |
| `address` | — | optional |
| `age` | ✅ | min: 1 |
| `createdAt` / `updatedAt` | auto | managed by MongoDB auditing |

## Error Response Format

```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/users",
  "fieldErrors": {
    "firstName": "First name must not be empty"
  }
}
```

## License

Created for educational and development purposes.
