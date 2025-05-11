package com.example.hospital.dto;

import com.example.hospital.enums.TreatmentOutcome;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDTO {
    private Long id;
    private String treatmentDetails;
    private String diagnosis;
    private String prescription;
    private LocalDate treatmentDate;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}