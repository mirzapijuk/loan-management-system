package com.bank.service.validation.config;

public class ValidationResponse {

    public static ValidationResponse of(ValidationError validationError) {
        if (validationError != null) {
            throw new ValidationException(validationError);
        }
        return null;
    }
}
