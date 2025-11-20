package com.abdul.usermanagement.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Standard error response format")
public record ErrorResponse(
    @Schema(description = "Timestamp when error occurred", example = "2024-01-01T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime timestamp,
    
    @Schema(description = "HTTP status code", example = "400")
    int status,
    
    @Schema(description = "Error type", example = "Bad Request")
    String error,
    
    @Schema(description = "Error message", example = "Validation failed")
    String message,
    
    @Schema(description = "Request path where error occurred", example = "/api/users")
    String path,
    
    @Schema(description = "Field-level validation errors")
    Map<String, String> fieldErrors
) {
    // Static builder method for backward compatibility
    public static Builder builder() {
        return new Builder();
    }
    
    // Builder class for backward compatibility with existing code
    public static class Builder {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, String> fieldErrors;
        
        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder status(int status) {
            this.status = status;
            return this;
        }
        
        public Builder error(String error) {
            this.error = error;
            return this;
        }
        
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        
        public Builder path(String path) {
            this.path = path;
            return this;
        }
        
        public Builder fieldErrors(Map<String, String> fieldErrors) {
            this.fieldErrors = fieldErrors;
            return this;
        }
        
        public ErrorResponse build() {
            return new ErrorResponse(timestamp, status, error, message, path, fieldErrors);
        }
    }
}

// Made with Bob
