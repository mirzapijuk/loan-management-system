package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.io.Serializable;

@Data
@AllArgsConstructor
public class CriticalCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long partyNumber;
    private String registrationNumber;
    private String fullName;
    private String email;
    private String phone;
}
