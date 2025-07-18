package com.bank.service.validation.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValidationError {
    private String code;
    private String message;

    public static ValidationError of(String code, String message) {
        return new ValidationError(code, message);
    }
}