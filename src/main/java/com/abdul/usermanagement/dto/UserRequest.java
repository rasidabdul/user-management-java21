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
}