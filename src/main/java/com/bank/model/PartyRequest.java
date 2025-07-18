package com.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartyRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private Long partyNumber;
    private LocalDate validFrom;
    private LocalDate validTo;
}
