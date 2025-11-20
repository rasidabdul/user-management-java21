# User Management Spring Boot Microservice API Creation Prompt

Create a production-ready Spring Boot microservice API with the following specifications:

## Technology Stack
- **Java 17** (source and target compatibility)
- **Spring Boot 3.2.0**
- **Gradle** (build tool with Groovy for testing)
- **MongoDB** (with Spring Data MongoDB)
- **Lombok** (code reduction)
- **SpringDoc OpenAPI** (API documentation - Swagger UI)
- **Spring Validation** (Jakarta Bean Validation)

## Testing Framework
- **Spock Framework 2.3** (unit testing with Groovy)
- **Karate Framework 1.4.1** (functional/API testing)
- **TestContainers** (integration testing)

## Project Configuration

### Gradle Dependencies
```gradle
dependencies {
    // Core Spring Boot
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    // API Documentation
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'

    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.spockframework:spock-core:2.3-groovy-4.0'
    testImplementation 'org.spockframework:spock-spring:2.3-groovy-4.0'
    testImplementation 'com.intuit.karate:karate-junit5:1.4.1'
    testImplementation 'org.testcontainers:mongodb'
    testImplementation 'org.testcontainers:junit-jupiter'
}
```

### Application Configuration (application.yml)
```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  application:
    name: user-management
  data:
    mongodb:
      uri: mongodb://localhost:27017/[DATABASE_NAME]
      database: user-management-db

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method

logging:
  level:
    com.abdul.usermanagement: DEBUG
```

## API Specification Format

Define your microservice API using this template:

### Service Name & Description
**Service Name:** user-management
**Description:** Manages users management by enabling user creation , deletetion , edit and user searches
**Base Package:** com.abdul.usermanagement

### Domain Model
Define your main entity with fields and validation:

**Entity Name:** User
**Collection Name:** Users

| Field Name | Type | Required | Validation Rules |
|------------|------|----------|------------------|
| id | String | Auto | MongoDB ObjectId |
| firstName | String | Yes | @NotBlank|
| lastName  | String | No | @NotBlank|
| address   | String | No | |
| age       | Integer| Yes | |
| createdAt | LocalDateTime | Auto | @CreatedDate |
| updatedAt | LocalDateTime | Auto | @LastModifiedDate |

### API Endpoints
Specify REST endpoints in this format:

| Method | Endpoint | Description | Request Body | Response | Status Codes |
|--------|----------|-------------|--------------|----------|--------------|
| GET | /api/users | Get all users | - | List<user> | 200 |
| GET | /api/users/{id} | Get users by ID | - | user | 200, 404 |
| GET | /api/users/search | Search users | Query params | List<user> | 200 |
| POST | /api/users | Create new users | userRequest | user | 201, 400, 409 |
| PUT | /api/users/{id} | Update users | userRequest | us | 200, 400, 404 |
| DELETE | /api/users/{id} | Delete users | - | - | 204, 404 |

### Search Parameters
Specify searchable fields:
- firstName: regex match, case-insensitive
- lastName: regex match, case-insensitive

### Business Rules
List any specific validation or business logic:
1. Rule 1: firstName  must not be empty
2. Rule 2: age can not be zero

## Implementation Requirements

### Architecture Pattern
- **Layered Architecture:** Controller → Service → Repository
- **DTOs:** Separate request/response objects from entities
- **Exception Handling:** Global exception handler with standardized error responses

### Standard Features (Auto-Include)

1. **MongoDB Configuration**
   - Enable MongoDB auditing (@EnableMongoAuditing)
   - Auto-populate createdAt and updatedAt fields

2. **Exception Handling**
   - Custom exceptions: usNotFoundException
   - Global exception handler with @RestControllerAdvice
   - Error response format:
     ```json
     {
       "timestamp": "2024-01-01T10:00:00",
       "status": 400,
       "error": "Error Type",
       "message": "Error message",
       "path": "/api/resource"
     }
     ```
   - Validation error response with field-level errors

3. **API Documentation**
   - OpenAPI annotations on all endpoints (@Operation, @ApiResponses, @Tag)
   - Swagger UI accessible at /swagger-ui.html
   - Parameter descriptions on all endpoint arguments

4. **Logging**
   - Use @Slf4j on all service and controller classes
   - DEBUG level for method entry with parameters
   - INFO level for successful operations
   - ERROR level for exceptions

5. **Repository Pattern**
   - Extend MongoRepository<user, String>
   - Custom query methods for search functionality
   - Use @Query annotation for complex MongoDB queries

