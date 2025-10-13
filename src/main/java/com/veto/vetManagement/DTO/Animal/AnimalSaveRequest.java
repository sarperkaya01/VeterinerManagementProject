package com.veto.vetManagement.DTO.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// AnimalSaveRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    private Long customerId;
}