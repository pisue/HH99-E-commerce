package com.hh99.ecommerce.common.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, String errCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.value();
        this.error = errCode;
        this.message = message;
    }
}
