package com.veto.vetManagement.Controller;



import com.veto.vetManagement.Entities.AvailableDate;
import com.veto.vetManagement.DTO.AvailableDate.AvailableDateResponse;
import com.veto.vetManagement.DTO.AvailableDate.AvailableDateSaveRequest;
import com.veto.vetManagement.DTO.AvailableDate.AvailableDateUpdateRequest;
import com.veto.vetManagement.Services.AvailableDateService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/available-dates")
public class AvailableDateController {

    private final AvailableDateService availableDateService;
    private final ModelMapper modelMapper;

    public AvailableDateController(AvailableDateService availableDateService, ModelMapper modelMapper) {
        this.availableDateService = availableDateService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<AvailableDateResponse>> findAll() {
        List<AvailableDate> availableDates = availableDateService.findAll();
        List<AvailableDateResponse> responses = availableDates.stream()
                .map(date -> modelMapper.map(date, AvailableDateResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailableDateResponse> getById(@PathVariable Long id) {
        AvailableDate availableDate = availableDateService.getById(id);
        AvailableDateResponse response = modelMapper.map(availableDate, AvailableDateResponse.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AvailableDateResponse> save(@RequestBody AvailableDateSaveRequest saveRequest) {
        AvailableDate newDate = modelMapper.map(saveRequest, AvailableDate.class);
        AvailableDate savedDate = availableDateService.save(newDate, saveRequest.getDoctorId());
        AvailableDateResponse response = modelMapper.map(savedDate, AvailableDateResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailableDateResponse> update(@PathVariable Long id, @RequestBody AvailableDateUpdateRequest updateRequest) {
        updateRequest.setId(id);
        AvailableDate updatedDate = modelMapper.map(updateRequest, AvailableDate.class);
        AvailableDate savedDate = availableDateService.update(updatedDate, updateRequest.getDoctorId());
        AvailableDateResponse response = modelMapper.map(savedDate, AvailableDateResponse.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availableDateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}