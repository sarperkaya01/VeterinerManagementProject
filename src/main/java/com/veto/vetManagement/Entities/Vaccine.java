package com.veto.vetManagement.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccine_id_seq")
    @SequenceGenerator(name = "vaccine_id_seq", sequenceName = "vaccine_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Temporal(TemporalType.DATE)
    @Column(name = "protection_start_date", nullable = false)
    private LocalDate protectionStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "protection_finish_date", nullable = false)
    private LocalDate protectionFinishDate;

    // Her aşı bir hayvana aittir.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Animal animal;
}