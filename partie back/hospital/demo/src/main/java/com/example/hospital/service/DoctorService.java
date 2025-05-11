package com.example.hospital.service;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.enums.Specialist;
import com.example.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Obtenir tous les médecins
    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtenir un médecin par ID
    public DoctorDTO findById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
        return convertToDTO(doctor);
    }

    // Enregistrer un médecin (créer ou mettre à jour)
    public DoctorDTO save(DoctorDTO doctorDTO) {
        Doctor doctor = convertToEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDTO(savedDoctor);
    }

    // Mettre à jour un médecin
    public DoctorDTO update(Long id, DoctorDTO doctorDetails) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
        existingDoctor.setName(doctorDetails.getName());
        existingDoctor.setSpecialist(doctorDetails.getSpecialist());
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return convertToDTO(updatedDoctor);
    }

    // Supprimer un médecin par ID
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    // Trouver les médecins par spécialité
    public List<DoctorDTO> findBySpecialty(Specialist specialty) {
        return doctorRepository.findBySpecialist(specialty).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Trouver les médecins par département
    public List<DoctorDTO> findByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convertir une entité Doctor en DoctorDTO
    private DoctorDTO convertToDTO(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialist(doctor.getSpecialist())
                .departmentId(doctor.getDepartment() != null ? doctor.getDepartment().getId() : null)
                .patientIds(doctor.getPatients().stream().map(patient -> patient.getId()).collect(Collectors.toList()))
                .appointmentIds(doctor.getAppointments().stream().map(appointment -> appointment.getId()).collect(Collectors.toList()))
                .medicalRecordIds(doctor.getMedicalRecords().stream().map(record -> record.getId()).collect(Collectors.toList()))
                .build();
    }

    // Convertir un DoctorDTO en entité Doctor
    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        return Doctor.builder()
                .id(doctorDTO.getId())
                .name(doctorDTO.getName())
                .specialist(doctorDTO.getSpecialist())
                .build();
    }
}