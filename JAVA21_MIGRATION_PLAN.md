# Java 21 Migration Plan: User Management Microservice

## Executive Summary

This document outlines the comprehensive plan to modernize the User Management microservice from Java 17 to Java 21, leveraging new language features while maintaining backward compatibility and system stability.

---

## 1. Current State Analysis

### 1.1 Technology Stack (Current)
- **Java Version**: 17
- **Spring Boot**: 3.2.0
- **Gradle**: 8.13
- **Build Tool**: Gradle with Kotlin DSL support
- **Database**: MongoDB
- **Testing Frameworks**: 
  - Spock 2.3 (Groovy 4.0)
  - Karate 1.4.1
  - TestContainers 1.19.3

### 1.2 Key Dependencies
```
Spring Boot Starter Web: 3.2.0
Spring Boot Starter Data MongoDB: 3.2.0
Spring Boot Starter Validation: 3.2.0
SpringDoc OpenAPI: 2.2.0
Lombok: (managed by Spring Boot)
Spock Framework: 2.3-groovy-4.0
Karate: 1.4.1
TestContainers: 1.19.3
Groovy: 4.0.15
```

### 1.3 Project Structure
```
user-management/
├── src/main/java/com/abdul/usermanagement/
│   ├── controller/      # REST endpoints
│   ├── service/         # Business logic
│   ├── repository/      # MongoDB data access
│   ├── model/           # Domain entities
│   ├── dto/             # Data transfer objects
│   └── exception/       # Exception handling
├── src/main/resources/
│   └── application.yml
└── src/test/
    ├── groovy/          # Spock unit tests
    └── java/            # Karate API tests
```

### 1.4 Current Java 17 Features in Use
- Records: Not currently used (opportunity for modernization)
- Sealed classes: Not currently used
- Pattern matching: Not currently used
- Text blocks: Not currently used
- Switch expressions: Not currently used
- Stream API enhancements: Basic usage in service layer

### 1.5 Code Patterns Identified
- **Lombok heavy usage**: `@Data`, `@Builder`, `@RequiredArgsConstructor`, `@Slf4j`
- **Stream operations**: Basic `map()` and `collect()` in service layer
- **Optional handling**: Standard `orElseThrow()` pattern
- **Validation**: Jakarta Bean Validation annotations
- **Exception handling**: Global exception handler with `@RestControllerAdvice`

---

## 2. Java 21 Features Assessment

### 2.1 Features to Implement (Balanced Approach)

#### High Priority - Clear Benefits
1. **Virtual Threads (JEP 444)**
   - Enable for Tomcat thread pool
   - Improve concurrent request handling
   - Minimal code changes required

2. **Sequenced Collections (JEP 431)**
   - Use `getFirst()`, `getLast()` where applicable
   - Cleaner collection operations

3. **Pattern Matching for switch (JEP 441)**
   - Refactor exception handling
   - Simplify type checking logic

4. **Record Patterns (JEP 440)**
   - Convert DTOs to records where immutability is desired
   - Reduce boilerplate in data classes

#### Medium Priority - Selective Implementation
5. **String Templates (Preview in JDK 21)**
   - Use for logging and string formatting
   - Improve readability in complex string operations

6. **Unnamed Patterns and Variables (JEP 443)**
   - Simplify code where variables aren't used
   - Cleaner switch statements

#### Low Priority - Future Consideration
7. **Structured Concurrency (Preview)**
   - Consider for future async operations
   - Not immediately applicable to current codebase

---

## 3. Dependency Compatibility Analysis

### 3.1 Dependencies Requiring Updates

| Dependency | Current Version | Java 21 Compatible Version | Notes |
|------------|----------------|---------------------------|-------|
| Spring Boot | 3.2.0 | 3.3.0+ | Full Java 21 support |
| Gradle | 8.13 | 8.5+ | Already compatible |
| Lombok | (managed) | 1.18.30+ | Java 21 support |
| Spock | 2.3-groovy-4.0 | 2.4-M4-groovy-4.0+ | Latest milestone |
| Groovy | 4.0.15 | 4.0.21+ | Java 21 compatibility |
| Karate | 1.4.1 | 1.4.1+ | Already compatible |
| TestContainers | 1.19.3 | 1.19.8+ | Latest stable |
| SpringDoc OpenAPI | 2.2.0 | 2.5.0+ | Latest stable |

### 3.2 No Breaking Changes Expected
- MongoDB driver (managed by Spring Boot)
- Jakarta Validation API
- SLF4J logging

---

## 4. Detailed Migration Plan

