package com.veto.vetManagement.DTO.Animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import com.veto.vetManagement.DTO.Customer.CustomerResponse;

// AnimalResponse.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    private CustomerResponse customer;
}
