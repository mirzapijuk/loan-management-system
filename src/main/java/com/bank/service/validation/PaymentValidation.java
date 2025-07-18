package com.bank.service.validation;

import com.bank.model.PaymentRequest;
import com.bank.repository.dao.PaymentDAO;
import com.bank.repository.entity.InstallmentEntity;
import com.bank.repository.entity.PaymentEntity;
import com.bank.service.validation.config.ValidationError;
import com.bank.service.validation.config.ValidationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@Component
@AllArgsConstructor
public class PaymentValidation {

    private PaymentDAO paymentDAO;

    public ValidationResponse validatePayment(final PaymentRequest paymentRequest, final InstallmentEntity installmentEntity) {
        ValidationError error = validateLastPaidInstallment(paymentRequest.getPartyNumber());
        if (error != null) {
            return ValidationResponse.of(error);
        }
        error = validatePaymentAmount(installmentEntity, paymentRequest.getAmount());
        return ValidationResponse.of(error);
    }

    private ValidationError validateLastPaidInstallment(final Long partyNumber) {
        ValidationError error = null;
        PaymentEntity lastPayment = paymentDAO.getPaymentsByParty(partyNumber).stream()
                .max(Comparator.comparingInt((PaymentEntity::getInstallmentNumber))).orElse(null);
        if (lastPayment != null) {
            LocalDate paymentDate = lastPayment.getPaymentDate();
            LocalDate now = LocalDate.now();

            if (paymentDate.getMonth() == now.getMonth() && paymentDate.getYear() == now.getYear()) {
                error = ValidationError.of("ALREADY_PAID", "The installment is already paid!");
            }
        }
        return error;
    }

    private ValidationError validatePaymentAmount(final InstallmentEntity installmentEntity, final BigDecimal amount) {
        ValidationError error = null;
        if (installmentEntity.getAmount().compareTo(amount) > 0) {
            error = ValidationError.of("PAID_LESS", "It is necessary to pay more " +
                    installmentEntity.getAmount().subtract(amount) + " euros!");
        }

        if (installmentEntity.getAmount().compareTo(amount) <  0) {
            error = ValidationError.of("PAID_MORE", amount.subtract(installmentEntity.getAmount()) +
                    "euros more than the agreed installment was paid!");
        }
        return error;
    }
}
