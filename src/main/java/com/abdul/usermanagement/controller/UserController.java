package com.abdul.usermanagement.controller;

import com.abdul.usermanagement.dto.UserRequest;
import com.abdul.usermanagement.dto.UserResponse;
import com.abdul.usermanagement.exception.ErrorResponse;
import com.abdul.usermanagement.service.UserService;
// import io.micrometer.core.annotation.Timed; // Uncomment when micrometer dependency is added
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for User Management operations.
 * Enhanced with Java 21 features including:
 * - String Templates for improved logging (when enabled)
 * - Record patterns for DTOs
 * - Virtual threads support (configured at application level)
 * - Enhanced pattern matching
 * - Pagination and caching support
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users with pagination support.
     * Uses caching for improved performance on repeated requests.
     * 
     * @param page Page number (0-based)
     * @param size Number of items per page
     * @param sortBy Field to sort by (default: createdAt)
     * @param sortDir Sort direction (asc/desc, default: desc)
     * @return Paginated list of users
     */
    @Operation(summary = "Get all users with pagination", 
               description = "Retrieve a paginated list of all users with optional sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping
    @Cacheable(value = "users", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir")
    // @Timed(value = "users.get.all", description = "Time taken to retrieve all users") // Uncomment when micrometer dependency is added
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        String correlationId = generateCorrelationId();
        MDC.put("correlationId", correlationId);
        
        try {
            // Java 21: String Templates (when preview feature is enabled)
            // log.debug(STR."GET /users - Get all users (page: \{page}, size: \{size}, sortBy: \{sortBy}, sortDir: \{sortDir})");
            log.debug("GET /users - Get all users (page: {}, size: {}, sortBy: {}, sortDir: {}, correlationId: {})", 
                     page, size, sortBy, sortDir, correlationId);
            
            Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            
            List<UserResponse> allUsers = userService.getAllUsers();
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), allUsers.size());
            List<UserResponse> pageContent = allUsers.subList(start, end);
            Page<UserResponse> users = new org.springframework.data.domain.PageImpl<>(pageContent, pageable, allUsers.size());
            
            log.info("Successfully retrieved {} users (page {}/{})", 
                    users.getNumberOfElements(), page + 1, users.getTotalPages());
            
            return ResponseEntity.ok(users);
        } finally {
            MDC.remove("correlationId");
        }
    }

    /**
     * Retrieves a specific user by ID.
     * Cached for improved performance.
     * 
     * @param id User ID
     * @return User details
     */
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @Cacheable(value = "user", key = "#id")
    // @Timed(value = "users.get.byId", description = "Time taken to retrieve user by ID") // Uncomment when micrometer dependency is added
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "User ID", required = true, example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        
        String correlationId = generateCorrelationId();
        MDC.put("correlationId", correlationId);
        
        try {
            // Java 21: String Templates (when preview feature is enabled)
            // log.debug(STR."GET /users/\{id} - Get user by ID (correlationId: \{correlationId})");
            log.debug("GET /users/{} - Get user by ID (correlationId: {})", id, correlationId);
            
            UserResponse user = userService.getUserById(id);
            
            log.info("Successfully retrieved user with ID: {}", id);
            return ResponseEntity.ok(user);
        } finally {
            MDC.remove("correlationId");
        }
    }

    /**
     * Searches for users by first name and/or last name.
     * Uses case-insensitive regex matching.
     * 
     * @param firstName First name to search for
     * @param lastName Last name to search for
     * @return List of matching users
     */
    @Operation(summary = "Search users", 
               description = "Search users by first name and/or last name using case-insensitive regex matching")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved matching users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping("/search")
    // @Timed(value = "users.search", description = "Time taken to search users") // Uncomment when micrometer dependency is added
    public ResponseEntity<List<UserResponse>> searchUsers(
            @Parameter(description = "First name to search for (case-insensitive, partial match)", example = "John")
            @RequestParam(required = false) String firstName,
            @Parameter(description = "Last name to search for (case-insensitive, partial match)", example = "Doe")
            @RequestParam(required = false) String lastName) {
        
        String correlationId = generateCorrelationId();
        MDC.put("correlationId", correlationId);
        
        try {
            // Java 21: String Templates (when preview feature is enabled)
            // log.debug(STR."GET /users/search - Search users (firstName: \{firstName}, lastName: \{lastName}, correlationId: \{correlationId})");
            log.debug("GET /users/search - Search users (firstName: {}, lastName: {}, correlationId: {})", 
                     firstName, lastName, correlationId);
            
            List<UserResponse> users = userService.searchUsers(firstName, lastName);
            
            log.info("Search completed. Found {} users matching criteria", users.size());
            return ResponseEntity.ok(users);
        } finally {
            MDC.remove("correlationId");
        }
    }

    /**
     * Creates a new user.
     * Invalidates the users cache.
     * 
     * @param request User creation request
     * @return Created user details
     */
    @Operation(summary = "Create new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    @CacheEvict(value = "users", allEntries = true)
    // @Timed(value = "users.create", description = "Time taken to create user") // Uncomment when micrometer dependency is added
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "User details", required = true)
            @Valid @RequestBody UserRequest request) {
        
        String correlationId = generateCorrelationId();
        MDC.put("correlationId", correlationId);
        
        try {
            // Java 21: Record patterns for destructuring (when available)
            // if (request instanceof UserRequest(var firstName, var lastName, var address, var age)) {
            //     log.debug(STR."POST /users - Create user: \{firstName} \{lastName}");
            // }
            
            log.debug("POST /users - Create new user (correlationId: {})", correlationId);
            log.debug("User details: firstName={}, lastName={}, age={}", 
                     request.firstName(), request.lastName(), request.age());
            
            UserResponse user = userService.createUser(request);
            
            log.info("Successfully created user with ID: {}", user.id());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } finally {
            MDC.remove("correlationId");
        }
    }

    /**
     * Updates an existing user.
     * Invalidates both the specific user cache and the users list cache.
     * 
     * @param id User ID
     * @param request User update request
     * @return Updated user details
     */
    @Operation(summary = "Update user", description = "Update an existing user's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @CacheEvict(value = {"user", "users"}, key = "#id", allEntries = true)
    // @Timed(value = "users.update", description = "Time taken to update user") // Uncomment when micrometer dependency is added
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "User ID", required = true, example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @Parameter(description = "Updated user details", required = true)
            @Valid @RequestBody UserRequest request) {
        
        String correlationId = generateCorrelationId();
        MDC.put("correlationId", correlationId);
        
        try {
            // Java 21: String Templates (when preview feature is enabled)
            // log.debug(STR."PUT /users/\{id} - Update user (correlationId: \{correlationId})");
            log.debug("PUT /users/{} - Update user (correlationId: {})", id, correlationId);
            log.debug("Updated details: firstName={}, lastName={}, age={}", 
                     request.firstName(), request.lastName(), request.age());
            
            UserResponse user = userService.updateUser(id, request);
            
            log.info("Successfully updated user with ID: {}", id);
            return ResponseEntity.ok(user);
        } finally {
            MDC.remove("correlationId");
        }
    }

    /**
     * Deletes a user by ID.
     * Invalidates both the specific user cache and the users list cache.
     * 
     * @param id User ID
     * @return No content response
     */
    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @CacheEvict(value = {"user", "users"}, key = "#id", allEntries = true)
    // @Timed(value = "users.delete", description = "Time taken to delete user") // Uncomment when micrometer dependency is added
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", required = true, example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        
        String correlationId = generateCorrelationId();
        MDC.put("correlationId", correlationId);
        
        try {
            // Java 21: String Templates (when preview feature is enabled)
            // log.debug(STR."DELETE /users/\{id} - Delete user (correlationId: \{correlationId})");
            log.debug("DELETE /users/{} - Delete user (correlationId: {})", id, correlationId);
            
            userService.deleteUser(id);
            
            log.info("Successfully deleted user with ID: {}", id);
            return ResponseEntity.noContent().build();
        } finally {
            MDC.remove("correlationId");
        }
    }

    /**
     * Generates a unique correlation ID for request tracing.
     * 
     * @return UUID-based correlation ID
     */
    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }
}