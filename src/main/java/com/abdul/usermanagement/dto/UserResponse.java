package com.abdul.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "User response payload")
public record UserResponse(
    @Schema(description = "User's unique identifier", example = "507f1f77bcf86cd799439011")
    String id,
    
    @Schema(description = "User's first name", example = "John")
    String firstName,
    
    @Schema(description = "User's last name", example = "Doe")
    String lastName,
    
    @Schema(description = "User's address", example = "123 Main St, City, Country")
    String address,
    
    @Schema(description = "User's age", example = "30")
    Integer age,
    
    @Schema(description = "Timestamp when user was created", example = "2024-01-01T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt,
    
    @Schema(description = "Timestamp when user was last updated", example = "2024-01-01T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime updatedAt
) {
    // Static builder method for backward compatibility
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder class for backward compatibility with existing code
    public static class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private String address;
        private Integer age;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public Builder address(String address) {
            this.address = address;
            return this;
        }
        
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }
        
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }
        
        public UserResponse build() {
            return new UserResponse(id, firstName, lastName, address, age, createdAt, updatedAt);
        }
    }
}

// Made with Bob
