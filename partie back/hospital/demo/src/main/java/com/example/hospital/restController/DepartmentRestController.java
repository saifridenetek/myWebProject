package com.example.hospital.restController;

import com.example.hospital.dto.DepartmentDTO;
import com.example.hospital.enums.Specialist;
import com.example.hospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    // Obtenir tous les départements
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }

    // Obtenir un département par ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentDTO department = departmentService.findById(id);
        return ResponseEntity.ok(department);
    }

    // Créer un nouveau département
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartment = departmentService.save(departmentDTO);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    // Mettre à jour un département
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentDTO departmentDetails) {
        DepartmentDTO updatedDepartment = departmentService.update(id, departmentDetails);
        return ResponseEntity.ok(updatedDepartment);
    }

    // Supprimer un département
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Trouver un département par spécialité
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<Optional<DepartmentDTO>> getDepartmentBySpecialty(@PathVariable Specialist specialty) {
        Optional<DepartmentDTO> department = departmentService.findBySpecialty(specialty);
        return ResponseEntity.of(Optional.ofNullable(department));
    }
}