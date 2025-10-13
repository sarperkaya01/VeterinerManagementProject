package com.veto.vetManagement.Controller;

import com.veto.vetManagement.Entities.Vaccine;
import com.veto.vetManagement.DTO.Vaccine.VaccineResponse;
import com.veto.vetManagement.DTO.Vaccine.VaccineSaveRequest;
import com.veto.vetManagement.DTO.Vaccine.VaccineUpdateRequest;
import com.veto.vetManagement.Services.VaccineService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;
    private final ModelMapper modelMapper;

    public VaccineController(VaccineService vaccineService, ModelMapper modelMapper) {
        this.vaccineService = vaccineService;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping
    public ResponseEntity<List<VaccineResponse>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<Vaccine> vaccines;
        if (startDate != null && endDate != null) {
            vaccines = vaccineService.findByDateRange(startDate, endDate);
        } else {
            vaccines = vaccineService.findAll();
        }
        
        List<VaccineResponse> responses = vaccines.stream()
                .map(vaccine -> modelMapper.map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping(params = "animalId")
    public ResponseEntity<List<VaccineResponse>> findByAnimalId(@RequestParam Long animalId) {
        List<Vaccine> vaccines = vaccineService.findByAnimalId(animalId);
        List<VaccineResponse> responses = vaccines.stream()
                .map(vaccine -> modelMapper.map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponse> getById(@PathVariable Long id) {
        Vaccine vaccine = vaccineService.getById(id);
        VaccineResponse response = modelMapper.map(vaccine, VaccineResponse.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<VaccineResponse> save(@RequestBody VaccineSaveRequest saveRequest) {
        Vaccine newVaccine = modelMapper.map(saveRequest, Vaccine.class);
        Vaccine savedVaccine = vaccineService.save(newVaccine, saveRequest.getAnimalId());
        VaccineResponse response = modelMapper.map(savedVaccine, VaccineResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccineResponse> update(@PathVariable Long id, @RequestBody VaccineUpdateRequest updateRequest) {
        updateRequest.setId(id);
        Vaccine updatedVaccine = modelMapper.map(updateRequest, Vaccine.class);
        Vaccine savedVaccine = vaccineService.update(updatedVaccine, updateRequest.getAnimalId());
        VaccineResponse response = modelMapper.map(savedVaccine, VaccineResponse.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vaccineService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}