### Phase 1: Codebase Analysis and Documentation ✓
**Status**: Completed
- [x] Analyze current Java version and features
- [x] Document all dependencies and versions
- [x] Identify code patterns and architecture
- [x] Assess Java 21 feature applicability
- [x] Create compatibility matrix

### Phase 2: Pre-Migration Preparation
**Duration**: 30 minutes
**Tasks**:
- [ ] Create backup branch of current codebase
- [ ] Document current test coverage metrics
- [ ] Verify Java 21 JDK installation
- [ ] Verify MongoDB availability
- [ ] Run baseline tests to establish success criteria

### Phase 3: Update Build Configuration for Java 21
**Duration**: 15 minutes
**Files to Modify**:
- [ ] `build.gradle`: Update `sourceCompatibility` and `targetCompatibility` to 21
- [ ] `gradle/wrapper/gradle-wrapper.properties`: Verify Gradle 8.5+ (already 8.13)
- [ ] Add Java 21 specific compiler flags if needed

**Changes**:
```gradle
java {
    sourceCompatibility = '21'
    targetCompatibility = '21'
}
```

### Phase 4: Update Dependencies to Java 21 Compatible Versions
**Duration**: 30 minutes
**Dependencies to Update**:
- [ ] Spring Boot: 3.2.0 → 3.3.5
- [ ] Spock Framework: 2.3-groovy-4.0 → 2.4-M4-groovy-4.0
- [ ] Groovy: 4.0.15 → 4.0.21
- [ ] TestContainers: 1.19.3 → 1.19.8
- [ ] SpringDoc OpenAPI: 2.2.0 → 2.5.0
- [ ] Lombok: Verify latest version via Spring Boot dependency management

### Phase 5: Refactor Code with Java 21 Features
**Duration**: 2-3 hours

#### 5.1 Enable Virtual Threads
**File**: `application.yml`
```yaml
spring:
  threads:
    virtual:
      enabled: true
```

#### 5.2 Convert DTOs to Records
**Files**: 
- [ ] `UserRequest.java` → Consider record conversion
- [ ] `UserResponse.java` → Convert to record
- [ ] `ErrorResponse.java` → Convert to record

**Benefits**: Immutability, reduced boilerplate, better pattern matching

#### 5.3 Implement Sequenced Collections
**File**: `UserService.java`
- [ ] Use `getFirst()` and `getLast()` for list operations
- [ ] Simplify collection access patterns

#### 5.4 Pattern Matching Enhancements
**File**: `GlobalExceptionHandler.java`
- [ ] Refactor exception handling with pattern matching for switch
- [ ] Simplify type checking logic

#### 5.5 String Templates for Logging
**Files**: `UserService.java`, `UserController.java`
- [ ] Replace string concatenation with string templates
- [ ] Improve log message readability

### Phase 6: Address Breaking Changes and Deprecations
**Duration**: 1 hour
**Tasks**:
- [ ] Review compiler warnings
- [ ] Check for deprecated API usage
- [ ] Update any reflection-based code
- [ ] Verify annotation processing compatibility
- [ ] Test Lombok annotation processing with Java 21

### Phase 7: Build and Compile with Java 21
**Duration**: 30 minutes
**Commands**:
```bash
./gradlew clean
./gradlew build -x test
```
**Validation**:
- [ ] No compilation errors
- [ ] No critical warnings
- [ ] All classes compile successfully
- [ ] JAR file generated correctly

### Phase 8: Run Application with Java 21 Runtime
**Duration**: 30 minutes
**Steps**:
- [ ] Start MongoDB (Docker or local)
- [ ] Run application: `./gradlew bootRun`
- [ ] Verify startup logs
- [ ] Check virtual threads activation
- [ ] Test health endpoint
- [ ] Verify Swagger UI accessibility

### Phase 9: Execute Comprehensive Testing
**Duration**: 1-2 hours

#### 9.1 Unit Tests (Spock)
```bash
./gradlew test --tests "*Spec"
```
- [ ] All Spock tests pass
- [ ] No test failures or errors
- [ ] Verify mock behavior unchanged

#### 9.2 API Tests (Karate)
```bash
./gradlew test --tests "*KarateTest"
```
- [ ] All Karate scenarios pass
- [ ] API contracts maintained
- [ ] Response formats unchanged

#### 9.3 Integration Tests
```bash
./gradlew test
```
- [ ] Full test suite passes
- [ ] TestContainers work correctly
- [ ] MongoDB integration stable

#### 9.4 Manual API Testing
- [ ] Test all CRUD operations via Swagger UI
- [ ] Verify search functionality
- [ ] Test error handling
- [ ] Validate response formats

