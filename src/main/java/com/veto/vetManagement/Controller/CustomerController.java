package com.veto.vetManagement.Controller;

import com.veto.vetManagement.Entities.Customer;
import com.veto.vetManagement.DTO.Customer.CustomerResponse;
import com.veto.vetManagement.DTO.Customer.CustomerSaveRequest;
import com.veto.vetManagement.DTO.Customer.CustomerUpdateRequest;
import com.veto.vetManagement.Services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<CustomerResponse>> findByName(@RequestParam String name) {
        List<Customer> customers = customerService.findByName(name);
        List<CustomerResponse> customerResponses = customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        Customer customer = customerService.getById(id);
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody CustomerSaveRequest customerSaveRequest) {
        Customer newCustomer = modelMapper.map(customerSaveRequest, Customer.class);
        Customer savedCustomer = customerService.save(newCustomer);
        CustomerResponse customerResponse = modelMapper.map(savedCustomer, CustomerResponse.class);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerUpdateRequest.setId(id);
        Customer updatedCustomer = modelMapper.map(customerUpdateRequest, Customer.class);
        Customer savedCustomer = customerService.update(updatedCustomer);
        CustomerResponse customerResponse = modelMapper.map(savedCustomer, CustomerResponse.class);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}