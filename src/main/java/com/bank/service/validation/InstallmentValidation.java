package com.bank.service.validation;

import com.bank.repository.dao.InstallmentDAO;
import com.bank.repository.entity.InstallmentEntity;
import com.bank.service.validation.config.ValidationError;
import com.bank.service.validation.config.ValidationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InstallmentValidation {

    private InstallmentDAO installmentDAO;

    public ValidationResponse validateInstallment(final Long loanId) {
        ValidationError error = null;
        if(installmentDAO.existsInstallment(loanId)) {
            error = ValidationError.of("ALREADY_EXISTS", "Installment for this loan already exists!");
        }
        return ValidationResponse.of(error);
    }

    public ValidationResponse validateInstallmentExistence(final Long loanId) {
        ValidationError error = null;
        if(!installmentDAO.existsInstallment(loanId)) {
            error = ValidationError.of("DOES_NOT_EXISTS", "Installment for this loan does not exist!");
        }
        return ValidationResponse.of(error);
    }

    public ValidationResponse isLoanPaid(final InstallmentEntity installmentEntity) {
        ValidationError error = null;
        if(installmentEntity == null) {
            error = ValidationError.of("LOAN_PAID", "Loan has been paid!");
        }
        return ValidationResponse.of(error);
    }
}
