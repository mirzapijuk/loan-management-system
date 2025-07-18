package com.bank.service.impl;

import com.bank.model.PartyRequest;
import com.bank.repository.dao.PartyDAO;
import com.bank.repository.entity.PartyEntity;
import com.bank.security.SecurityUtils;
import com.bank.service.PartyService;
import com.bank.service.validation.PartyValidation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PartyServiceImpl implements PartyService {

    private final PartyValidation partyValidation;
    private final PartyDAO partyDAO;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public PartyEntity setParty(PartyRequest request) {
        partyValidation.validateParty(request.getPartyNumber());
        PartyEntity partyEntity = new PartyEntity();
        partyEntity.setPartyNumber(request.getPartyNumber());
        partyEntity.setValidFrom(request.getValidFrom() == null ? LocalDate.now() : request.getValidFrom());
        partyEntity.setValidTo(request.getValidTo());
        partyEntity.setCreated(LocalDateTime.now());
        partyEntity.setCreatedBy(securityUtils.getCurrentUsername());
        return partyDAO.save(partyEntity);
    }
}
