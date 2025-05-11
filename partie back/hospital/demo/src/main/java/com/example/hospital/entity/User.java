package com.example.hospital.entity;

import com.example.hospital.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // Ajout de cette annotation
@Table(name = "users") // Optionnel : pour spécifier un nom de table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;
}