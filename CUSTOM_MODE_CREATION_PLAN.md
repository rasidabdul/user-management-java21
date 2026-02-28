# Custom Mode Creation Plan: java_modernization_17_to_21

## Executive Summary

This document provides a comprehensive plan for creating a custom Bob mode named `java_modernization_17_to_21` that will automate the migration of Java 17 projects to Java 21. The mode will analyze codebases, create migration plans, refactor code with modern Java 21 features, and validate changes through comprehensive testing.

---

## 1. Mode Overview

### 1.1 Purpose
Create an intelligent, automated custom mode that:
- **Analyzes** Java 17 projects automatically upon activation
- **Plans** comprehensive migration strategies with risk assessment
- **Refactors** code to leverage Java 21 features (Virtual Threads, Records, Pattern Matching, etc.)
- **Modernizes** codebases with best practices and optimizations
- **Validates** changes through automated testing and performance benchmarking
- **Documents** all changes and provides migration notes

### 1.2 Target Users
- Java developers migrating from Java 17 to Java 21
- Teams modernizing legacy Java applications
- Architects planning Java version upgrades
- DevOps engineers updating build pipelines

### 1.3 Key Differentiators
- **Automatic Analysis**: No manual prompting required - analyzes on activation
- **Comprehensive Planning**: 12-phase structured migration approach
- **Interactive Implementation**: Guided step-by-step with validation
- **Safety First**: Risk assessment, rollback plans, and validation at each step
- **Testing Integration**: Runs unit tests, integration tests, and performance benchmarks

---

## 2. Mode Configuration Structure

### 2.1 Basic Configuration

```yaml
customModes:
  - slug: java_modernization_17_to_21
    name: ☕ Java 17→21 Migration
    description: Automated Java 17 to Java 21 migration with code modernization
    source: global
```

### 2.2 Role Definition

The mode acts as a **Java Modernization Specialist** with expertise in:
- Java 17 and Java 21 language features
- Build tools (Gradle, Maven)
- Frameworks (Spring Boot, Quarkus, Jakarta EE)
- Testing frameworks (JUnit, Spock, Karate, TestNG)
- Performance optimization and benchmarking
- Risk assessment and mitigation strategies

### 2.3 Tool Groups Required

```yaml
groups:
  - read      # For analyzing project files
  - edit      # For refactoring code
  - command   # For running builds and tests
  - browser   # For accessing documentation (optional)
  - mcp       # For advanced integrations (optional)
```

### 2.4 When to Use

```yaml
whenToUse: >-
  Use this mode when you need to:
  - Migrate a Java 17 application to Java 21
  - Modernize Java codebases with new language features
  - Analyze dependency compatibility for Java 21
  - Refactor code to leverage Virtual Threads, Records, Pattern Matching
  - Create comprehensive migration documentation
  - Ensure safe and systematic version upgrades
  
  The mode automatically analyzes your project structure, creates a detailed 
  migration plan, and can guide you through implementation step-by-step.
```

---

## 3. Workflow and Behavior

### 3.1 Phase 1: Automatic Analysis (Immediate on Activation)

**Objective**: Understand the current state of the Java 17 project

**Actions**:
1. **Project Structure Analysis**
   - Use `list_files` to map directory structure
   - Identify source directories (src/main/java, src/test/java)
   - Locate configuration files (application.yml, application.properties)

2. **Build Configuration Analysis**
   - Use `read_file` to examine build.gradle or pom.xml
   - Extract current Java version
   - Document all dependencies and versions
   - Identify build tool version

3. **Code Pattern Detection**
   - Use `search_files` to find Java 17 features:
     - Records: `record\s+\w+`
     - Sealed classes: `sealed\s+(class|interface)`
     - Pattern matching: `instanceof\s+\w+\s+\w+`
     - Text blocks: `"""`
     - Switch expressions: `switch.*->`
   - Use `list_code_definition_names` to understand architecture

4. **Testing Framework Detection**
   - Identify test frameworks (JUnit, Spock, Karate, TestNG)
   - Document test structure and coverage

5. **Documentation Generation**
   - Create structured analysis document
   - Generate compatibility matrix
   - Identify modernization opportunities

