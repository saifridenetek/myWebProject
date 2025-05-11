package com.example.hospital.restController;

import com.example.hospital.dto.AppointmentDTO;
import com.example.hospital.enums.StatusAppointment;
import com.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentRestController {

    @Autowired
    private AppointmentService appointmentService;

    // Obtenir tous les rendez-vous
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    // Obtenir un rendez-vous par ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.findById(id);
        return ResponseEntity.ok(appointment);
    }

    // Créer un nouveau rendez-vous
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO createdAppointment = appointmentService.save(appointmentDTO);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    // Mettre à jour un rendez-vous
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentDTO appointmentDetails) {
        AppointmentDTO updatedAppointment = appointmentService.update(id, appointmentDetails);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Supprimer un rendez-vous
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Trouver les rendez-vous par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByStatus(@PathVariable StatusAppointment status) {
        List<AppointmentDTO> appointments = appointmentService.findByStatus(status);
        return ResponseEntity.ok(appointments);
    }
}