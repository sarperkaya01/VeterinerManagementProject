package com.veto.vetManagement.DTO.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// AppointmentUpdateRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    private Long id;
    private LocalDateTime appointmentDate;
    private Long animalId;
    private Long doctorId;
}
