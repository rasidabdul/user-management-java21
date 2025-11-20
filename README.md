# User Management Microservice (Java 21)

A production-ready Spring Boot microservice for managing user data with MongoDB persistence, modernized with Java 21 features.

## Technology Stack

- **Java 21** (LTS)
- **Spring Boot 3.3.5**
- **Gradle 8.13** (build tool)
- **MongoDB** (database)
- **SpringDoc OpenAPI 2.6.0** (API documentation)
- **Spock Framework 2.4-M4** (unit testing)
- **Karate Framework 1.4.1** (functional/API testing)
- **TestContainers 1.20.4** (integration testing)

## Java 21 Features Utilized

- **Virtual Threads**: Enabled for improved concurrency and scalability
- **Records**: DTOs converted to immutable records (UserResponse, ErrorResponse)
- **Stream API Enhancements**: Using `.toList()` for cleaner collection operations
- **Modern Language Constructs**: Leveraging latest Java features for cleaner, more maintainable code

## Features

- Complete CRUD operations for user management
- Advanced search functionality with regex matching
- RESTful API design with proper HTTP status codes
- Comprehensive input validation
- Global exception handling with standardized error responses
- MongoDB auditing (automatic createdAt/updatedAt timestamps)
- Interactive API documentation with Swagger UI
- Comprehensive test coverage (unit and functional tests)
- **Virtual threads for enhanced performance**

## Project Structure

```
user-management/
├── src/
│   ├── main/
│   │   ├── java/com/abdul/usermanagement/
│   │   │   ├── controller/          # REST controllers
│   │   │   ├── service/              # Business logic
│   │   │   ├── repository/           # Data access layer
│   │   │   ├── model/                # Domain entities
│   │   │   ├── dto/                  # Data transfer objects
│   │   │   ├── exception/            # Custom exceptions and handlers
│   │   │   └── UserManagementApplication.java
│   │   └── resources/
│   │       └── application.yml       # Application configuration
│   └── test/
│       ├── groovy/                   # Spock unit tests
│       └── java/                     # Karate functional tests
├── build.gradle
├── settings.gradle
└── README.md
```

## API Endpoints

| Method | Endpoint | Description | Status Codes |
|--------|----------|-------------|--------------|
| GET | `/api/users` | Get all users | 200 |
| GET | `/api/users/{id}` | Get user by ID | 200, 404 |
| GET | `/api/users/search` | Search users by firstName/lastName | 200 |
| POST | `/api/users` | Create new user | 201, 400 |
| PUT | `/api/users/{id}` | Update user | 200, 400, 404 |
| DELETE | `/api/users/{id}` | Delete user | 204, 404 |

## User Model

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| id | String | Auto-generated | MongoDB ObjectId |
| firstName | String | Yes | Not blank |
| lastName | String | No | - |
| address | String | No | - |
| age | Integer | Yes | Min value: 1 |
| createdAt | LocalDateTime | Auto-generated | - |
| updatedAt | LocalDateTime | Auto-generated | - |

## Prerequisites

- **Java 21 JDK** (LTS version required)
- **MongoDB** (running on localhost:27017)
- **Gradle 8.5+** (or use the Gradle wrapper - included)

## Setup Instructions

### 1. Start MongoDB

Using Docker:
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

Or install MongoDB locally and start the service:
```bash
# macOS (using Homebrew)
brew services start mongodb-community

# Linux
sudo systemctl start mongod

# Windows
net start MongoDB
```

### 2. Clone and Build

```bash
# Navigate to project directory
cd user-management

# Build the project
./gradlew build

# Skip tests during build
./gradlew build -x test
```

### 3. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080/api`

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Spock Unit Tests Only
```bash
./gradlew test --tests "*Spec"
```

### Run Karate Functional Tests Only
```bash
./gradlew test --tests "*KarateTest"
```

## API Documentation

Once the application is running, access the interactive API documentation:

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/api-docs

## Example API Usage

### Create a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "address": "123 Main St",
    "age": 30
  }'
```

Response (201 Created):
```json
{
  "id": "507f1f77bcf86cd799439011",
  "firstName": "John",
  "lastName": "Doe",
  "address": "123 Main St",
  "age": 30,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### Get All Users

```bash
curl http://localhost:8080/api/users
```

### Get User by ID

```bash
curl http://localhost:8080/api/users/507f1f77bcf86cd799439011
```

### Search Users

```bash
# Search by firstName
curl "http://localhost:8080/api/users/search?firstName=John"

# Search by lastName
curl "http://localhost:8080/api/users/search?lastName=Doe"

# Search by both
curl "http://localhost:8080/api/users/search?firstName=John&lastName=Doe"
```

### Update User

```bash
curl -X PUT http://localhost:8080/api/users/507f1f77bcf86cd799439011 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Johnny",
    "lastName": "Doe",
    "address": "456 Oak Ave",
    "age": 31
  }'
```

### Delete User

```bash
curl -X DELETE http://localhost:8080/api/users/507f1f77bcf86cd799439011
```

## Error Handling

The API returns standardized error responses:

```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/users",
  "fieldErrors": {
    "firstName": "First name must not be empty",
    "age": "Age cannot be zero or negative"
  }
}
```

## Configuration

Edit `src/main/resources/application.yml` to customize:

- Server port
- MongoDB connection URI
- Logging levels
- API documentation paths

## Docker Support

### Build Docker Image

```bash
./gradlew bootBuildImage
```

### Run with Docker Compose

Create a `docker-compose.yml`:

```yaml
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    ports:
      - "27017:27017"
    volumes:
      - mongo-data:/data/db

  user-management:
    image: user-management:0.0.1-SNAPSHOT
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/user-management-db
    depends_on:
      - mongodb

volumes:
  mongo-data:
```

Run:
```bash
docker-compose up
```

## Development

### Code Style
- Uses Lombok annotations for boilerplate reduction
- Follows layered architecture pattern (Controller → Service → Repository)
- Comprehensive logging with SLF4J
- Input validation using Jakarta Bean Validation

### Testing Strategy
- **Unit Tests**: Spock framework with Given-When-Then pattern
- **Functional Tests**: Karate framework for API testing
- **Integration Tests**: TestContainers for MongoDB (optional)

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running on localhost:27017
- Check MongoDB connection string in application.yml
- Verify MongoDB credentials if authentication is enabled

### Build Failures
- Ensure Java 17 is installed: `java -version`
- Clean and rebuild: `./gradlew clean build`

### Port Already in Use
- Change the port in application.yml:
  ```yaml
  server:
    port: 8081
  ```

## License

This project is created for educational and development purposes.

## Support

For issues or questions, please refer to the project documentation or contact the development team.
