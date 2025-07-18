package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private String clientRegNo;
    private BigDecimal approvedAmount;
    private LocalDate approvalDate;
    private Integer repaymentPeriodMonths;
    private BigDecimal interestRate;
    private PartyRequest partyRequest;
}
