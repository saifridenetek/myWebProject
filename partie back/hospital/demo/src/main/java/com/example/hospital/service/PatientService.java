package com.example.hospital.service;

import com.example.hospital.dto.PatientDTO;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Obtenir tous les patients
    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtenir un patient par ID
    public PatientDTO findById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
        return convertToDTO(patient);
    }

    // Enregistrer un patient (créer ou mettre à jour)
    public PatientDTO save(PatientDTO patientDTO) {
        Patient patient = convertToEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return convertToDTO(savedPatient);
    }

    // Mettre à jour un patient
    public PatientDTO update(Long id, PatientDTO patientDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
        existingPatient.setName(patientDTO.getName());
        existingPatient.setEmail(patientDTO.getEmail());
        Patient updatedPatient = patientRepository.save(existingPatient);
        return convertToDTO(updatedPatient);
    }

    // Supprimer un patient par ID
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    // Rechercher des patients par nom
    public List<PatientDTO> searchByName(String name) {
        return patientRepository.findByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convertir une entité Patient en PatientDTO
    private PatientDTO convertToDTO(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }

    // Convertir un PatientDTO en entité Patient
    private Patient convertToEntity(PatientDTO patientDTO) {
        return Patient.builder()
                .id(patientDTO.getId())
                .name(patientDTO.getName())
                .email(patientDTO.getEmail())
                .build();
    }
}