**Output**: Comprehensive current state analysis document

### 3.2 Phase 2: Migration Plan Creation

**Objective**: Create a detailed, actionable migration plan

**Actions**:
1. **Java 21 Features Assessment**
   - Evaluate applicability of each Java 21 feature:
     - **Virtual Threads (JEP 444)**: For I/O-bound operations
     - **Sequenced Collections (JEP 431)**: For collection operations
     - **Pattern Matching for switch (JEP 441)**: For control flow
     - **Record Patterns (JEP 440)**: For data handling
     - **String Templates (Preview)**: For string operations
     - **Unnamed Patterns (JEP 443)**: For cleaner code

2. **Dependency Compatibility Analysis**
   - Create compatibility matrix for all dependencies
   - Identify required version updates
   - Flag potential breaking changes

3. **Risk Assessment**
   - Identify high-risk areas (reflection, native code, custom processors)
   - Assess probability and impact of issues
   - Develop mitigation strategies

4. **12-Phase Migration Plan**
   - Phase 1: Codebase Analysis and Documentation
   - Phase 2: Pre-Migration Preparation
   - Phase 3: Update Build Configuration
   - Phase 4: Update Dependencies
   - Phase 5: Refactor Code with Java 21 Features
   - Phase 6: Address Breaking Changes and Deprecations
   - Phase 7: Build and Compile
   - Phase 8: Run Application
   - Phase 9: Execute Comprehensive Testing
   - Phase 10: Performance Validation
   - Phase 11: Documentation Updates
   - Phase 12: Version Control and Release

5. **Success Criteria Definition**
   - Functional requirements
   - Technical requirements
   - Performance requirements
   - Code quality requirements

6. **Timeline Estimation**
   - Estimate effort for each phase
   - Calculate total migration time
   - Identify dependencies between phases

**Output**: 
- Use `update_todo_list` to create actionable checklist
- Present comprehensive migration plan in markdown format

### 3.3 Phase 3: Interactive Implementation

**Objective**: Guide user through migration with validation at each step

**Actions**:
1. **User Approval**
   - Use `ask_followup_question`: "Would you like me to proceed with implementing this migration plan?"
   - Options: Yes (full), Partial (specific phases), No (manual)

2. **Mode Switching**
   - If approved, use `switch_mode` to transition to code mode
   - Reason: "Implementing Java 21 migration changes"

3. **Phased Implementation**
   - Execute each phase sequentially
   - Validate completion before proceeding
   - Update todo list after each phase

4. **Branch Management**
   - Always ask before creating branches
   - Suggest: `feature/java-21-migration` or `migrate/java-17-to-21`
   - Never create branches without explicit approval

5. **Validation Steps**
   - After build config changes: `./gradlew clean build -x test`
   - After dependency updates: `./gradlew dependencies`
   - After code changes: `./gradlew build`
   - After all changes: `./gradlew test`

**Output**: Implemented changes with validation at each step

---

## 4. Java 21 Features Implementation Strategy

### 4.1 Virtual Threads (High Priority)

**When to Apply**:
- I/O-bound operations (database, network, file system)
- High-concurrency scenarios
- Spring Boot applications (simple configuration)

**Implementation**:
```yaml
# application.yml
spring:
  threads:
    virtual:
      enabled: true
```

**Code Changes**:
```java
// Before
ExecutorService executor = Executors.newFixedThreadPool(100);

// After
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
```

**Validation**:
- Check startup logs for virtual threads activation
- Monitor thread count and performance
- Validate no blocking operations on virtual threads

### 4.2 Records (High Priority)

**When to Apply**:
- DTOs (Data Transfer Objects)
- Immutable data classes
- API request/response objects
- Configuration classes

**Implementation Strategy**:
```java
// Before (Lombok)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
}

// After (Java 21 Record)
public record UserResponse(
    String id,
    String firstName,
    String lastName,
    Integer age
) {
    // Optional: Custom builder for backward compatibility
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Integer age;
        
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        // ... other builder methods
        
        public UserResponse build() {
            return new UserResponse(id, firstName, lastName, age);
        }
    }
}
```

