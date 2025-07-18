package com.bank.service.validation;

import com.bank.repository.dao.PartyDAO;
import com.bank.service.validation.config.ValidationError;
import com.bank.service.validation.config.ValidationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PartyValidation {

    private PartyDAO partyDAO;

    public ValidationResponse validateParty(final Long partyNumber) {
        ValidationError error = null;
        if(partyDAO.existsParty(partyNumber)) {
            error = ValidationError.of("ALREADY_EXISTS", "Party with number: " + partyNumber + " already exists!");
        };
        return ValidationResponse.of(error);
    }

    public ValidationResponse validatePartyExistence(final Long partyNumber) {
        ValidationError error = null;
        if(!partyDAO.existsParty(partyNumber)) {
            error = ValidationError.of("DOES_NOT_EXIST", "Party with number: " + partyNumber + " does not exists!");
        };
        return ValidationResponse.of(error);
    }
}
