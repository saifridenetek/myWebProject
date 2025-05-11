package com.example.hospital.entity;

import com.example.hospital.enums.TreatmentOutcome;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Les détails du traitement sont obligatoires")
    @Column(columnDefinition = "TEXT")
    private String treatmentDetails;

    @NotBlank(message = "Le diagnostic est obligatoire")
    @Size(max = 500)
    private String diagnosis;

    @NotBlank(message = "La prescription est obligatoire")
    @Size(max = 1000)
    private String prescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TreatmentOutcome treatmentOutcome;

    @PastOrPresent
    private LocalDate treatmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference("doctor-medicalrecords")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference("patient-medicalrecords")
    private Patient patient;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
