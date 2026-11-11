package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "User login credentials")
public class LoginRequest {

    @Schema(
            description = "Username",
            example = "john"
    )
    @NotBlank
    private String username;

    @Schema(
            description = "User password",
            example = "password123"
    )
    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
