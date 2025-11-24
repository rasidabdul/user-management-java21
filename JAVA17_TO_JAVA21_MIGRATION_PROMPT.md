# Java 17 to Java 21 Migration Prompt Template

## How to Use This Prompt

This is a reusable prompt template for migrating any Java 17 application to Java 21. Follow these steps:

1. **Fill in the placeholders** marked with `[PLACEHOLDER: description]` with your application-specific details
2. **Review and customize** the sections based on your project's needs
3. **Remove or add** phases based on your application's complexity
4. **Use this prompt** with an AI assistant or as a guide for your migration team

---

## Migration Request Prompt

I need to migrate a Java 17 application to Java 21. Please create a comprehensive migration plan following this structure:

### Application Context

**Application Name**: [PLACEHOLDER: Your application name, e.g., "Order Processing Service"]

**Application Type**: [PLACEHOLDER: e.g., "REST API microservice", "Batch processing application", "Web application", "Monolithic application"]

**Current Technology Stack**:
- **Java Version**: 17
- **Framework**: [PLACEHOLDER: e.g., "Spring Boot 3.2.0", "Quarkus 3.x", "Jakarta EE 10", "Plain Java"]
- **Build Tool**: [PLACEHOLDER: e.g., "Gradle 8.x", "Maven 3.9.x"]
- **Database**: [PLACEHOLDER: e.g., "PostgreSQL", "MongoDB", "MySQL", "Oracle", "None"]
- **Testing Frameworks**: [PLACEHOLDER: e.g., "JUnit 5", "Spock", "TestNG", "Karate", "RestAssured"]
- **Key Dependencies**: [PLACEHOLDER: List major dependencies with versions, e.g., "Hibernate 6.x", "Jackson 2.15", "Lombok"]

**Project Structure**:
```
[PLACEHOLDER: Provide your project structure, e.g.,
my-application/
├── src/main/java/com/company/app/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── config/
├── src/main/resources/
└── src/test/
]
```

