package com.bank.service;

import com.bank.model.Customer;
import com.bank.model.CustomerRequest;
import com.bank.repository.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();

    Customer getCustomerById(Long id);

    Customer getCustomerByRegistrationNo(String registrationNo);

    CustomerEntity setCustomer(CustomerRequest request);

    Customer updateCustomer(CustomerRequest request);

    void deleteCustomer(Long id);
}
