package com.example.expensetracker.dto;

public record ErrorResponse(int status, String message, long timestamp) {
}
