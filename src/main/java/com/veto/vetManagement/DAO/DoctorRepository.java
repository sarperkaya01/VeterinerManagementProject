package com.veto.vetManagement.DAO;
import com.veto.vetManagement.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByPhone(String phone);
    
    boolean existsByMail(String mail);
}