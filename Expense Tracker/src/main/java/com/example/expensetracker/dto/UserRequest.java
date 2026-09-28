package com.example.expensetracker.dto;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull String userName,
        @NotNull String email
        ) {
}
