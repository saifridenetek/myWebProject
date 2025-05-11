package com.example.hospital.repository;

import com.example.hospital.entity.Doctor;
import com.example.hospital.enums.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Trouver les médecins par spécialité
    List<Doctor> findBySpecialist(Specialist specialist);

    // Trouver les médecins d'un département spécifique
    List<Doctor> findByDepartmentId(Long departmentId);
}