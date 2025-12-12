# How to Use the Java 17→21 Migration Custom Mode

## Overview

The `java_modernization_17_to_21` custom mode is now installed globally in your Bob IDE. This mode provides automated analysis and guided migration of Java 17 applications to Java 21.

## Installation Status

✅ **Installed**: The custom mode has been successfully added to:
```
/Users/abdulrasid/Library/Application Support/Bob-IDE/User/globalStorage/ibm.bob-code/settings/custom_modes.yaml
```

## Activation

### Option 1: Reload Bob IDE
1. Press `Cmd+Shift+P` (macOS) or `Ctrl+Shift+P` (Windows/Linux)
2. Type "Reload Window" and select it
3. The new mode will be available after reload

### Option 2: Restart Bob IDE
Simply close and reopen Bob IDE to load the new custom mode.

## How to Use

### Step 1: Open Your Java Project
Navigate to any Java 17 project you want to migrate to Java 21.

### Step 2: Activate the Migration Mode
1. Click on the mode selector in Bob IDE (usually shows current mode like "💻 Code" or "📝 Plan")
2. Look for **"☕ Java 17→21 Migration"** in the mode list
3. Click to activate it

### Step 3: Automatic Analysis Begins
Once activated, the mode will **automatically**:
- Analyze your project structure
- Detect Java version from build files
- Identify all dependencies
- Scan for Java 17 features in use
- Document current technology stack

**You don't need to ask anything** - the analysis starts immediately!

### Step 4: Review the Migration Plan
The mode will present:
- **Current State Analysis**: Your project's technology stack and architecture
- **Java 21 Features Assessment**: Which new features are applicable
- **Dependency Compatibility Matrix**: What needs updating
- **12-Phase Migration Plan**: Detailed step-by-step guide
- **Risk Assessment**: Potential issues and mitigation strategies
- **Success Criteria**: How to validate the migration

### Step 5: Decide on Implementation
After presenting the plan, the mode will ask:
> "Would you like me to proceed with implementing this migration plan?"

**Your options:**
- **Yes**: The mode switches to code mode and begins implementation
- **No**: You can ask questions, request plan refinements, or implement manually
- **Partial**: You can ask to implement specific phases only

### Step 6: Interactive Implementation
If you proceed with implementation:
- The mode tracks progress with a todo list
- Each phase is validated before moving to the next
- You'll be asked before creating branches or making major changes
- Code changes are shown with before/after examples
- Tests are run to validate each step

## Example Usage Scenarios

### Scenario 1: Quick Assessment
```
User: [Activates Java 17→21 Migration mode]
Mode: [Automatically analyzes project]
Mode: [Presents migration plan]
User: "Just show me what needs to change, I'll implement it myself"
Mode: [Provides detailed change list and exits]
```

### Scenario 2: Guided Migration
```
User: [Activates Java 17→21 Migration mode]
Mode: [Automatically analyzes project]
Mode: [Presents migration plan]
User: "Yes, let's proceed with the migration"
Mode: [Switches to code mode and implements changes step-by-step]
Mode: [Validates each phase]
Mode: [Updates documentation]
```

### Scenario 3: Selective Implementation
```
User: [Activates Java 17→21 Migration mode]
Mode: [Automatically analyzes project]
Mode: [Presents migration plan]
User: "Only implement Phase 3 and Phase 4 (build config and dependencies)"
Mode: [Implements only requested phases]
Mode: [Provides guidance for remaining phases]
```

## What the Mode Does Automatically

### Analysis Phase (Automatic)
- ✅ Scans project structure
- ✅ Reads build configuration (Gradle/Maven)
- ✅ Identifies dependencies and versions
- ✅ Finds Java 17 features in code
- ✅ Assesses testing frameworks
- ✅ Documents current state

### Planning Phase (Automatic)
- ✅ Creates compatibility matrix
- ✅ Identifies applicable Java 21 features
- ✅ Generates 12-phase migration plan
- ✅ Assesses risks and mitigation strategies
- ✅ Defines success criteria
- ✅ Estimates timeline

### Implementation Phase (With Your Approval)
- ⚠️ Updates build configuration
- ⚠️ Updates dependencies
- ⚠️ Refactors code with Java 21 features
- ⚠️ Runs tests and validates
- ⚠️ Updates documentation
- ⚠️ Creates migration notes

**Legend**: ✅ = Automatic, ⚠️ = Requires approval

## Key Features

### 1. Virtual Threads Integration
The mode identifies I/O-bound operations and suggests Virtual Threads implementation:
```java
// Before (Java 17)
ExecutorService executor = Executors.newFixedThreadPool(100);

// After (Java 21)
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
```

### 2. Records Conversion
Identifies DTOs and POJOs that can be converted to records:
```java
// Before
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}

// After
public record UserResponse(Long id, String name, String email) {}
```

### 3. Pattern Matching Enhancement
Improves switch statements and instanceof checks:
```java
// Before
if (obj instanceof String) {
    String s = (String) obj;
    return s.length();
}

// After
if (obj instanceof String s) {
    return s.length();
}
```

