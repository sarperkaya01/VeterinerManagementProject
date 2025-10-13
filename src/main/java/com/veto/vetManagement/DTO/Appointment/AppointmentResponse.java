package com.veto.vetManagement.DTO.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.veto.vetManagement.DTO.Animal.AnimalResponse;
import com.veto.vetManagement.DTO.Doctor.DoctorResponse;

// AppointmentResponse.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentDate;
    private AnimalResponse animal;
    private DoctorResponse doctor;
}