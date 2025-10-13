package com.veto.vetManagement.Services;

import com.veto.vetManagement.Entities.Doctor;
import com.veto.vetManagement.Util.AlreadyExistsException;
import com.veto.vetManagement.Util.NotFoundException;
import com.veto.vetManagement.DAO.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor with ID " + id + " not found."));
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor save(Doctor doctor) {
        if (doctorRepository.existsByPhone(doctor.getPhone()) || doctorRepository.existsByMail(doctor.getMail())) {
            throw new AlreadyExistsException("A doctor with this phone number or email already exists.");
        }
        return doctorRepository.save(doctor);
    }

    public Doctor update(Doctor doctor) {
        getById(doctor.getId());
        return doctorRepository.save(doctor);
    }

    public void delete(Long id) {
        Doctor doctorToDelete = getById(id);
        doctorRepository.delete(doctorToDelete);
    }
}
