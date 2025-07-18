package com.bank.service;

import com.bank.model.Installment;
import com.bank.model.Loan;
import com.bank.model.LoanRequest;
import com.bank.repository.entity.LoanEntity;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {

    List<Loan> getLoans();

    Loan getLoanById(Long id);

    LoanEntity getLoanByPartyNumber(Long partyNumber);

    Loan setLoan(LoanRequest loanRequest);

    void updateRemainingDbtAmount(Long loanId, BigDecimal amount);

    List<Installment> getAmortizationPlan(Long partyNumber);

    byte[] getLoanSchedulePdf(Long partyNumber);

    String sendInstallmentToCustomer(Long partyNumber);
}
