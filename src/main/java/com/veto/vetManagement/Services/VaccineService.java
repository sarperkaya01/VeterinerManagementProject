package com.veto.vetManagement.Services;

import com.veto.vetManagement.Entities.Animal;
import com.veto.vetManagement.Entities.Vaccine;
import com.veto.vetManagement.Util.AlreadyExistsException;
import com.veto.vetManagement.Util.NotFoundException;
import com.veto.vetManagement.DAO.AnimalRepository;
import com.veto.vetManagement.DAO.VaccineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final AnimalRepository animalRepository;

    public VaccineService(VaccineRepository vaccineRepository, AnimalRepository animalRepository) {
        this.vaccineRepository = vaccineRepository;
        this.animalRepository = animalRepository;
    }

    public Vaccine getById(Long id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vaccine with ID " + id + " not found."));
    }

    public List<Vaccine> findAll() {
        return vaccineRepository.findAll();
    }

    public List<Vaccine> findByAnimalId(Long animalId) {
        if (!animalRepository.existsById(animalId)) {
            throw new NotFoundException("Animal with ID " + animalId + " not found.");
        }
        return vaccineRepository.findByAnimalId(animalId);
    }

    public List<Vaccine> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return vaccineRepository.findByProtectionFinishDateBetween(startDate, endDate);
    }

    public Vaccine save(Vaccine vaccine, Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with ID " + animalId + " not found."));

        // Aşı koruyuculuk bitiş tarihi gelmemiş ise sisteme yeni aşı girilemez.
        if (vaccineRepository.existsByCodeAndAnimalIdAndProtectionFinishDateAfter(
                vaccine.getCode(), animalId, LocalDate.now())) {
            throw new AlreadyExistsException("This type of vaccine is still active for this animal.");
        }

        vaccine.setAnimal(animal);
        return vaccineRepository.save(vaccine);
    }

    public Vaccine update(Vaccine vaccine, Long animalId) {
        getById(vaccine.getId());
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with ID " + animalId + " not found."));
        vaccine.setAnimal(animal);
        return vaccineRepository.save(vaccine);
    }

    public void delete(Long id) {
        Vaccine vaccineToDelete = getById(id);
        vaccineRepository.delete(vaccineToDelete);
    }
}