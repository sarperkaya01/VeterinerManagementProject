package com.veto.vetManagement.DTO.AvailableDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {
    private LocalDate availableDate;
    private Long doctorId;
}