**Validation**:
- Ensure immutability is maintained
- Verify serialization/deserialization works
- Test with Jackson/JSON processing
- Validate equals/hashCode behavior

### 4.3 Pattern Matching for Switch (High Priority)

**When to Apply**:
- Exception handling
- Type-based routing
- State machines
- Polymorphic behavior

**Implementation**:
```java
// Before
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
    if (ex instanceof UserNotFoundException) {
        UserNotFoundException unf = (UserNotFoundException) ex;
        return handleUserNotFound(unf, request);
    } else if (ex instanceof MethodArgumentNotValidException) {
        MethodArgumentNotValidException manv = (MethodArgumentNotValidException) ex;
        return handleValidation(manv, request);
    } else {
        return handleDefault(ex, request);
    }
}

// After
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
    return switch (ex) {
        case UserNotFoundException unf -> handleUserNotFound(unf, request);
        case MethodArgumentNotValidException manv -> handleValidation(manv, request);
        case null, default -> handleDefault(ex, request);
    };
}
```

**Validation**:
- Test all switch cases
- Verify null handling
- Ensure exhaustiveness
- Check for compilation warnings

### 4.4 Sequenced Collections (Medium Priority)

**When to Apply**:
- List operations requiring first/last access
- Queue-like operations
- Ordered collection manipulation

**Implementation**:
```java
// Before
List<User> users = userRepository.findAll();
if (!users.isEmpty()) {
    User firstUser = users.get(0);
    User lastUser = users.get(users.size() - 1);
    List<User> reversed = new ArrayList<>(users);
    Collections.reverse(reversed);
}

// After
List<User> users = userRepository.findAll();
if (!users.isEmpty()) {
    User firstUser = users.getFirst();
    User lastUser = users.getLast();
    List<User> reversed = users.reversed();
}
```

**Validation**:
- Test boundary conditions (empty lists)
- Verify reversed() creates new list
- Check performance impact

### 4.5 String Templates (Medium Priority - Preview Feature)

**When to Apply**:
- Logging statements
- SQL query building
- Complex string formatting
- Error messages

**Implementation**:
```java
// Before
log.debug("Creating user with firstName: {}, lastName: {}, age: {}",
    request.getFirstName(), request.getLastName(), request.getAge());

// After (if preview enabled)
log.debug(STR."Creating user with firstName: \{request.getFirstName()}, lastName: \{request.getLastName()}, age: \{request.getAge()}");
```

**Note**: String Templates are preview in Java 21. Enable with:
```gradle
tasks.withType(JavaCompile) {
    options.compilerArgs += ["--enable-preview"]
}
```

**Validation**:
- Test string interpolation
- Verify escaping works correctly
- Check performance vs traditional concatenation

### 4.6 Record Patterns (Medium Priority)

**When to Apply**:
- Deconstructing records in pattern matching
- Nested data structure handling
- Complex data extraction

**Implementation**:
```java
// Before
if (obj instanceof Point) {
    Point p = (Point) obj;
    int x = p.x();
    int y = p.y();
    // use x and y
}

// After
if (obj instanceof Point(int x, int y)) {
    // use x and y directly
}
```

**Validation**:
- Test with nested records
- Verify type safety
- Check null handling

---

## 5. Testing Strategy

### 5.1 Unit Testing

**Frameworks Supported**:
- JUnit 5 (Jupiter)
- Spock Framework (Groovy)
- TestNG

**Validation Steps**:
```bash
# Run all unit tests
./gradlew test --tests "*Test"
./gradlew test --tests "*Spec"

# Run with detailed output
./gradlew test --info

# Generate coverage report
./gradlew test jacocoTestReport
```

**Success Criteria**:
- All existing tests pass
- No new test failures
- Code coverage maintained or improved
- No flaky tests introduced

### 5.2 Integration Testing

**Frameworks Supported**:
- Spring Boot Test
- TestContainers
- Karate Framework

**Validation Steps**:
```bash
# Run integration tests
./gradlew integrationTest

# Run with TestContainers
./gradlew test --tests "*IntegrationTest"

# Run Karate tests
./gradlew test --tests "*KarateTest"
```

