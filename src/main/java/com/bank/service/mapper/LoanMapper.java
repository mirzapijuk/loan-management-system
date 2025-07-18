package com.bank.service.mapper;

import com.bank.model.Loan;
import com.bank.model.LoanRequest;
import com.bank.repository.entity.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source = "party.partyNumber", target = "party")
    @Mapping(source = "customer.registrationNumber", target = "registrationNumber")
    @Mapping(source = "customer.fullName", target = "fullName")
    Loan entityToDto(LoanEntity entity);

    List<Loan> entitiesToDtos(List<LoanEntity> entities);

    @Mapping(source = "approvedAmount", target = "remainingDebtAmount")
    LoanEntity requestToEntity(LoanRequest request);
}
