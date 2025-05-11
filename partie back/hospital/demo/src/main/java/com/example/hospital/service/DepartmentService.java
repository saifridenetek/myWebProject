package com.example.hospital.service;

import com.example.hospital.dto.DepartmentDTO;
import com.example.hospital.entity.Department;
import com.example.hospital.enums.Specialist;
import com.example.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Obtenir tous les départements
    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtenir un département par ID
    public DepartmentDTO findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
        return convertToDTO(department);
    }

    // Enregistrer un département (créer ou mettre à jour)
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    // Mettre à jour un département
    public DepartmentDTO update(Long id, DepartmentDTO departmentDetails) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id " + id));
        existingDepartment.setNameOfDepartment(departmentDetails.getNameOfDepartment());
        existingDepartment.setDescription(departmentDetails.getDescription());
        existingDepartment.setLocation(departmentDetails.getLocation());
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return convertToDTO(updatedDepartment);
    }

    // Supprimer un département par ID
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    // Trouver un département par spécialité
    public Optional<DepartmentDTO> findBySpecialty(Specialist specialty) {
        return departmentRepository.findByNameOfDepartment(specialty)
                .map(this::convertToDTO);
    }

    // Convertir une entité Department en DepartmentDTO
    private DepartmentDTO convertToDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .nameOfDepartment(department.getNameOfDepartment())
                .description(department.getDescription())
                .location(department.getLocation())
                .doctorIds(department.getDoctors().stream()
                        .map(doctor -> doctor.getId())
                        .collect(Collectors.toList()))
                .build();
    }

    // Convertir un DepartmentDTO en entité Department
    private Department convertToEntity(DepartmentDTO departmentDTO) {
        return Department.builder()
                .id(departmentDTO.getId())
                .nameOfDepartment(departmentDTO.getNameOfDepartment())
                .description(departmentDTO.getDescription())
                .location(departmentDTO.getLocation())
                .build();
    }
}