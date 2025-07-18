package com.bank.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "party")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "party_sequence_gen")
    @SequenceGenerator(name = "party_sequence_gen", sequenceName = "party_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "party_number", nullable = false)
    private Long partyNumber;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "created_by")
    private String createdBy;

    private LocalDateTime created;

    @Column(name = "modified_by")
    private String modifiedBy;

    private LocalDateTime modified;

    private String status;
}
