package com.veto.vetManagement.DAO;

import com.veto.vetManagement.Entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    // İster: Hayvanlar isme göre filtrelenecek.
    List<Animal> findByNameContainingIgnoreCase(String name);

    // İster: Hayvan sahibinin sistemde kayıtlı tüm hayvanlarını görüntülemek.
    List<Animal> findByCustomerId(Long customerId);
}