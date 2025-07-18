package com.bank.service.impl;

import com.bank.model.CriticalCustomer;
import com.bank.repository.dao.InstallmentDAO;
import com.bank.repository.entity.InstallmentEntity;
import com.bank.security.SecurityUtils;
import com.bank.service.EmailService;
import com.bank.service.InstallmentService;
import com.bank.service.validation.InstallmentValidation;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentDAO installmentDAO;
    private final InstallmentValidation installmentValidation;
    private final EmailService emailService;
    private final SecurityUtils securityUtils;

    @Override
    public List<InstallmentEntity> setInstallment(List<InstallmentEntity> entities) {
        entities.forEach(value -> {
            value.setCreatedBy(securityUtils.getCurrentUsername());
            value.setCreated(LocalDateTime.now());
        });
       return installmentDAO.saveAll(entities);
    }

    @Override
    public List<InstallmentEntity> getInstallmentsByLoanId(final Long loanId) {
        return installmentDAO.getInstallmentsByLoanId(loanId);
    }

    @Override
    public InstallmentEntity getCurrentInstallmentByLoanId(final Long loanId) {
        installmentValidation.validateInstallmentExistence(loanId);
        InstallmentEntity installmentEntity = installmentDAO.getCurrentInstallmentsByLoanId(loanId);
        installmentValidation.isLoanPaid(installmentEntity);
        return installmentEntity;
    }

    /**
     * Scheduled task that runs every day at 9 AM.
     * Checks for clients who have loan installments overdue by more than 5 days,
     * and sends them warning emails regarding their delayed payments.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkLatePayment() {
        List<CriticalCustomer> criticalCustomers = installmentDAO.getCurrentUnpaidInstallments();
        criticalCustomers.forEach(customer -> {
            try {
                emailService.sendPaymentWarning(customer);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
