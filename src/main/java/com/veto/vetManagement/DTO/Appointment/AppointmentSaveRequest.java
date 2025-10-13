package com.veto.vetManagement.DTO.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// AppointmentSaveRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
}
