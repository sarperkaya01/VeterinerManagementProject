package com.veto.vetManagement.DAO;

import com.veto.vetManagement.Entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {
    // İster: Randevu oluştururken doktorun girilen tarihte müsait günü olup olmadığı kontrolü için.
    Optional<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);
}