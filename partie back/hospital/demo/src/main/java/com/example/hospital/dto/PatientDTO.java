package com.example.hospital.dto;

import com.example.hospital.enums.Sex;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private Long id;
    private String name;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}