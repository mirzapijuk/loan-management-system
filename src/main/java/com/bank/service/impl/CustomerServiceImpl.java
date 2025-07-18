package com.bank.service.impl;

import com.bank.model.Customer;
import com.bank.model.CustomerRequest;
import com.bank.repository.dao.CustomerDAO;
import com.bank.repository.entity.CustomerEntity;
import com.bank.security.SecurityUtils;
import com.bank.service.CustomerService;
import com.bank.service.mapper.CustomerMapper;
import com.bank.service.validation.CustomerValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final CustomerMapper customerMapper;
    private final CustomerValidation customerValidation;
    private final SecurityUtils securityUtils;

    @Override
    public List<Customer> getCustomers() {
        List<CustomerEntity> entities = customerDAO.findAll();
        return customerMapper.entitiesToDtos(entities);
    }

    @Override
    public Customer getCustomerById(final Long id) {
        customerValidation.validateCustomer(id);
        CustomerEntity entity = customerDAO.findById(id);
        return customerMapper.entityToDto(entity);
    }

    @Override
    public Customer getCustomerByRegistrationNo(final String registrationNo) {
        customerValidation.validateCustomerByRegistration(registrationNo);
        CustomerEntity entity = customerDAO.findCustomerByRegistrationNo(registrationNo);
        return customerMapper.entityToDto(entity);
    }

    @Override
    public CustomerEntity setCustomer(final CustomerRequest request) {
        CustomerEntity entity = customerMapper.requestToEntity(request);
        entity.setCreated(LocalDateTime.now());
        entity.setCreatedBy(securityUtils.getCurrentUsername());
        return customerDAO.save(entity);
    }

    @Override
    public Customer updateCustomer(final CustomerRequest request) {
        customerValidation.validateCustomer(request.getId());
        CustomerEntity entity = customerDAO.findById(request.getId());
        customerMapper.updateEntityFromDto(request, entity);
        entity.setModified(LocalDateTime.now());
        entity.setModifiedBy(securityUtils.getCurrentUsername());
        return customerMapper.entityToDto(customerDAO.merge(entity));
    }

    @Override
    public void deleteCustomer(final Long id) {
        customerValidation.validateCustomer(id);
        customerDAO.deleteById(id);
    }
}
