package com.remedy.iarlen.course.User;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @NotBlank
        String oldPassword,
        @NotBlank
        String newPassword) {
}