### Phase 10: Performance Validation and Optimization
**Duration**: 1 hour
**Metrics to Measure**:
- [ ] Application startup time (Java 17 vs Java 21)
- [ ] Memory usage baseline
- [ ] Request throughput (concurrent requests)
- [ ] Response time percentiles (p50, p95, p99)
- [ ] Virtual threads effectiveness

**Tools**:
- JMeter or Apache Bench for load testing
- JVM monitoring (VisualVM or JConsole)
- Application logs analysis

### Phase 11: Documentation Updates
**Duration**: 1 hour
**Files to Update**:
- [ ] `README.md`: Update Java version requirements
- [ ] `README.md`: Update technology stack section
- [ ] `README.md`: Add Java 21 features section
- [ ] `README.md`: Update prerequisites
- [ ] Create `MIGRATION_NOTES.md`: Document changes and benefits
- [ ] Update API documentation if needed
- [ ] Document new features utilized

### Phase 12: Create New Repository and Check-in
**Duration**: 30 minutes
**Steps**:
- [ ] Initialize new Git repository: `user-management-java21`
- [ ] Copy all source files to new repository
- [ ] Create `.gitignore` file
- [ ] Initial commit with message: "Initial commit: Java 21 migration"
- [ ] Create migration summary commit
- [ ] Tag release: `v1.0.0-java21`
- [ ] Prepare for remote push (if applicable)

---

## 5. Specific Code Refactoring Examples

### 5.1 Convert UserResponse to Record

**Before (Java 17)**:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

**After (Java 21)**:
```java
public record UserResponse(
    String id,
    String firstName,
    String lastName,
    String address,
    Integer age,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    // Custom builder if needed
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        // Builder implementation for backward compatibility
    }
}
```

### 5.2 Pattern Matching in Exception Handler

**Before (Java 17)**:
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGlobalException(
        Exception ex, WebRequest request) {
    if (ex instanceof UserNotFoundException) {
        // handle UserNotFoundException
    } else if (ex instanceof MethodArgumentNotValidException) {
        // handle validation exception
    }
    // default handling
}
```

**After (Java 21)**:
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleGlobalException(
        Exception ex, WebRequest request) {
    return switch (ex) {
        case UserNotFoundException unf -> handleUserNotFound(unf, request);
        case MethodArgumentNotValidException manv -> handleValidation(manv, request);
        default -> handleDefault(ex, request);
    };
}
```

### 5.3 Sequenced Collections in Service

**Before (Java 17)**:
```java
List<User> users = userRepository.findAll();
if (!users.isEmpty()) {
    User firstUser = users.get(0);
    User lastUser = users.get(users.size() - 1);
}
```

**After (Java 21)**:
```java
List<User> users = userRepository.findAll();
if (!users.isEmpty()) {
    User firstUser = users.getFirst();
    User lastUser = users.getLast();
}
```

### 5.4 String Templates for Logging

**Before (Java 17)**:
```java
log.debug("Creating user with firstName: {}, lastName: {}, age: {}",
    request.getFirstName(), request.getLastName(), request.getAge());
```

**After (Java 21)** (if using preview features):
```java
log.debug(STR."Creating user with firstName: \{request.getFirstName()}, lastName: \{request.getLastName()}, age: \{request.getAge()}");
```

---

## 6. Risk Assessment and Mitigation

### 6.1 Risks

| Risk | Probability | Impact | Mitigation |
|------|------------|--------|------------|
| Dependency incompatibility | Low | High | Test all dependencies before migration |
| Test failures | Medium | Medium | Run comprehensive test suite |
| Performance regression | Low | Medium | Benchmark before and after |
| Breaking API changes | Low | High | Maintain API contracts |
| Lombok compatibility issues | Low | Medium | Verify annotation processing |

### 6.2 Rollback Plan
- Keep Java 17 branch as backup
- Document all changes for easy reversion
- Maintain separate repository for Java 21 version
- Test rollback procedure before production deployment

---

## 7. Success Criteria

### 7.1 Functional Requirements
- ✓ All existing features work identically
- ✓ All API endpoints respond correctly
- ✓ Database operations function properly
- ✓ Error handling maintains same behavior
- ✓ Validation rules unchanged

### 7.2 Technical Requirements
- ✓ Application compiles without errors
- ✓ All tests pass (100% success rate)
- ✓ No critical warnings in build output
- ✓ Application starts successfully
- ✓ Swagger UI accessible and functional