**Success Criteria**:
- All integration tests pass
- Database operations work correctly
- External service mocks function properly
- TestContainers start and stop cleanly

### 5.3 API Testing

**Frameworks Supported**:
- Karate Framework
- REST Assured
- Spring MockMvc

**Validation Steps**:
```bash
# Run API tests
./gradlew test --tests "*ApiTest"

# Run Karate features
./gradlew test --tests "*KarateTest"
```

**Test Scenarios**:
- All CRUD operations
- Search and filter functionality
- Error handling and validation
- Authentication and authorization
- Response format validation

### 5.4 Performance Testing

**Metrics to Measure**:
- Application startup time
- Memory usage (heap, non-heap)
- Request throughput (requests/second)
- Response time percentiles (p50, p95, p99)
- Virtual threads effectiveness
- GC behavior and frequency

**Tools**:
- JMeter for load testing
- Apache Bench for simple benchmarks
- VisualVM or JConsole for JVM monitoring
- Spring Boot Actuator metrics

**Validation Steps**:
```bash
# Measure startup time
time ./gradlew bootRun

# Load test with Apache Bench
ab -n 1000 -c 10 http://localhost:8080/api/users

# Monitor with JConsole
jconsole
```

**Success Criteria**:
- Startup time ≤ Java 17 baseline
- Memory usage ≤ 110% of baseline
- Throughput ≥ Java 17 baseline
- Response times ≤ Java 17 baseline
- Virtual threads show improved concurrency

---

## 6. Build Configuration Updates

### 6.1 Gradle Configuration

**File**: `build.gradle`

**Changes Required**:
```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.5'  // Updated
    id 'io.spring.dependency-management' version '1.1.6'
}

group = 'com.example'
version = '1.0.0-java21'

java {
    sourceCompatibility = '21'  // Updated from 17
    targetCompatibility = '21'  // Updated from 17
}

// Optional: Enable preview features
tasks.withType(JavaCompile) {
    options.compilerArgs += ['--enable-preview']
}

tasks.withType(Test) {
    jvmArgs += ['--enable-preview']
}

dependencies {
    // Updated versions
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0'
    
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    
    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.spockframework:spock-core:2.4-M4-groovy-4.0'
    testImplementation 'org.spockframework:spock-spring:2.4-M4-groovy-4.0'
    testImplementation 'com.intuit.karate:karate-junit5:1.4.1'
    testImplementation 'org.testcontainers:mongodb:1.20.4'
}

test {
    useJUnitPlatform()
}
```

### 6.2 Maven Configuration

**File**: `pom.xml`

**Changes Required**:
```xml
<properties>
    <java.version>21</java.version>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <spring-boot.version>3.3.5</spring-boot.version>
</properties>

<dependencies>
    <!-- Updated versions -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- ... other dependencies -->
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>21</source>
                <target>21</target>
                <!-- Optional: Enable preview features -->
                <compilerArgs>
                    <arg>--enable-preview</arg>
                </compilerArgs>
            </configuration>
        </plugin>
    </plugins>
</build>
```

---

## 7. Dependency Compatibility Matrix

### 7.1 Spring Boot Applications

| Dependency | Java 17 Version | Java 21 Version | Notes |
|------------|----------------|-----------------|-------|
| Spring Boot | 3.2.x | 3.3.5+ | Full Java 21 support |
| Spring Framework | 6.1.x | 6.1.x+ | Compatible |
| Hibernate | 6.2.x | 6.4.x+ | Recommended update |
| Lombok | 1.18.28+ | 1.18.30+ | Java 21 support |
| Jackson | 2.15.x | 2.17.x+ | Latest stable |
| SLF4J | 2.0.x | 2.0.x+ | Compatible |
| Logback | 1.4.x | 1.5.x+ | Recommended update |

### 7.2 Testing Frameworks

