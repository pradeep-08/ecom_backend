package com.webbious.ecommerce.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private Instant timestamp;
    private T data;

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
        this.data = data;
    }
}

