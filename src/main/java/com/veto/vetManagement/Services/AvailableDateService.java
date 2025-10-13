package com.veto.vetManagement.Services;

import com.veto.vetManagement.Entities.AvailableDate;
import com.veto.vetManagement.Entities.Doctor;
import com.veto.vetManagement.Util.AlreadyExistsException;
import com.veto.vetManagement.Util.NotFoundException;
import com.veto.vetManagement.DAO.AvailableDateRepository;
import com.veto.vetManagement.DAO.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableDateService {

    private final AvailableDateRepository availableDateRepository;
    private final DoctorRepository doctorRepository;

    public AvailableDateService(AvailableDateRepository availableDateRepository, DoctorRepository doctorRepository) {
        this.availableDateRepository = availableDateRepository;
        this.doctorRepository = doctorRepository;
    }

    public AvailableDate getById(Long id) {
        return availableDateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AvailableDate with ID " + id + " not found."));
    }

    public List<AvailableDate> findAll() {
        return availableDateRepository.findAll();
    }

    public AvailableDate save(AvailableDate availableDate, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor with ID " + doctorId + " not found."));
        
        if (availableDateRepository.findByDoctorIdAndAvailableDate(doctorId, availableDate.getAvailableDate()).isPresent()) {
            throw new AlreadyExistsException("This doctor is already available on this date.");
        }
        
        availableDate.setDoctor(doctor);
        return availableDateRepository.save(availableDate);
    }

    public AvailableDate update(AvailableDate availableDate, Long doctorId) {
        getById(availableDate.getId());
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor with ID " + doctorId + " not found."));
        availableDate.setDoctor(doctor);
        return availableDateRepository.save(availableDate);
    }

    public void delete(Long id) {
        AvailableDate dateToDelete = getById(id);
        availableDateRepository.delete(dateToDelete);
    }
}