| Framework | Java 17 Version | Java 21 Version | Notes |
|-----------|----------------|-----------------|-------|
| JUnit Jupiter | 5.9.x | 5.10.x+ | Full support |
| Mockito | 5.3.x | 5.14.x+ | Latest stable |
| Spock | 2.3-groovy-4.0 | 2.4-M4-groovy-4.0 | Milestone release |
| Groovy | 4.0.15 | 4.0.21+ | Java 21 compatible |
| Karate | 1.4.1 | 1.4.1+ | Already compatible |
| TestContainers | 1.19.3 | 1.20.4+ | Latest stable |
| REST Assured | 5.3.x | 5.5.x+ | Latest stable |

### 7.3 Build Tools

| Tool | Minimum Version | Recommended | Notes |
|------|----------------|-------------|-------|
| Gradle | 8.5 | 8.13+ | Full Java 21 support |
| Maven | 3.9.5 | 3.9.9+ | Latest stable |
| Gradle Wrapper | 8.5 | 8.13 | Update wrapper |

### 7.4 Database Drivers

| Database | Driver | Java 21 Compatible | Notes |
|----------|--------|-------------------|-------|
| PostgreSQL | postgresql | 42.7.x+ | Compatible |
| MongoDB | mongodb-driver | 5.x+ | Via Spring Boot |
| MySQL | mysql-connector-j | 8.3.x+ | Compatible |
| Oracle | ojdbc11 | 23.x+ | Use ojdbc11 |
| H2 | h2 | 2.2.x+ | Compatible |

---

## 8. Risk Assessment and Mitigation

### 8.1 High-Risk Areas

| Risk | Probability | Impact | Mitigation Strategy |
|------|------------|--------|---------------------|
| Dependency incompatibility | Medium | High | Test all dependencies before migration; maintain compatibility matrix |
| Reflection-based code breaks | Low | High | Identify reflection usage; test thoroughly; update to use method handles |
| Native library incompatibility | Low | Critical | Verify native libraries support Java 21; have fallback plan |
| Custom annotation processors | Low | High | Test annotation processing; update processors if needed |
| Performance regression | Low | Medium | Benchmark before/after; monitor metrics; optimize if needed |
| Test failures | Medium | Medium | Run comprehensive test suite; fix failures incrementally |
| Breaking API changes | Low | High | Maintain API contracts; version APIs if needed |
| Lombok compatibility | Low | Medium | Update to latest Lombok; verify annotation processing |
| Build tool issues | Low | Medium | Update build tools; test build process thoroughly |

### 8.2 Mitigation Strategies

**1. Comprehensive Testing**
- Run full test suite after each phase
- Perform manual testing of critical features
- Use TestContainers for integration testing
- Conduct load testing for performance validation

**2. Incremental Migration**
- Migrate in phases, not all at once
- Validate each phase before proceeding
- Maintain rollback capability at each step

**3. Backup and Version Control**
- Create backup branch before migration
- Commit changes incrementally
- Tag important milestones
- Document all changes

**4. Monitoring and Validation**
- Monitor application logs for warnings
- Track performance metrics
- Validate functionality continuously
- Use feature flags for gradual rollout

**5. Rollback Plan**
- Keep Java 17 branch as backup
- Document rollback procedure
- Test rollback process
- Maintain separate deployment for Java 17 version

---

## 9. Documentation Requirements

### 9.1 Migration Notes Document

**File**: `MIGRATION_NOTES.md`

**Contents**:
- Migration date and team
- Java versions (from/to)
- Key changes summary
- Dependency updates
- Code refactoring highlights
- Breaking changes (if any)
- Performance improvements
- Known issues or limitations
- Rollback instructions

### 9.2 README Updates

**Sections to Update**:
- Prerequisites (Java 21 requirement)
- Technology stack
- Build instructions
- Run instructions
- Testing instructions
- New features utilized

### 9.3 API Documentation

**Updates Required**:
- Swagger/OpenAPI annotations
- Request/response examples
- Error response formats
- Authentication requirements
- Rate limiting (if changed)

### 9.4 Deployment Documentation

**Updates Required**:
- JVM requirements
- Environment variables
- Configuration properties
- Docker base images
- CI/CD pipeline changes

---

## 10. Custom Instructions for the Mode

### 10.1 Automatic Behavior

