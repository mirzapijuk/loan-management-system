package com.bank.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "installment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "installment_seq_gen")
    @SequenceGenerator(name = "installment_seq_gen", sequenceName = "installment_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "installment_number")
    private int installmentNumber;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanEntity loan;

    @Column(name = "created_by")
    private String createdBy;

    private LocalDateTime created;

    @Column(name = "modified_by")
    private String modifiedBy;

    private LocalDateTime modified;

    private String status;
}
