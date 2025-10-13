package com.veto.vetManagement.Services;

import com.veto.vetManagement.Entities.Animal;
import com.veto.vetManagement.Entities.Appointment;
import com.veto.vetManagement.Entities.Doctor;
import com.veto.vetManagement.Util.AppointmentConflictException;
import com.veto.vetManagement.Util.NotFoundException;
import com.veto.vetManagement.DAO.AnimalRepository;
import com.veto.vetManagement.DAO.AppointmentRepository;
import com.veto.vetManagement.DAO.AvailableDateRepository;
import com.veto.vetManagement.DAO.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AnimalRepository animalRepository;
    private final AvailableDateRepository availableDateRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, AnimalRepository animalRepository, AvailableDateRepository availableDateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.animalRepository = animalRepository;
        this.availableDateRepository = availableDateRepository;
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment with ID " + id + " not found."));
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByDoctorAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new NotFoundException("Doctor with ID " + doctorId + " not found.");
        }
        return appointmentRepository.findByAppointmentDateBetweenAndDoctorId(startDate, endDate, doctorId);
    }

    public List<Appointment> findByAnimalAndDateRange(Long animalId, LocalDateTime startDate, LocalDateTime endDate) {
        if (!animalRepository.existsById(animalId)) {
            throw new NotFoundException("Animal with ID " + animalId + " not found.");
        }
        return appointmentRepository.findByAppointmentDateBetweenAndAnimalId(startDate, endDate, animalId);
    }

    public Appointment save(Appointment appointment, Long doctorId, Long animalId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor with ID " + doctorId + " not found."));
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with ID " + animalId + " not found."));

        // Doktorun o tarihte müsait günü var mı kontrolü
        LocalDate appointmentDay = appointment.getAppointmentDate().toLocalDate();
        if (availableDateRepository.findByDoctorIdAndAvailableDate(doctorId, appointmentDay).isEmpty()) {
            throw new AppointmentConflictException("The doctor is not working on this date!");
        }

        // Doktorun o saatte başka randevusu var mı kontrolü
        if (appointmentRepository.existsByDoctorIdAndAppointmentDate(doctorId, appointment.getAppointmentDate())) {
            throw new AppointmentConflictException("The doctor already has an appointment at this time.");
        }

        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        return appointmentRepository.save(appointment);
    }

    public Appointment update(Appointment appointment, Long doctorId, Long animalId) {
        getById(appointment.getId()); // Randevu var mı kontrolü
        
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor with ID " + doctorId + " not found."));
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal with ID " + animalId + " not found."));

        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);
        return appointmentRepository.save(appointment);
    }

    public void delete(Long id) {
        Appointment appointmentToDelete = getById(id);
        appointmentRepository.delete(appointmentToDelete);
    }
}