**On Mode Activation**:
1. Immediately start project analysis
2. Don't wait for user prompts
3. Use tools to gather information
4. Present findings in structured format

**Analysis Tools Sequence**:
```
1. list_files (project structure)
2. read_file (build.gradle/pom.xml)
3. search_files (Java 17 features)
4. list_code_definition_names (architecture)
5. read_file (test files)
```

### 10.2 Communication Style

**Guidelines**:
- Be direct and technical
- Avoid conversational fluff
- Use clear, actionable language
- Provide concrete examples
- Use tables and lists for structure
- Include code snippets with before/after
- Explain the "why" behind recommendations

**Example Good Communication**:
```
Found 3 opportunities for Virtual Threads:
1. UserService.processUsers() - I/O-bound operation
2. ReportGenerator.generateReport() - File I/O
3. EmailService.sendBulkEmails() - Network I/O

Recommendation: Enable Virtual Threads in application.yml
Expected benefit: 40% improvement in concurrent request handling
```

**Example Bad Communication**:
```
Hey! I noticed you might want to use Virtual Threads. 
They're really cool and could help your app. What do you think?
```

### 10.3 Decision Points

**Always Ask Before**:
- Creating branches
- Making breaking changes
- Enabling preview features
- Modifying production configurations
- Deleting code

**Never Ask Before**:
- Reading files
- Analyzing code
- Creating documentation
- Running tests
- Generating reports

### 10.4 Error Handling

**If Analysis Fails**:
1. Explain what went wrong
2. Suggest corrective actions
3. Offer alternative approaches
4. Don't give up - try different tools

**If Build Fails**:
1. Show compilation errors
2. Identify root cause
3. Suggest fixes
4. Offer to implement fixes

**If Tests Fail**:
1. Show test failures
2. Analyze failure reasons
3. Suggest fixes
4. Offer to debug

---

## 11. Implementation Timeline

### 11.1 Mode Development Phases

| Phase | Duration | Tasks |
|-------|----------|-------|
| **Phase 1: Configuration** | 1 hour | Create YAML configuration, define role, set up tool groups |
| **Phase 2: Analysis Logic** | 2 hours | Implement automatic analysis workflow, tool sequencing |
| **Phase 3: Planning Logic** | 2 hours | Implement migration plan generation, risk assessment |
| **Phase 4: Refactoring Patterns** | 3 hours | Define code transformation patterns for each Java 21 feature |
| **Phase 5: Testing Integration** | 2 hours | Implement test execution and validation logic |
| **Phase 6: Documentation** | 2 hours | Create user guides, examples, troubleshooting |
| **Phase 7: Testing** | 2 hours | Test mode with various project types |
| **Phase 8: Refinement** | 2 hours | Fix issues, improve prompts, optimize workflow |
| **Total** | **16 hours** | |

### 11.2 Testing Plan for the Mode

**Test Projects**:
1. Simple Spring Boot REST API
2. Complex microservice with multiple dependencies
3. Batch processing application
4. Web application with frontend
5. Library/framework project

**Test Scenarios**:
- Gradle vs Maven projects
- Different Spring Boot versions
- Various testing frameworks
- With and without Lombok
- Different database types

---

## 12. Success Criteria for the Mode

### 12.1 Functional Requirements

✓ Mode activates successfully  
✓ Automatic analysis completes without errors  
✓ Migration plan is comprehensive and actionable  
✓ Code refactoring suggestions are accurate  
✓ Testing integration works correctly  
✓ Documentation is generated properly  

### 12.2 User Experience Requirements

✓ Clear, actionable guidance  
✓ Minimal user input required  
✓ Progress tracking with todo lists  
✓ Validation at each step  
✓ Helpful error messages  
✓ Easy rollback capability  

### 12.3 Technical Requirements

✓ Handles Gradle and Maven projects  
✓ Supports major frameworks (Spring Boot, Quarkus)  
✓ Works with various testing frameworks  
✓ Identifies all Java 21 features correctly  
✓ Generates valid code transformations  
✓ Maintains code quality and style  

---

## 13. Future Enhancements

### 13.1 Phase 2 Features (Post-Launch)

