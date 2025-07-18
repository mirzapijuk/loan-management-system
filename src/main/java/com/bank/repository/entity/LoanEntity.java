package com.bank.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loans_seq_gen")
    @SequenceGenerator(name = "loans_seq_gen", sequenceName = "loans_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "party", nullable = false)
    private PartyEntity party;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "repayment_period_months")
    private Integer repaymentPeriodMonths;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "remaining_debt_amount")
    private BigDecimal remainingDebtAmount;

    @Column(name = "created_by")
    private String createdBy;

    private LocalDateTime created;

    @Column(name = "modified_by")
    private String modifiedBy;

    private LocalDateTime modified;

    private String status;
}
