package com.bank.service.validation;

import com.bank.repository.dao.CustomerDAO;
import com.bank.service.validation.config.ValidationError;
import com.bank.service.validation.config.ValidationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerValidation {

    private CustomerDAO customerDAO;

    public ValidationResponse validateCustomer(final Long id) {
        ValidationError error = null;
        if (!customerDAO.existsById(id)) {
            error = ValidationError.of("NOT_EXISTS", "Customer does not exist!");
        }
        return ValidationResponse.of(error);
    }

    public ValidationResponse validateCustomerByRegistration(final String registrationNo) {
        ValidationError error = null;
        if (!customerDAO.existsByRegistrationNo(registrationNo)) {
            error = ValidationError.of("NOT_EXISTS", "Customer does not exist!");
        }
        return ValidationResponse.of(error);
    }
}