6. **Testing**
   - Spock unit tests for service layer (Given-When-Then pattern)
   - Mock repository dependencies
   - Test all CRUD operations and exception scenarios
   - Karate functional tests for API endpoints
   - **Important**: Karate test structure:
     - Test runner class: `src/test/java/[package]/karate/[TestName].java`
     - Feature files: `src/test/resources/[package]/karate/[feature-name].feature`

## Build, Run & Test Instructions

### Prerequisites
```bash
# Required
- Java 17 JDK
- MongoDB (running on localhost:27017)
- Gradle 8.5+ (or use wrapper)
```

### Build Commands
```bash
# Build project
./gradlew build

# Run application
./gradlew bootRun

# Run tests
./gradlew test

# Skip tests and build
./gradlew build -x test
```

### Access Points
- **Application:** http://localhost:8080/api
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **OpenAPI Docs:** http://localhost:8080/api/api-docs
- **Health Check:** http://localhost:8080/api/actuator/health

### Docker Setup (Optional)
```bash
# Start MongoDB
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Build Docker image
./gradlew bootBuildImage

# Run with Docker Compose (create docker-compose.yml)
docker-compose up
```

## Example Usage

Here's how to specify a simple User Management microservice:

```
Service Name: user-management-service
Description: Manages user profiles and authentication
Base Package: com.company.usermanagement

Entity: User
Collection: users

Fields:
| username | String | Yes | @NotBlank, @Size(min=3, max=50), unique |
| email | String | Yes | @NotBlank, @Email, unique |
| fullName | String | Yes | @NotBlank, @Size(max=100) |
| age | Integer | No | @Min(18), @Max(120) |
| status | String | Yes | @Pattern(regexp="ACTIVE|INACTIVE|SUSPENDED") |

Endpoints:
- GET /api/users - List all users
- GET /api/users/{id} - Get user by ID
- GET /api/users/search?username=X&status=Y - Search users
- POST /api/users - Create user
- PUT /api/users/{id} - Update user
- DELETE /api/users/{id} - Delete user

Search Parameters:
- username: partial match, case-insensitive
- email: exact match
- status: exact match

Business Rules:
1. Username must be unique across all users
2. Email must be unique across all users
3. Default status is ACTIVE when creating user
```

## Additional Features (Optional)

Include these if needed:

### External API Integration
```
Add WebClient/RestTemplate integration for external APIs:
- API base URL configuration
- DTO mapping for external responses
- Error handling for API failures
- Separate client package with dedicated service
```

### Additional Dependencies
```gradle
// For external API calls
implementation 'org.springframework.boot:spring-boot-starter-webflux'

// For specific integrations
implementation '[groupId]:[artifactId]:[version]'
```

### Environment Variables
```yaml
# Add custom configuration
custom:
  property:
    value: ${ENV_VAR_NAME:default_value}
```

## Output Deliverables

The generated project should include:

1. **Source Code**
   - Main application class
   - Entity, DTO, Repository, Service, Controller classes
   - Configuration classes
   - Exception classes with global handler

2. **Test Code**
   - Spock unit tests for services (located in `src/test/groovy`)
   - Karate test runners (located in `src/test/java`)
   - Karate feature files (located in `src/test/resources` - **IMPORTANT**: must mirror the package structure)

3. **Configuration Files**
   - build.gradle
   - settings.gradle
   - application.yml
   - .gitignore

4. **Documentation**
   - README.md with setup and usage instructions
   - API documentation via Swagger

## Testing Structure Example

For a service with base package `com.abdul.usermanagement`:

```
src/
├── test/
    ├── groovy/
    │   └── com/abdul/usermanagement/
    │       └── service/
    │           └── UserServiceSpec.groovy         # Spock unit tests
    ├── java/
    │   └── com/abdul/usermanagement/
    │       └── karate/
    │           └── UserApiTest.java               # Karate test runner
    └── resources/
        └── com/abdul/usermanagement/
            └── karate/
                └── user-api.feature               # Karate feature file
```

**Critical**: The Karate `.feature` file must be in `src/test/resources` with the same package path as the test runner class in `src/test/java`.

## Notes

- Use Lombok annotations (@Data, @Builder, @RequiredArgsConstructor, @Slf4j)
- Follow REST conventions and HTTP status codes
- Implement proper validation and error handling
- Include comprehensive logging
- Write tests for all business logic
- Document all public APIs with OpenAPI annotations
