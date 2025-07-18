package com.bank.conroller;

import com.bank.model.Customer;
import com.bank.model.CustomerRequest;
import com.bank.repository.entity.CustomerEntity;
import com.bank.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
@AllArgsConstructor
public class CustomerRest {

    private  CustomerService customerService;

    @Operation(summary = "Get all customers.")
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @Operation(summary = "Get customer by id.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(summary = "Get Customer by registration number")
    @GetMapping(value = "/registration-no")
    public ResponseEntity<Customer> getCustomerByRegistrationNo(
            @RequestParam(name = "Registration no") final String registrationNo) {
        return ResponseEntity.ok(customerService.getCustomerByRegistrationNo(registrationNo));
    }

    @Operation(summary = "Create customer.")
    @PostMapping
    ResponseEntity<CustomerEntity> setCustomer(@RequestBody @Valid final CustomerRequest request) {
        return ResponseEntity.ok(customerService.setCustomer(request));
    }

    @Operation(summary = "Update customer by id.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(name = "id") final Long id,
                                            @RequestBody final CustomerRequest request) {
        request.setId(id);
        return ResponseEntity.ok(customerService.updateCustomer(request));
    }

    @Operation(summary = "Delete customer by id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") final Long id) {
        customerService.deleteCustomer(id);
        return  ResponseEntity.ok("Customer deleted successfully!");
    }
}
