package com.veto.vetManagement.DTO.Doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DoctorUpdateRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    private Long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}