### 4. Sequenced Collections
Utilizes new collection methods:
```java
// Before
List<String> items = getItems();
String first = items.get(0);
String last = items.get(items.size() - 1);

// After
List<String> items = getItems();
String first = items.getFirst();
String last = items.getLast();
```

## Branch Management

The mode will **always ask** before creating branches:
- Suggested branch names: `feature/java-21-migration` or `migrate/java-17-to-21`
- You can specify your own branch name
- You can choose to work on the current branch

## Safety Features

### Validation at Each Step
- Compiles code after changes
- Runs tests to ensure functionality
- Checks for warnings and errors
- Validates performance metrics

### Rollback Strategy
- Creates backup documentation
- Tracks all changes
- Provides rollback instructions
- Maintains git history

### Risk Assessment
- Identifies potential breaking changes
- Assesses dependency compatibility
- Evaluates performance impact
- Provides mitigation strategies

## Migration Phases

The mode follows a 12-phase approach:

1. **Codebase Analysis and Documentation**
2. **Pre-Migration Preparation**
3. **Update Build Configuration**
4. **Update Dependencies**
5. **Refactor Code with Java 21 Features**
6. **Address Breaking Changes and Deprecations**
7. **Build and Compile**
8. **Run Application**
9. **Execute Comprehensive Testing**
10. **Performance Validation**
11. **Documentation Updates**
12. **Version Control and Release**

Each phase is validated before proceeding to the next.

## Tips for Best Results

### 1. Start with a Clean State
- Commit all pending changes
- Ensure tests pass on Java 17
- Document current performance metrics

### 2. Review the Plan Carefully
- Understand the proposed changes
- Assess the risk level for your project
- Identify any custom considerations

### 3. Test Thoroughly
- Run all test suites after each phase
- Perform manual testing of critical features
- Validate performance metrics

### 4. Document Everything
- The mode creates migration notes automatically
- Add your own observations and decisions
- Update team documentation

## Troubleshooting

### Mode Not Appearing
1. Verify the YAML file exists at the correct location
2. Check for YAML syntax errors
3. Reload or restart Bob IDE
4. Check Bob IDE logs for errors

### Analysis Fails
- Ensure you're in a Java project directory
- Verify build files (build.gradle or pom.xml) exist
- Check file permissions

### Implementation Issues
- Review the migration plan carefully
- Implement phases one at a time
- Run tests after each phase
- Consult the mode's suggestions

## Support and Resources

### Documentation Files
- `JAVA17_TO_JAVA21_MIGRATION_PROMPT.md` - Complete migration template
- `JAVA_MODERNIZATION_CUSTOM_MODE.md` - Mode configuration details
- `HOW_TO_USE_JAVA_MODERNIZATION_MODE.md` - This file

### Java 21 Resources
- [Java 21 Release Notes](https://openjdk.org/projects/jdk/21/)
- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)
- [JEP 431: Sequenced Collections](https://openjdk.org/jeps/431)
- [JEP 441: Pattern Matching for switch](https://openjdk.org/jeps/441)
- [JEP 440: Record Patterns](https://openjdk.org/jeps/440)

## Example: Complete Migration Flow

```
1. Open your Java 17 project in Bob IDE

2. Activate "☕ Java 17→21 Migration" mode

3. Mode automatically analyzes:
   ✓ Detected Java 17 in build.gradle
   ✓ Found 15 dependencies
   ✓ Identified 3 records in use
   ✓ Found 5 pattern matching instances
   ✓ Detected Spring Boot 3.2.0

4. Mode presents migration plan:
   📋 12-phase migration plan created
   ⚠️ 3 dependencies need updates
   ✨ 8 opportunities for Java 21 features
   ⏱️ Estimated time: 4-6 hours

5. You decide: "Yes, let's proceed"

6. Mode implements changes:
   [Phase 1] ✓ Analysis complete
   [Phase 2] ✓ Backup created
   [Phase 3] ✓ Build config updated to Java 21
   [Phase 4] ✓ Dependencies updated
   [Phase 5] ✓ Virtual Threads enabled
   [Phase 6] ✓ No breaking changes found
   [Phase 7] ✓ Build successful
   [Phase 8] ✓ Application starts
   [Phase 9] ✓ All tests pass (127/127)
   [Phase 10] ✓ Performance improved by 15%
   [Phase 11] ✓ Documentation updated
   [Phase 12] ✓ Changes committed

7. Migration complete! 🎉
```

## Next Steps After Migration

1. **Monitor in Development**
   - Watch for any warnings or errors
   - Validate all functionality
   - Check performance metrics

2. **Team Review**
   - Share migration notes with team
   - Conduct code review
   - Update team documentation

3. **Plan Production Deployment**
   - Test in staging environment
   - Prepare rollback plan
   - Schedule deployment window

4. **Explore Additional Features**
   - Structured Concurrency (Preview)
   - Scoped Values (Preview)
   - Other Java 21 enhancements

## Feedback and Improvements

This custom mode is designed to evolve. If you encounter issues or have suggestions:
1. Document your experience
2. Note any edge cases
3. Suggest improvements to the mode configuration
4. Share successful migration patterns

---

**Happy Migrating! ☕→☕**

*Made with Bob - Your AI Software Engineering Assistant*