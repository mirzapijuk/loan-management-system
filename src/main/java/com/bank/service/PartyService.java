package com.bank.service;

import com.bank.model.PartyRequest;
import com.bank.repository.entity.PartyEntity;

public interface PartyService {

    PartyEntity setParty(PartyRequest request);
}
