package com.example.hospital.dto;

import com.example.hospital.enums.StatusAppointment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private LocalDateTime dateTime;
    private StatusAppointment status;
    private String notes;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}