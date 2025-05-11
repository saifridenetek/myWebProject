package com.example.hospital.service;

import com.example.hospital.dto.MedicalRecordDTO;
import com.example.hospital.entity.MedicalRecord;
import com.example.hospital.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Obtenir tous les dossiers médicaux
    public List<MedicalRecordDTO> findAll() {
        return medicalRecordRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtenir un dossier médical par ID
    public MedicalRecordDTO findById(Long id) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id " + id));
        return convertToDTO(medicalRecord);
    }

    // Enregistrer un dossier médical (créer ou mettre à jour)
    public MedicalRecordDTO save(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = convertToEntity(medicalRecordDTO);
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        return convertToDTO(savedRecord);
    }

    // Mettre à jour un dossier médical
    public MedicalRecordDTO update(Long id, MedicalRecordDTO recordDetails) {
        MedicalRecord existingRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id " + id));
        existingRecord.setDiagnosis(recordDetails.getDiagnosis());
        existingRecord.setTreatmentDetails(recordDetails.getTreatmentDetails());
        existingRecord.setPrescription(recordDetails.getPrescription());
        existingRecord.setTreatmentDate(recordDetails.getTreatmentDate());
        MedicalRecord updatedRecord = medicalRecordRepository.save(existingRecord);
        return convertToDTO(updatedRecord);
    }

    // Supprimer un dossier médical par ID
    public void delete(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    // Convertir une entité MedicalRecord en MedicalRecordDTO
    private MedicalRecordDTO convertToDTO(MedicalRecord medicalRecord) {
        return MedicalRecordDTO.builder()
                .id(medicalRecord.getId())
                .treatmentDetails(medicalRecord.getTreatmentDetails())
                .diagnosis(medicalRecord.getDiagnosis())
                .prescription(medicalRecord.getPrescription())
                .treatmentDate(medicalRecord.getTreatmentDate())
                .doctorId(medicalRecord.getDoctor().getId())
                .patientId(medicalRecord.getPatient().getId())
                .createdAt(medicalRecord.getCreatedAt())
                .updatedAt(medicalRecord.getUpdatedAt())
                .build();
    }

    // Convertir un MedicalRecordDTO en entité MedicalRecord
    private MedicalRecord convertToEntity(MedicalRecordDTO medicalRecordDTO) {
        return MedicalRecord.builder()
                .id(medicalRecordDTO.getId())
                .treatmentDetails(medicalRecordDTO.getTreatmentDetails())
                .diagnosis(medicalRecordDTO.getDiagnosis())
                .prescription(medicalRecordDTO.getPrescription())
                .treatmentDate(medicalRecordDTO.getTreatmentDate())
                .build();
    }
}