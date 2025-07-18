package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String registrationNumber;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private int sector;
    private String createdBy;
    private LocalDateTime created;
    private String modifiedBy;
    private LocalDateTime modified;
    private String status;
}
