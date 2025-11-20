package com.abdul.usermanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User request payload for creating or updating a user")
public class UserRequest {

    @NotBlank(message = "First name must not be empty")
    @Schema(description = "User's first name", example = "John", required = true)
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @Schema(description = "User's address", example = "123 Main St, City, Country")
    private String address;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age cannot be zero or negative")
    @Schema(description = "User's age", example = "30", required = true)
    private Integer age;
}
