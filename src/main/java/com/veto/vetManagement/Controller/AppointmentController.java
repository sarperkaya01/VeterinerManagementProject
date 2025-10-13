package com.veto.vetManagement.Controller;

import com.veto.vetManagement.Entities.Appointment;
import com.veto.vetManagement.DTO.Appointment.AppointmentResponse;
import com.veto.vetManagement.DTO.Appointment.AppointmentSaveRequest;
import com.veto.vetManagement.DTO.Appointment.AppointmentUpdateRequest;
import com.veto.vetManagement.Services.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;

    public AppointmentController(AppointmentService appointmentService, ModelMapper modelMapper) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<AppointmentResponse>> findByDateRangeAndEntity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long animalId) {
        
        List<Appointment> appointments;
        if (doctorId != null) {
            appointments = appointmentService.findByDoctorAndDateRange(doctorId, startDate, endDate);
        } else if (animalId != null) {
            appointments = appointmentService.findByAnimalAndDateRange(animalId, startDate, endDate);
        } else {
            return ResponseEntity.badRequest().build();
        }
        
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getById(id);
        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> save(@RequestBody AppointmentSaveRequest saveRequest) {
        Appointment newAppointment = modelMapper.map(saveRequest, Appointment.class);
        Appointment savedAppointment = appointmentService.save(newAppointment, saveRequest.getDoctorId(), saveRequest.getAnimalId());
        AppointmentResponse response = modelMapper.map(savedAppointment, AppointmentResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable Long id, @RequestBody AppointmentUpdateRequest updateRequest) {
        updateRequest.setId(id);
        Appointment updatedAppointment = modelMapper.map(updateRequest, Appointment.class);
        Appointment savedAppointment = appointmentService.update(updatedAppointment, updateRequest.getDoctorId(), updateRequest.getAnimalId());
        AppointmentResponse response = modelMapper.map(savedAppointment, AppointmentResponse.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}