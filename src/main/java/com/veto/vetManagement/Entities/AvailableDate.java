package com.veto.vetManagement.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "available_dates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "available_date_id_seq")
    @SequenceGenerator(name = "available_date_id_seq", sequenceName = "available_date_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "available_date", nullable = false)
    private LocalDate availableDate;

    // Her müsait gün bir doktora aittir.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Doctor doctor;
}