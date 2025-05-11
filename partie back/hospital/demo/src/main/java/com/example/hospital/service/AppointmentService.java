package com.example.hospital.service;

import com.example.hospital.dto.AppointmentDTO;
import com.example.hospital.entity.Appointment;
import com.example.hospital.enums.StatusAppointment;
import com.example.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Obtenir tous les rendez-vous
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtenir un rendez-vous par ID
    public AppointmentDTO findById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
        return convertToDTO(appointment);
    }

    // Enregistrer un rendez-vous (créer ou mettre à jour)
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        Appointment appointment = convertToEntity(appointmentDTO);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    // Mettre à jour un rendez-vous
    public AppointmentDTO update(Long id, AppointmentDTO appointmentDetails) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
        existingAppointment.setDateTime(appointmentDetails.getDateTime());
        existingAppointment.setStatus(appointmentDetails.getStatus());
        existingAppointment.setNotes(appointmentDetails.getNotes());
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return convertToDTO(updatedAppointment);
    }

    // Supprimer un rendez-vous par ID
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    // Trouver les rendez-vous par statut
    public List<AppointmentDTO> findByStatus(StatusAppointment status) {
        return appointmentRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convertir une entité Appointment en AppointmentDTO
    private AppointmentDTO convertToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    // Convertir un AppointmentDTO en entité Appointment
    private Appointment convertToEntity(AppointmentDTO appointmentDTO) {
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .dateTime(appointmentDTO.getDateTime())
                .status(appointmentDTO.getStatus())
                .notes(appointmentDTO.getNotes())
                .build();
    }
}
