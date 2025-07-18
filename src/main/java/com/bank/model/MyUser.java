package com.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String role;

}
