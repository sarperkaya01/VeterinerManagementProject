package com.veto.vetManagement.DTO.Doctor;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DoctorSaveRequest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;
}