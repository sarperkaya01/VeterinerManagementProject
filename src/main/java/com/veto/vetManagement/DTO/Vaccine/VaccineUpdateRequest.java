package com.veto.vetManagement.DTO.Vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// VaccineUpdateRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private Long animalId;
}