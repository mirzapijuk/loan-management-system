package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String registrationNumber;
    private String fullName;
    private Long party;
    private BigDecimal approvedAmount;
    private LocalDate approvalDate;
    private Integer repaymentPeriodMonths;
    private BigDecimal interestRate;
    private BigDecimal remainingDebtAmount;
    private String createdBy;
    private LocalDateTime created;
    private String modifiedBy;
    private LocalDateTime modified;
    private String status;
}
