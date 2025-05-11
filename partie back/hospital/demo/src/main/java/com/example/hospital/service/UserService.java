package com.example.hospital.service;

import com.example.hospital.dto.UserDTO;
import com.example.hospital.entity.User;
import com.example.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Trouver un utilisateur par email
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToDTO);
    }

    // Vérifier si un email existe déjà
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Créer ou mettre à jour un utilisateur
    public UserDTO save(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Supprimer un utilisateur par ID
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // Convertir une entité User en UserDTO
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    // Convertir un UserDTO en entité User
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        return user;
    }
}