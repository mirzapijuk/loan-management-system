package com.bank.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq_gen")
    @SequenceGenerator(name = "payment_seq_gen", sequenceName = "payment_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanEntity loan;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "installment_number")
    private int installmentNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "created_by")
    private String createdBy;

    private LocalDateTime created;

    @Column(name = "modified_by")
    private String modifiedBy;

    private LocalDateTime modified;
}
