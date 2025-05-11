package com.example.hospital.restController;

import com.example.hospital.dto.MedicalRecordDTO;
import com.example.hospital.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin(origins = "http://localhost:4200")
public class MedicalRecordRestController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    // Obtenir tous les dossiers médicaux
    @GetMapping
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        List<MedicalRecordDTO> records = medicalRecordService.findAll();
        return ResponseEntity.ok(records);
    }

    // Obtenir un dossier médical par ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecordDTO record = medicalRecordService.findById(id);
        return ResponseEntity.ok(record);
    }

    // Créer un nouveau dossier médical
    @PostMapping
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO createdRecord = medicalRecordService.save(medicalRecordDTO);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    // Mettre à jour un dossier médical
    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
            @PathVariable Long id,
            @RequestBody MedicalRecordDTO recordDetails) {
        MedicalRecordDTO updatedRecord = medicalRecordService.update(id, recordDetails);
        return ResponseEntity.ok(updatedRecord);
    }

    // Supprimer un dossier médical
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}