### 7.3 Performance Requirements
- ✓ Startup time: ≤ Java 17 baseline
- ✓ Memory usage: ≤ 110% of Java 17 baseline
- ✓ Request throughput: ≥ Java 17 baseline
- ✓ Response time p95: ≤ Java 17 baseline

### 7.4 Code Quality Requirements
- ✓ Code coverage maintained or improved
- ✓ No new SonarQube issues
- ✓ Documentation updated
- ✓ Clean commit history

---

## 8. Timeline Estimate

| Phase | Duration | Dependencies |
|-------|----------|--------------|
| Phase 1: Analysis | 1 hour | None |
| Phase 2: Preparation | 30 min | Phase 1 |
| Phase 3: Build Config | 15 min | Phase 2 |
| Phase 4: Dependencies | 30 min | Phase 3 |
| Phase 5: Code Refactoring | 2-3 hours | Phase 4 |
| Phase 6: Breaking Changes | 1 hour | Phase 5 |
| Phase 7: Build | 30 min | Phase 6 |
| Phase 8: Run Application | 30 min | Phase 7 |
| Phase 9: Testing | 1-2 hours | Phase 8 |
| Phase 10: Performance | 1 hour | Phase 9 |
| Phase 11: Documentation | 1 hour | Phase 10 |
| Phase 12: Repository | 30 min | Phase 11 |
| **Total** | **8-11 hours** | |

---

## 9. Post-Migration Tasks

### 9.1 Immediate
- [ ] Monitor application in development environment
- [ ] Review logs for any warnings or errors
- [ ] Validate all API endpoints
- [ ] Update CI/CD pipeline for Java 21

### 9.2 Short-term (1-2 weeks)
- [ ] Gather performance metrics
- [ ] Collect feedback from development team
- [ ] Identify additional optimization opportunities
- [ ] Plan for production deployment

### 9.3 Long-term
- [ ] Explore additional Java 21 features
- [ ] Consider structured concurrency for async operations
- [ ] Evaluate virtual threads performance in production
- [ ] Plan for future Java LTS upgrades

---

## 10. References and Resources

### 10.1 Java 21 Documentation
- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)
- [JEP 431: Sequenced Collections](https://openjdk.org/jeps/431)
- [JEP 441: Pattern Matching for switch](https://openjdk.org/jeps/441)
- [JEP 440: Record Patterns](https://openjdk.org/jeps/440)
- [JEP 430: String Templates (Preview)](https://openjdk.org/jeps/430)

### 10.2 Spring Boot 3.3 Documentation
- [Spring Boot 3.3 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.3-Release-Notes)
- [Spring Boot Java 21 Support](https://spring.io/blog/2023/09/20/hello-java-21)

### 10.3 Migration Guides
- [Oracle Java 21 Migration Guide](https://docs.oracle.com/en/java/javase/21/migrate/)
- [Spring Boot Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.3-Migration-Guide)

---

## 11. Appendix

### 11.1 Build Configuration Changes

**build.gradle** (Key Changes):
```gradle
plugins {
    id 'java'
    id 'groovy'
    id 'org.springframework.boot' version '3.3.5'
    id 'io.spring.dependency-management' version '1.1.6'
}

java {
    sourceCompatibility = '21'
    targetCompatibility = '21'
}

dependencies {
    // Updated versions
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0'
    testImplementation 'org.spockframework:spock-core:2.4-M4-groovy-4.0'
    testImplementation 'org.spockframework:spock-spring:2.4-M4-groovy-4.0'
    testImplementation 'org.apache.groovy:groovy-all:4.0.21'
    testImplementation 'org.testcontainers:mongodb:1.19.8'
    testImplementation 'org.testcontainers:junit-jupiter:1.19.8'
}
```

### 11.2 Application Configuration Changes

**application.yml** (Virtual Threads):
```yaml
spring:
  application:
    name: user-management
  threads:
    virtual:
      enabled: true
  data:
    mongodb:
      uri: mongodb://localhost:27017/user-management-db
```

---

## Conclusion

This migration plan provides a comprehensive, step-by-step approach to modernizing the User Management microservice from Java 17 to Java 21. The balanced approach focuses on leveraging Java 21 features that provide clear benefits while maintaining system stability and backward compatibility.

**Key Benefits of Migration**:
1. **Performance**: Virtual threads for improved concurrency
2. **Code Quality**: Records and pattern matching for cleaner code
3. **Maintainability**: Modern language features reduce boilerplate
4. **Future-proofing**: Latest LTS version with long-term support
5. **Developer Experience**: Enhanced language features improve productivity

**Next Steps**: Review this plan, then switch to Code mode to begin implementation.