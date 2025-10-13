package com.veto.vetManagement.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_id_seq")
    @SequenceGenerator(name = "animal_id_seq", sequenceName = "animal_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "colour", nullable = false)
    private String colour;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    // Her hayvan bir müşteriye aittir.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    // Bir hayvanın birden fazla aşısı olabilir.
    // Hayvan silinirse, aşı kayıtları da silinir.
    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccine> vaccineList;

    // Bir hayvanın birden fazla randevusu olabilir.
    // Hayvan silinirse, randevu kayıtları da silinir.
    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointmentList;
}