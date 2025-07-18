package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Installment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int installmentNumber;
    private String registrationNumber;
    private String customerName;
    private Long partyNumber;
    private LocalDate dueDate;
    private BigDecimal amount;
}
