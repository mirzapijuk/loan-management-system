package com.bank.service.impl;

import com.bank.model.Payment;
import com.bank.model.PaymentRequest;
import com.bank.repository.dao.PaymentDAO;
import com.bank.repository.entity.InstallmentEntity;
import com.bank.repository.entity.LoanEntity;
import com.bank.repository.entity.PaymentEntity;
import com.bank.security.SecurityUtils;
import com.bank.service.InstallmentService;
import com.bank.service.LoanService;
import com.bank.service.PaymentService;
import com.bank.service.mapper.PaymentMapper;
import com.bank.service.validation.PartyValidation;
import com.bank.service.validation.PaymentValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;
    private final PartyValidation partyValidation;
    private final PaymentMapper paymentMapper;
    private final LoanService loanService;
    private final InstallmentService installmentService;
    private final PaymentValidation paymentValidation;
    private final SecurityUtils securityUtils;

    @Override
    public List<Payment> getPaymentsByParty(final Long partyNumber) {
        partyValidation.validatePartyExistence(partyNumber);
        List<PaymentEntity> paymentEntities = paymentDAO.getPaymentsByParty(partyNumber);
        return paymentMapper.entitiesToDtos(paymentEntities);
    }

    @Override
    public Payment setPayment(final PaymentRequest paymentRequest) {
        PaymentEntity paymentEntity = new PaymentEntity();
        LoanEntity loan = loanService.getLoanByPartyNumber(paymentRequest.getPartyNumber());
        InstallmentEntity installmentEntity = installmentService.getCurrentInstallmentByLoanId(loan.getId());
        paymentValidation.validatePayment(paymentRequest, installmentEntity);
        paymentEntity.setLoan(loan);
        paymentEntity.setInstallmentNumber(installmentEntity.getInstallmentNumber());
        paymentEntity.setPaymentDate(LocalDate.now());
        paymentEntity.setAmount(paymentRequest.getAmount());
        paymentEntity.setCreated(LocalDateTime.now());
        paymentEntity.setCreatedBy(securityUtils.getCurrentUsername());
        Payment payment = paymentMapper.entityToDto(paymentDAO.save(paymentEntity));
        loanService.updateRemainingDbtAmount(loan.getId(), paymentRequest.getAmount());
        return payment;
    }
}
