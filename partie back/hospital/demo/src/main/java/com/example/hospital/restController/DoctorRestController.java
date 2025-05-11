package com.example.hospital.restController;

import com.example.hospital.dto.DoctorDTO;
import com.example.hospital.enums.Specialist;
import com.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorRestController {

    @Autowired
    private DoctorService doctorService;

    // Obtenir tous les médecins
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.findAll();
        return ResponseEntity.ok(doctors);
    }

    // Obtenir un médecin par ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.findById(id);
        return ResponseEntity.ok(doctor);
    }

    // Créer un nouveau médecin
    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createdDoctor = doctorService.save(doctorDTO);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    // Mettre à jour un médecin
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDTO doctorDetails) {
        DoctorDTO updatedDoctor = doctorService.update(id, doctorDetails);
        return ResponseEntity.ok(updatedDoctor);
    }

    // Supprimer un médecin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Trouver les médecins par spécialité
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@PathVariable Specialist specialty) {
        List<DoctorDTO> doctors = doctorService.findBySpecialty(specialty);
        return ResponseEntity.ok(doctors);
    }

    // Trouver les médecins par département
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByDepartment(@PathVariable Long departmentId) {
        List<DoctorDTO> doctors = doctorService.findByDepartment(departmentId);
        return ResponseEntity.ok(doctors);
    }
}