package com.veto.vetManagement.DTO.AvailableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.veto.vetManagement.DTO.Doctor.DoctorResponse;

// AvailableDateResponse.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateResponse {
    private Long id;
    private LocalDate availableDate;
    private DoctorResponse doctor;
}