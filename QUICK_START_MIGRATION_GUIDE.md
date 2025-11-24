# Quick Start: Java 17 to Java 21 Migration

This is a condensed version of the migration prompt for quick reference. For the complete template, see [`JAVA17_TO_JAVA21_MIGRATION_PROMPT.md`](JAVA17_TO_JAVA21_MIGRATION_PROMPT.md).

## Quick Prompt Template

```
Migrate my Java 17 [APPLICATION_TYPE] to Java 21.

**Application**: [NAME]
**Framework**: [e.g., Spring Boot 3.2.0]
**Build Tool**: [Gradle/Maven + version]
**Database**: [PostgreSQL/MongoDB/etc.]
**Testing**: [JUnit/Spock/etc.]

**Key Dependencies**:
- [Dependency 1]: [version]
- [Dependency 2]: [version]

**Java 17 Features Currently Used**:
- [ ] Records
- [ ] Sealed classes
- [ ] Pattern matching
- [ ] Text blocks
- [ ] Switch expressions

**Requirements**:
1. Create comprehensive migration plan with phases
2. Update all dependencies to Java 21 compatible versions
3. Refactor code to use Java 21 features (Virtual Threads, Sequenced Collections, Pattern Matching)
4. Provide before/after code examples
5. Include risk assessment and rollback plan
6. Define success criteria and timeline
7. Maintain backward compatibility and test coverage

**Focus Areas**:
- [Specify any particular areas of concern or focus]
```

## Migration Checklist

### Pre-Migration
- [ ] Backup current codebase
- [ ] Document current performance baseline
- [ ] Verify Java 21 JDK installed
- [ ] Run all tests and document results

### Build Configuration
- [ ] Update Java version to 21
- [ ] Update framework version
- [ ] Update all dependencies
- [ ] Update build plugins

### Code Refactoring
- [ ] Enable Virtual Threads
- [ ] Convert appropriate classes to Records
- [ ] Apply Pattern Matching for switch
- [ ] Use Sequenced Collections
- [ ] Implement String Templates (if using preview)

### Testing & Validation
- [ ] All unit tests pass
- [ ] All integration tests pass
- [ ] Performance benchmarks meet criteria
- [ ] Manual testing complete

### Documentation
- [ ] Update README
- [ ] Create migration notes
- [ ] Update API documentation
- [ ] Update deployment guides

### Deployment
- [ ] Update CI/CD pipeline
- [ ] Deploy to development environment
- [ ] Monitor and validate
- [ ] Plan production rollout

## Common Migration Patterns

### 1. Enable Virtual Threads (Spring Boot)
```yaml
spring:
  threads:
    virtual:
      enabled: true
```

### 2. Convert to Record
```java
// Before
@Data
@Builder
public class UserDTO {
    private String id;
    private String name;
}

// After
public record UserDTO(String id, String name) {
    public static Builder builder() {
        return new Builder();
    }
    // Optional builder for compatibility
}
```

### 3. Pattern Matching for Switch
```java
// Before
if (obj instanceof String) {
    String s = (String) obj;
    // use s
} else if (obj instanceof Integer) {
    Integer i = (Integer) obj;
    // use i
}

// After
switch (obj) {
    case String s -> handleString(s);
    case Integer i -> handleInteger(i);
    default -> handleDefault(obj);
}
```

### 4. Sequenced Collections
```java
// Before
List<Item> items = getItems();
Item first = items.get(0);
Item last = items.get(items.size() - 1);

// After
List<Item> items = getItems();
Item first = items.getFirst();
Item last = items.getLast();
```

## Key Dependencies to Update

### Spring Boot Applications
- Spring Boot: 3.2.x → 3.3.x or later
- Lombok: Ensure 1.18.30+
- Hibernate: Check compatibility with Spring Boot version

### Testing Frameworks
- JUnit: 5.10.0+
- Mockito: 5.5.0+
- Spock: 2.4-M4-groovy-4.0+
- TestContainers: 1.19.8+

### Build Tools
- Gradle: 8.5+ (8.13 recommended)
- Maven: 3.9.5+

## Timeline Estimates

| Application Size | Estimated Time |
|-----------------|----------------|
| Small (< 10k LOC) | 4-6 hours |
| Medium (10k-50k LOC) | 8-16 hours |
| Large (50k-100k LOC) | 2-4 days |
| Enterprise (> 100k LOC) | 1-2 weeks |

## Risk Mitigation

**High Risk Areas**:
- Reflection-heavy code
- Custom annotation processors
- Native libraries
- Complex concurrency patterns

**Mitigation**:
- Test thoroughly in isolated environment
- Maintain rollback branch
- Gradual rollout strategy
- Monitor performance metrics

## Success Criteria

✓ All tests pass (100% or defined threshold)  
✓ No compilation errors or critical warnings  
✓ Performance ≥ Java 17 baseline  
✓ Application starts successfully  
✓ All features work identically  

## Resources

- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
- [Spring Boot 3.3 Release Notes](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.3-Release-Notes)
- [Java 21 Migration Guide](https://docs.oracle.com/en/java/javase/21/migrate/)

---

For detailed instructions and comprehensive planning, use the full template in [`JAVA17_TO_JAVA21_MIGRATION_PROMPT.md`](JAVA17_TO_JAVA21_MIGRATION_PROMPT.md).