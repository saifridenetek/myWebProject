package com.example.hospital.restController;

import com.example.hospital.dto.PatientDTO;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientRestController {

    @Autowired
    private PatientService patientService;

    // Obtenir tous les patients
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientService.findAll();
        return ResponseEntity.ok(patients);
    }

    // Obtenir un patient par ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patient = patientService.findById(id);
        return ResponseEntity.ok(patient);
    }

    // Créer un nouveau patient
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.save(patientDTO);
        return ResponseEntity.ok(createdPatient);
    }

    // Mettre à jour un patient existant
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.update(id, patientDTO);
        return ResponseEntity.ok(updatedPatient);
    }

    // Supprimer un patient par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Rechercher des patients par nom
    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatientsByName(@RequestParam String name) {
        List<PatientDTO> patients = patientService.searchByName(name);
        return ResponseEntity.ok(patients);
    }
}