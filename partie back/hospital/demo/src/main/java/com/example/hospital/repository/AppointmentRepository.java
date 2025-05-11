package com.example.hospital.repository;

import com.example.hospital.entity.*;
import com.example.hospital.enums.StatusAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Rendez-vous par statut
    List<Appointment> findByStatus(StatusAppointment status);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, StatusAppointment status);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);

}