package com.example.hospital.repository;

import com.example.hospital.entity.Department;
import com.example.hospital.enums.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Recherche par spécialité
    Optional<Department> findByNameOfDepartment(Specialist specialist);

}