package com.abdul.usermanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "User request payload for creating or updating a user")
public record UserRequest(
    @NotBlank(message = "First name must not be empty")
    @Schema(description = "User's first name", example = "John", required = true)
    String firstName,

    @Schema(description = "User's last name", example = "Doe")
    String lastName,

    @Schema(description = "User's address", example = "123 Main St, City, Country")
    String address,

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age cannot be zero or negative")
    @Schema(description = "User's age", example = "30", required = true)
    Integer age
) {
    // Compact constructor for additional validation if needed
    public UserRequest {
        // Validation is handled by Jakarta Bean Validation annotations
        // Additional custom validation can be added here if needed
    }
    
    // Static builder method for backward compatibility with tests
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder class for backward compatibility with existing test code
    public static class Builder {
        private String firstName;
        private String lastName;
        private String address;
        private Integer age;
        
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
        
        public UserRequest build() {
            return new UserRequest(firstName, lastName, address, age);
        }
    }
}