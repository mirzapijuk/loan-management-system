package com.bank.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String email;

    private String phone;

    private String address;

    private int sector;

    @Column(name = "created_by")
    private String createdBy;

    private LocalDateTime created;

    @Column(name = "modified_by")
    private String modifiedBy;

    private LocalDateTime modified;

    private String status;
}
