package com.veto.vetManagement.DTO.Vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// VaccineSaveRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private Long animalId;
}