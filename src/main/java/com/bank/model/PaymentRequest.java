package com.bank.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long partyNumber;
    private BigDecimal amount;
}