1. **AI-Powered Code Analysis**
   - Machine learning for pattern detection
   - Intelligent refactoring suggestions
   - Performance prediction models

2. **Multi-Version Support**
   - Java 11 → Java 21 migration
   - Java 8 → Java 21 migration
   - Incremental version upgrades

3. **Framework-Specific Optimizations**
   - Spring Boot specific patterns
   - Quarkus optimizations
   - Jakarta EE modernization

4. **Advanced Testing**
   - Automated performance benchmarking
   - Security vulnerability scanning
   - Code quality metrics

5. **CI/CD Integration**
   - Automated pipeline updates
   - Docker image generation
   - Kubernetes manifest updates

### 13.2 Integration Opportunities

- **SonarQube**: Code quality analysis
- **JaCoCo**: Coverage reporting
- **JMeter**: Performance testing
- **GitHub Actions**: CI/CD automation
- **Dependabot**: Dependency updates

---

## 14. Appendix

### 14.1 Complete Mode YAML Configuration

```yaml
customModes:
  - slug: java_modernization_17_to_21
    name: ☕ Java 17→21 Migration
    description: Automated Java 17 to Java 21 migration with code modernization and testing
    roleDefinition: >-
      You are Bob, a Java modernization specialist with deep expertise in migrating Java 17 applications to Java 21. 
      
      AUTOMATIC ANALYSIS (Execute immediately upon mode activation):
      1. Use list_files to understand project structure
      2. Use read_file to examine build.gradle or pom.xml for Java version and dependencies
      3. Use search_files to find Java 17 features in use (records, sealed classes, pattern matching)
      4. Use list_code_definition_names to understand codebase architecture
      5. Document findings in structured format
      
      MIGRATION PLAN CREATION:
      - Create comprehensive migration plan with 12 phases
      - Generate dependency compatibility matrix
      - Assess Java 21 features applicability
      - Provide risk assessment and mitigation strategies
      - Define success criteria and timeline
      - Use update_todo_list to create actionable steps
      
      INTERACTIVE IMPLEMENTATION:
      - Present migration plan for review
      - Ask: "Would you like me to proceed with implementing this migration plan?"
      - If approved, use switch_mode to transition to code mode
      - Track progress with todo list updates
      - Validate each phase before proceeding
      
      JAVA 21 FEATURES EXPERTISE:
      - Virtual Threads (JEP 444) for improved concurrency
      - Sequenced Collections (JEP 431) for cleaner collection operations
      - Pattern Matching for switch (JEP 441) for cleaner control flow
      - Record Patterns (JEP 440) for better data handling
      - String Templates (Preview) for improved string handling
      - Unnamed Patterns and Variables (JEP 443) for cleaner code
      
      CODE REFACTORING PRIORITIES:
      1. Enable Virtual Threads for I/O-bound operations
      2. Convert DTOs to Records where appropriate
      3. Apply Pattern Matching to switch statements
      4. Use Sequenced Collections methods (getFirst, getLast, reversed)
      5. Implement String Templates for logging (if not preview)
      
      VALIDATION AND TESTING:
      - Run tests after each significant change
      - Verify application starts successfully
      - Check for compiler warnings
      - Validate performance metrics
      - Ensure backward compatibility
      
      BRANCH MANAGEMENT:
      - ALWAYS ask before creating branches
      - Suggest: feature/java-21-migration or migrate/java-17-to-21
      - Never create branches without explicit approval
      
      COMMUNICATION STYLE:
      - Be direct and technical
      - Use clear, actionable language
      - Provide concrete examples with code snippets
      - Use tables and lists for structured information
      - Always explain the "why" behind recommendations
      
      RISK MANAGEMENT:
      - Assess and communicate risks
      - Provide rollback strategies
      - Validate each phase before proceeding
      - Monitor for breaking changes
      - Test thoroughly at each step
    whenToUse: >-
      Use this mode when you need to migrate a Java 17 application to Java 21. This mode is ideal for:
      - Planning and executing Java version upgrades
      - Modernizing Java codebases with new language features
      - Analyzing dependency compatibility for Java 21
      - Refactoring code to leverage Virtual Threads, Records, Pattern Matching, and other Java 21 features
      - Creating comprehensive migration documentation
      - Ensuring safe and systematic version upgrades
      
      The mode automatically analyzes your project structure, creates a detailed migration plan, 
      and can guide you through implementation step-by-step with validation at each phase.
    groups:
      - read
      - edit
      - command
      - browser
      - mcp
    customInstructions: >-
      WORKFLOW:
      
      1. AUTOMATIC ANALYSIS (Immediate):
         - list_files for project structure
         - read_file for build configuration
         - search_files for Java 17 features
         - list_code_definition_names for architecture
         - Document findings
      
      2. MIGRATION PLAN:
         - Assess Java 21 features applicability
         - Create dependency compatibility matrix
         - Generate 12-phase migration plan
         - Assess risks and mitigation
         - Define success criteria
         - Create todo list with update_todo_list
      
      3. IMPLEMENTATION:
         - Ask for approval to proceed
         - Use switch_mode to code mode if approved
         - Execute phases sequentially
         - Validate after each phase
         - Update todo list progress
      
      4. VALIDATION:
         - Run tests: ./gradlew test
         - Build: ./gradlew build
         - Start app: ./gradlew bootRun
         - Check logs and metrics
      
      5. DOCUMENTATION:
         - Update README.md
         - Create MIGRATION_NOTES.md
         - Update API documentation
         - Document new features
      
      REMEMBER:
      - Start analysis automatically
      - Create comprehensive plans
      - Ask before branches/major changes
      - Switch to code mode for implementation
      - Track progress continuously
      - Focus on delivering value through Java 21 features
    source: global
```

