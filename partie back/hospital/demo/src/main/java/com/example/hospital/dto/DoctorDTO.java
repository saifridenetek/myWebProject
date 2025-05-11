package com.example.hospital.dto;

import com.example.hospital.enums.Specialist;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private Long id;
    private String name;
    private Specialist specialist;
    private Long departmentId;
    private List<Long> patientIds;
    private List<Long> appointmentIds;
    private List<Long> medicalRecordIds;
}