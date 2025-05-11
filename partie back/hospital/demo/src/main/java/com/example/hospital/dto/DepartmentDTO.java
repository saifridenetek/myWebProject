package com.example.hospital.dto;

import com.example.hospital.enums.Specialist;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;
    private Specialist nameOfDepartment;
    private String description;
    private String location;
    private List<Long> doctorIds;
}