### 14.2 Example Project Analysis Output

```markdown
# Java 17 to Java 21 Migration Analysis

## Project Overview
- **Name**: user-management-service
- **Type**: REST API Microservice
- **Build Tool**: Gradle 8.13
- **Current Java Version**: 17

## Technology Stack
- Spring Boot: 3.2.0
- MongoDB: 5.0
- Lombok: 1.18.28
- Spock: 2.3-groovy-4.0
- Karate: 1.4.1

## Java 17 Features in Use
- ✓ Records: 2 instances found
- ✗ Sealed classes: Not used
- ✓ Pattern matching: 3 instances found
- ✓ Text blocks: 5 instances found
- ✓ Switch expressions: 1 instance found

## Modernization Opportunities
1. **Virtual Threads**: 4 I/O-bound operations identified
2. **Records**: 8 DTOs can be converted
3. **Pattern Matching**: 6 switch statements can be enhanced
4. **Sequenced Collections**: 12 list operations can be simplified

## Dependency Updates Required
- Spring Boot: 3.2.0 → 3.3.5
- Spock: 2.3-groovy-4.0 → 2.4-M4-groovy-4.0
- Groovy: 4.0.15 → 4.0.21

## Risk Assessment
- **Overall Risk**: Low
- **High-Risk Areas**: None identified
- **Estimated Effort**: 4-6 hours

## Next Steps
Would you like me to proceed with creating a detailed migration plan?
```

---

## Conclusion

This comprehensive plan provides a complete blueprint for creating the `java_modernization_17_to_21` custom mode. The mode will:

1. **Automatically analyze** Java 17 projects upon activation
2. **Create detailed migration plans** with risk assessment and timeline
3. **Guide implementation** with step-by-step validation
4. **Refactor code** to leverage Java 21 features effectively
5. **Validate changes** through comprehensive testing
6. **Document everything** for team knowledge sharing

**Key Success Factors**:
- Automatic analysis reduces user effort
- Comprehensive planning ensures safe migration
- Interactive implementation provides guidance
- Validation at each step ensures quality
- Clear documentation enables team adoption

**Next Steps**:
1. Review and approve this plan
2. Create the custom mode YAML configuration
3. Test with sample projects
4. Refine based on feedback
5. Deploy globally for team use

---

**Document Version**: 1.0  
**Created**: 2025-12-17  
**Author**: Bob - Java Modernization Specialist  
**Status**: Ready for Implementation