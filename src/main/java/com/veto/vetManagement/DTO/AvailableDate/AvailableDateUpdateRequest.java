package com.veto.vetManagement.DTO.AvailableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// AvailableDateUpdateRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {
    private Long id;
    private LocalDate availableDate;
    private Long doctorId;
}