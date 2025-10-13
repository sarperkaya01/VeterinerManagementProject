package com.veto.vetManagement.Services;
import com.veto.vetManagement.Entities.Customer;
import com.veto.vetManagement.Util.AlreadyExistsException;
import com.veto.vetManagement.Util.NotFoundException;
import com.veto.vetManagement.DAO.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer with ID " + id + " not found."));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    public List<Customer> findByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public Customer save(Customer customer) {
        if (customerRepository.existsByPhone(customer.getPhone()) || customerRepository.existsByMail(customer.getMail())) {
            throw new AlreadyExistsException("A customer with this phone number or email already exists.");
        }
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        // ID ile müşteri var mı kontrolü
        getById(customer.getId());
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        Customer customerToDelete = getById(id);
        customerRepository.delete(customerToDelete);
    }
}