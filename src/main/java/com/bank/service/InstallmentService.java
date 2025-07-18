package com.bank.service;

import com.bank.repository.entity.InstallmentEntity;

import java.util.List;

public interface InstallmentService {

    List<InstallmentEntity> setInstallment(List<InstallmentEntity> entities);

    List<InstallmentEntity> getInstallmentsByLoanId(final Long loanId);

    InstallmentEntity getCurrentInstallmentByLoanId(final Long loanId);
}
