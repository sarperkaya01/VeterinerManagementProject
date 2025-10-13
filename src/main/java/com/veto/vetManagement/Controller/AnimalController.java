package com.veto.vetManagement.Controller;

import com.veto.vetManagement.Entities.Animal;
import com.veto.vetManagement.DTO.Animal.AnimalResponse;
import com.veto.vetManagement.DTO.Animal.AnimalSaveRequest;
import com.veto.vetManagement.DTO.Animal.AnimalUpdateRequest;
import com.veto.vetManagement.Services.AnimalService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final ModelMapper modelMapper;

    public AnimalController(AnimalService animalService, ModelMapper modelMapper) {
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> findAll() {
        List<Animal> animals = animalService.findAll();
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(animalResponses);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<AnimalResponse>> findByName(@RequestParam String name) {
        List<Animal> animals = animalService.findByName(name);
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(animalResponses);
    }
    
    @GetMapping(params = "customerId")
    public ResponseEntity<List<AnimalResponse>> findByCustomerId(@RequestParam Long customerId) {
        List<Animal> animals = animalService.findByCustomer(customerId);
        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(animalResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> getById(@PathVariable Long id) {
        Animal animal = animalService.getById(id);
        AnimalResponse animalResponse = modelMapper.map(animal, AnimalResponse.class);
        return ResponseEntity.ok(animalResponse);
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> save(@RequestBody AnimalSaveRequest animalSaveRequest) {
        Animal newAnimal = modelMapper.map(animalSaveRequest, Animal.class);
        Animal savedAnimal = animalService.save(newAnimal, animalSaveRequest.getCustomerId());
        AnimalResponse animalResponse = modelMapper.map(savedAnimal, AnimalResponse.class);
        return new ResponseEntity<>(animalResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponse> update(@PathVariable Long id, @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        animalUpdateRequest.setId(id);
        Animal updatedAnimal = modelMapper.map(animalUpdateRequest, Animal.class);
        Animal savedAnimal = animalService.update(updatedAnimal, animalUpdateRequest.getCustomerId());
        AnimalResponse animalResponse = modelMapper.map(savedAnimal, AnimalResponse.class);
        return ResponseEntity.ok(animalResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
