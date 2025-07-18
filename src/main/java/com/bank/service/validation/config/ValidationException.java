package com.bank.service.validation.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationException extends RuntimeException{
    private final ValidationError error;

    public String getError() {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("code: " + getCode(error));
        errorMessage.append("\n");
        errorMessage.append("message: "+ getMessage(error));
        return errorMessage.toString();
    }

    private String getCode(ValidationError validationError) {
        return validationError.getCode();
    }

    private String getMessage(ValidationError validationError) {
        return validationError.getMessage();
    }
}
