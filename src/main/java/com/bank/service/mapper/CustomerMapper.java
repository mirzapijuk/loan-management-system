package com.bank.service.mapper;

import com.bank.model.Customer;
import com.bank.model.CustomerRequest;
import com.bank.repository.entity.CustomerEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer entityToDto(CustomerEntity entity);

    CustomerEntity dtoToEntity(Customer customer);

    List<Customer> entitiesToDtos(List<CustomerEntity> entities);

    CustomerEntity requestToEntity(CustomerRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CustomerRequest customerRequest, @MappingTarget CustomerEntity customerEntity);
}
