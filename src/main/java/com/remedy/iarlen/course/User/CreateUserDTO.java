package com.remedy.iarlen.course.User;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
        @NotBlank
        String username,
        @NotBlank
        String password,

        UserRole role
        ) {
}
