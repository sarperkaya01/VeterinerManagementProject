package com.veto.vetManagement.Services;

import com.veto.vetManagement.Entities.Animal;
import com.veto.vetManagement.Entities.Customer;
import com.veto.vetManagement.Util.NotFoundException;
import com.veto.vetManagement.DAO.AnimalRepository;
import com.veto.vetManagement.DAO.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;

    public AnimalService(AnimalRepository animalRepository, CustomerRepository customerRepository) {
        this.animalRepository = animalRepository;
        this.customerRepository = customerRepository;
    }

    public Animal getById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal with ID " + id + " not found."));
    }

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }
    
    public List<Animal> findByName(String name) {
        return animalRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Animal> findByCustomer(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer with ID " + customerId + " not found.");
        }
        return animalRepository.findByCustomerId(customerId);
    }

    public Animal save(Animal animal, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with ID " + customerId + " not found."));
        animal.setCustomer(customer);
        return animalRepository.save(animal);
    }

    public Animal update(Animal animal, Long customerId) {
        getById(animal.getId()); // Hayvan var mı kontrolü
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with ID " + customerId + " not found."));
        animal.setCustomer(customer);
        return animalRepository.save(animal);
    }

    public void delete(Long id) {
        Animal animalToDelete = getById(id);
        animalRepository.delete(animalToDelete);
    }
}