**Current Java 17 Features in Use**:
- [PLACEHOLDER: Check which features you're using]
  - [ ] Records
  - [ ] Sealed classes
  - [ ] Pattern matching for instanceof
  - [ ] Text blocks
  - [ ] Switch expressions
  - [ ] Stream API enhancements
  - [ ] Other: [specify]

**Code Patterns Identified**:
- [PLACEHOLDER: Describe common patterns in your codebase, e.g.,]
  - Lombok usage: @Data, @Builder, @Slf4j
  - Exception handling approach
  - Validation strategy
  - Async/concurrent processing patterns
  - Logging framework and patterns

---

## Required Migration Plan Structure

Please create a detailed migration plan with the following sections:

### 1. Current State Analysis
- Analyze the provided technology stack
- Document all dependencies and their versions
- Identify code patterns and architecture
- Assess which Java 21 features would be most beneficial
- Create a compatibility matrix for all dependencies

### 2. Java 21 Features Assessment

Evaluate and prioritize these Java 21 features for implementation:

**High Priority** (Clear benefits, minimal risk):
- Virtual Threads (JEP 444) - for improved concurrency
- Sequenced Collections (JEP 431) - for cleaner collection operations
- Pattern Matching for switch (JEP 441) - for cleaner control flow
- Record Patterns (JEP 440) - for better data handling

**Medium Priority** (Selective implementation):
- String Templates (Preview) - for improved string handling
- Unnamed Patterns and Variables (JEP 443) - for cleaner code

**Low Priority** (Future consideration):
- Structured Concurrency (Preview) - for complex async operations
- Scoped Values (Preview) - for thread-local alternatives

For each feature, specify:
- Where it can be applied in the codebase
- Expected benefits
- Implementation complexity
- Any risks or considerations

### 3. Dependency Compatibility Analysis

Create a table analyzing all dependencies:

| Dependency | Current Version | Java 21 Compatible Version | Update Required | Notes |
|------------|----------------|---------------------------|-----------------|-------|
| [Framework] | [version] | [version] | Yes/No | [compatibility notes] |
| ... | ... | ... | ... | ... |

### 4. Detailed Migration Plan

Break down the migration into these phases:

#### Phase 1: Codebase Analysis and Documentation
- Analyze current Java version and features
- Document all dependencies and versions
- Identify code patterns and architecture
- Assess Java 21 feature applicability
- Create compatibility matrix

#### Phase 2: Pre-Migration Preparation
- Create backup branch
- Document current test coverage metrics
- Verify Java 21 JDK installation
- Verify database/external service availability
- Run baseline tests to establish success criteria
- Document current performance metrics

#### Phase 3: Update Build Configuration
- Update build tool configuration for Java 21
- Update compiler settings
- Add Java 21 specific compiler flags if needed
- Verify build tool compatibility

#### Phase 4: Update Dependencies
- Update framework to Java 21 compatible version
- Update all libraries to compatible versions
- Update testing frameworks
- Update build plugins
- Verify transitive dependencies

#### Phase 5: Refactor Code with Java 21 Features
For each applicable feature:
- Identify files to modify
- Provide before/after code examples
- Estimate effort and complexity
- List benefits of the change

Specific refactoring areas:
- Enable Virtual Threads (if applicable)
- Convert DTOs/POJOs to Records (where appropriate)
- Implement Sequenced Collections
- Apply Pattern Matching enhancements
- Use String Templates for logging/formatting
- Simplify switch statements

#### Phase 6: Address Breaking Changes and Deprecations
- Review compiler warnings
- Check for deprecated API usage
- Update reflection-based code
- Verify annotation processing compatibility
- Test framework-specific features

#### Phase 7: Build and Compile
- Clean build
- Compile with Java 21
- Validate no compilation errors
- Review and address warnings
- Verify artifact generation

#### Phase 8: Run Application
- Start required services (database, etc.)
- Run application with Java 21 runtime
- Verify startup logs
- Check feature activation (e.g., virtual threads)
- Test health/readiness endpoints
- Verify UI/API accessibility

#### Phase 9: Execute Comprehensive Testing
- Unit tests
- Integration tests
- API/Contract tests
- End-to-end tests
- Manual testing scenarios
- Verify test coverage maintained

#### Phase 10: Performance Validation
Measure and compare:
- Application startup time
- Memory usage
- Request throughput
- Response time percentiles (p50, p95, p99)
- Resource utilization
- Virtual threads effectiveness (if enabled)

#### Phase 11: Documentation Updates
- Update README with Java 21 requirements
- Update technology stack documentation
- Document new features utilized
- Create migration notes
- Update API documentation
- Update deployment guides

#### Phase 12: Version Control and Release
- Create migration branch/repository
- Commit changes with clear messages
- Tag release version
- Prepare release notes
- Update CI/CD pipeline

### 5. Specific Code Refactoring Examples

Provide concrete before/after examples for:
- Converting classes to Records
- Pattern matching in exception handlers
- Sequenced Collections usage
- String Templates for logging
- Virtual Threads configuration
- Any other relevant refactorings

### 6. Risk Assessment and Mitigation

Create a risk matrix:

| Risk | Probability | Impact | Mitigation Strategy |
|------|------------|--------|---------------------|
| Dependency incompatibility | Low/Medium/High | Low/Medium/High | [mitigation] |
| Test failures | Low/Medium/High | Low/Medium/High | [mitigation] |
| Performance regression | Low/Medium/High | Low/Medium/High | [mitigation] |
| Breaking API changes | Low/Medium/High | Low/Medium/High | [mitigation] |
| ... | ... | ... | ... |

Include a rollback plan.

### 7. Success Criteria

Define clear success criteria:

**Functional Requirements**:
- All existing features work identically
- All API endpoints respond correctly
- Database operations function properly
- Error handling maintains same behavior

**Technical Requirements**:
- Application compiles without errors
- All tests pass (specify acceptable threshold)
- No critical warnings
- Application starts successfully

**Performance Requirements**:
- Startup time: ≤ baseline
- Memory usage: ≤ 110% of baseline
- Throughput: ≥ baseline
- Response times: ≤ baseline

**Code Quality Requirements**:
- Code coverage maintained or improved
- No new critical issues
- Documentation updated

### 8. Timeline Estimate

Provide a realistic timeline for each phase based on:
- Application complexity
- Team size and experience
- Testing requirements
- Risk tolerance

### 9. Post-Migration Tasks

**Immediate**:
- Monitor application in development
- Review logs for warnings/errors
- Validate all functionality
- Update CI/CD pipeline

**Short-term (1-2 weeks)**:
- Gather performance metrics
- Collect team feedback
- Identify optimization opportunities
- Plan production deployment

**Long-term**:
- Explore additional Java 21 features
- Evaluate production performance
- Plan future upgrades

### 10. References and Resources

Include relevant links to:
- Java 21 JEPs and documentation
- Framework migration guides
- Dependency upgrade guides
- Best practices and tutorials

---

## Additional Context (Optional)

Provide any additional information that might be relevant:

**Known Issues or Concerns**:
[PLACEHOLDER: List any known issues, technical debt, or concerns]

**Performance Requirements**:
[PLACEHOLDER: Specify any critical performance requirements or SLAs]

**Deployment Environment**:
[PLACEHOLDER: Describe where the application runs - cloud, on-premise, containers, etc.]

**Team Context**:
[PLACEHOLDER: Team size, Java experience level, timeline constraints]

**Business Constraints**:
[PLACEHOLDER: Any business requirements, deadlines, or constraints]

---

## Example Usage

Here's an example of how to fill in this prompt:

```
I need to migrate a Java 17 application to Java 21. Please create a comprehensive migration plan following this structure:

### Application Context

**Application Name**: Order Processing Service

**Application Type**: REST API microservice

**Current Technology Stack**:
- **Java Version**: 17
- **Framework**: Spring Boot 3.2.0
- **Build Tool**: Maven 3.9.5
- **Database**: PostgreSQL 15
- **Testing Frameworks**: JUnit 5, Mockito, RestAssured
- **Key Dependencies**: 
  - Hibernate 6.2.0
  - Jackson 2.15.0
  - Lombok 1.18.28
  - Flyway 9.22.0

**Project Structure**:
```
order-service/
├── src/main/java/com/company/orders/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── config/
├── src/main/resources/
└── src/test/
```

**Current Java 17 Features in Use**:
- [x] Records (for DTOs)
- [ ] Sealed classes
- [x] Pattern matching for instanceof
- [x] Text blocks
- [x] Switch expressions
- [x] Stream API enhancements

**Code Patterns Identified**:
- Heavy Lombok usage: @Data, @Builder, @Slf4j
- Global exception handler with @RestControllerAdvice
- Jakarta Bean Validation
- CompletableFuture for async operations
- SLF4J with Logback

[Continue with the rest of the prompt structure...]
```

---

## Notes

- This template is designed to be comprehensive. Adjust the depth and detail based on your application's complexity.
- Some phases may be combined or reordered based on your specific needs.
- The timeline estimates should be adjusted based on your team's experience and the application's size.
- Consider running a pilot migration on a smaller service first if you have a microservices architecture.
- Always maintain a rollback plan and test thoroughly before production deployment.