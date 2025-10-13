package com.veto.vetManagement.Controller;

import com.veto.vetManagement.Entities.Doctor;
import com.veto.vetManagement.DTO.Doctor.DoctorResponse;
import com.veto.vetManagement.DTO.Doctor.DoctorSaveRequest;
import com.veto.vetManagement.DTO.Doctor.DoctorUpdateRequest;
import com.veto.vetManagement.Services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    public DoctorController(DoctorService doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        List<DoctorResponse> doctorResponses = doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctorResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getById(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        DoctorResponse doctorResponse = modelMapper.map(doctor, DoctorResponse.class);
        return ResponseEntity.ok(doctorResponse);
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> save(@RequestBody DoctorSaveRequest doctorSaveRequest) {
        Doctor newDoctor = modelMapper.map(doctorSaveRequest, Doctor.class);
        Doctor savedDoctor = doctorService.save(newDoctor);
        DoctorResponse doctorResponse = modelMapper.map(savedDoctor, DoctorResponse.class);
        return new ResponseEntity<>(doctorResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(@PathVariable Long id,
            @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        doctorUpdateRequest.setId(id);
        Doctor updatedDoctor = modelMapper.map(doctorUpdateRequest, Doctor.class);
        Doctor savedDoctor = doctorService.update(updatedDoctor);
        DoctorResponse doctorResponse = modelMapper.map(savedDoctor, DoctorResponse.class);
        return ResponseEntity.ok(doctorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        doctorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
