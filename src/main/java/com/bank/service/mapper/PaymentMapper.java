package com.bank.service.mapper;

import com.bank.model.Payment;
import com.bank.repository.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "loan.party.partyNumber", target = "partyNumber")
    Payment entityToDto(PaymentEntity paymentEntity);

    List<Payment> entitiesToDtos(List<PaymentEntity> paymentEntities);
}
