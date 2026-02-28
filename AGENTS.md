# AGENTS.md

This file provides guidance to agents when working with code in this repository.

## Project Overview

This is a **User Management Microservice** built with Java 21 and Spring Boot 3.3.5. It provides a RESTful API for managing user data with MongoDB persistence. The project demonstrates modern Java 21 features including virtual threads, records, and enhanced stream operations.

### Core Technologies

- **Java 21 (LTS)** - Latest long-term support version with virtual threads
- **Spring Boot 3.3.5** - Application framework
- **MongoDB** - NoSQL database for user data persistence
- **Gradle 8.13** - Build automation tool
- **Lombok** - Reduces boilerplate code
- **SpringDoc OpenAPI 2.6.0** - API documentation (Swagger UI)

### Testing Stack

- **Spock Framework 2.4-M4** - Groovy-based unit testing with BDD style
- **Karate Framework 1.4.1** - API/functional testing
- **TestContainers 1.20.4** - Integration testing with containerized MongoDB

## Architecture

The application follows a **layered architecture pattern**:

```
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
MongoDB Database
```

### Key Components

- **UserController** - REST endpoints with pagination, caching, and correlation ID tracking
- **UserService** - Business logic for CRUD operations and search functionality
- **UserRepository** - Spring Data MongoDB repository with custom regex queries
- **User** (Entity) - Domain model with MongoDB auditing (@CreatedDate, @LastModifiedDate)
- **UserRequest/UserResponse** - DTOs implemented as Java records

### Java 21 Features in Use

1. **Virtual Threads** - Enabled via `spring.threads.virtual.enabled: true` for improved concurrency
2. **Records** - Used for DTOs (UserResponse, ErrorResponse) providing immutability
3. **Stream.toList()** - Modern collection operations without collectors
4. **Pattern Matching** - Enhanced switch expressions (ready for future use)
5. **String Templates** - Commented out, ready to enable when feature stabilizes

### Key Features

- Complete CRUD operations for user management
- Advanced search with case-insensitive regex matching on firstName/lastName
- Pagination support with configurable page size and sorting
- Caching with Caffeine (users list and individual user caches)
- Global exception handling with standardized error responses
- MongoDB auditing for automatic timestamp management
- Correlation ID tracking for request tracing (MDC-based logging)
- Comprehensive API documentation via Swagger UI

## Building and Running

### Prerequisites

- Java 21 JDK installed
- MongoDB running on `localhost:27017` (or use Docker)
- Gradle 8.5+ (wrapper included)

### Start MongoDB

```bash
# Using Docker (recommended)
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Or use local MongoDB installation
brew services start mongodb-community  # macOS
sudo systemctl start mongod            # Linux
```

### Build Commands

```bash
# Build the project
./gradlew build

# Build without running tests
./gradlew build -x test

# Clean and rebuild
./gradlew clean build
```

### Run the Application

```bash
# Run with Gradle
./gradlew bootRun

# Run the JAR directly (after building)
java -jar build/libs/user-management-0.0.1-SNAPSHOT.jar
```

The application starts on `http://localhost:8080/api`

### Testing Commands

```bash
# Run all tests
./gradlew test

# Run only Spock unit tests
./gradlew test --tests "*Spec"

# Run only Karate functional tests
./gradlew test --tests "*KarateTest"

# Run tests with detailed output
./gradlew test --info
```

## Development Conventions

### Code Style

- **Lombok annotations** - Use `@Slf4j`, `@RequiredArgsConstructor`, `@Builder` to reduce boilerplate
- **Constructor injection** - Prefer constructor injection over field injection (via `@RequiredArgsConstructor`)
- **Records for DTOs** - Use Java records for immutable data transfer objects
- **Logging** - Use SLF4J with structured logging patterns including correlation IDs
- **Validation** - Use Jakarta Bean Validation annotations (`@Valid`, `@NotBlank`, `@Min`)

### Logging Patterns

```java
// Always include correlation ID in logs
String correlationId = generateCorrelationId();
MDC.put("correlationId", correlationId);
try {
    log.debug("Operation details (correlationId: {})", correlationId);
    // ... operation logic
    log.info("Operation completed successfully");
} finally {
    MDC.remove("correlationId");
}
```

### API Design

- RESTful endpoints under `/api/users`
- Proper HTTP status codes (200, 201, 204, 400, 404)
- Comprehensive OpenAPI documentation with `@Operation`, `@ApiResponses`
- Request/response validation with detailed error messages
- Pagination support for list endpoints

### Testing Approach

1. **Unit Tests (Spock)** - Test service layer logic with Given-When-Then pattern
2. **API Tests (Karate)** - Test REST endpoints with feature files
3. **Integration Tests** - Use TestContainers for MongoDB integration testing

### Caching Strategy

- **users cache** - Paginated user lists (key: page-size-sortBy-sortDir)
- **user cache** - Individual users (key: userId)
- Cache eviction on create, update, and delete operations

### Error Handling

- Custom `UserNotFoundException` for missing users
- `GlobalExceptionHandler` with `@ControllerAdvice` for centralized error handling
- Standardized `ErrorResponse` record with timestamp, status, error, message, path, and fieldErrors

## Configuration

Key configuration in `src/main/resources/application.yml`:

- **Server**: Port 8080, context path `/api`
- **MongoDB**: URI `mongodb://localhost:27017/user-management-db`
- **Virtual Threads**: Enabled for improved concurrency
- **Caching**: Caffeine cache for users and user
- **Logging**: DEBUG level for application code, console pattern with correlation ID

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users (paginated) |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/search` | Search users by firstName/lastName |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

Access Swagger UI at: `http://localhost:8080/api/swagger-ui.html`

## Important Notes for Agents

1. **Virtual Threads**: The application uses Java 21 virtual threads. Be aware of this when debugging concurrency issues.

2. **Records vs Classes**: DTOs use records for immutability. Don't try to add setters or make them mutable.

3. **Caching**: Remember to evict caches when modifying data. The controller already handles this with `@CacheEvict`.

4. **Correlation IDs**: Always use MDC for correlation ID tracking in new endpoints. Follow the existing pattern.

5. **MongoDB Auditing**: `createdAt` and `updatedAt` are automatically managed. Don't set them manually.

6. **Testing**: Write Spock tests for service layer, Karate tests for API endpoints. Follow existing patterns.

7. **Validation**: Use Jakarta validation annotations on DTOs. The framework handles validation automatically.

8. **Error Handling**: Don't create new exception handlers. Use existing `GlobalExceptionHandler` or extend it.

9. **Logging**: Use structured logging with placeholders `{}`, not string concatenation. Include correlation IDs.

10. **Future Java 21 Features**: String templates are commented out but ready to enable when the feature stabilizes.
