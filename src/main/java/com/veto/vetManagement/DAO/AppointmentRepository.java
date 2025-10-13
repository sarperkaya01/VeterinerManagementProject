package com.veto.vetManagement.DAO;

import com.veto.vetManagement.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // İster: Randevu kaydederken doktorun o saatte başka randevusu var mı kontrolü.
    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);

    // İster: Randevuları tarih aralığına ve doktora göre filtrelemek.
    List<Appointment> findByAppointmentDateBetweenAndDoctorId(LocalDateTime startDate, LocalDateTime endDate, Long doctorId);

    // İster: Randevuları tarih aralığına ve hayvana göre filtrelemek.
    List<Appointment> findByAppointmentDateBetweenAndAnimalId(LocalDateTime startDate, LocalDateTime endDate, Long animalId);
}