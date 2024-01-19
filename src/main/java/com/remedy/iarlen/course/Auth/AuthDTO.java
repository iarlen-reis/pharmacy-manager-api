package com.remedy.iarlen.course.Auth;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
