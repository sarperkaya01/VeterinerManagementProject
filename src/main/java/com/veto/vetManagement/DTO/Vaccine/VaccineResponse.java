package com.veto.vetManagement.DTO.Vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.veto.vetManagement.DTO.Animal.AnimalResponse;

// VaccineResponse.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResponse {
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private AnimalResponse animal;
}