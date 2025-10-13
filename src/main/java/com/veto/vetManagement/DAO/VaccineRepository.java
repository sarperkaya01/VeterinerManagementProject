package com.veto.vetManagement.DAO;

import com.veto.vetManagement.Entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    // İster: Hayvan id’sine göre belirli bir hayvana ait tüm aşı kayıtlarını listelemek.
    List<Vaccine> findByAnimalId(Long animalId);

    // İster: Aşı koruyuculuk bitiş tarihi yaklaşanları listelemek.
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);
    
    // İster: Aynı tip aşının koruyuculuk süresi bitmemişse yeni aşı girilememesi kontrolü.
    // "Verilen koda sahip, verilen hayvana ait ve koruma bitiş tarihi bugünden sonra olan bir aşı var mı?"
    boolean existsByCodeAndAnimalIdAndProtectionFinishDateAfter(String code, Long animalId, LocalDate today);
}