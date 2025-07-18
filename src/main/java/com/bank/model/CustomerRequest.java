package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    @NotNull
    private String registrationNumber;
    @NotNull
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private int sector;
}
