import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Doctor } from '../../../models/doctor.model';
import { PatientService } from '../../../services/patient.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-appointment-form',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './appointment-form.component.html',
  styleUrls: ['./appointment-form.component.css']
})
export class AppointmentFormComponent implements OnInit {
  appointmentForm: FormGroup;
  doctors: Doctor[] = [];

  constructor(
    private fb: FormBuilder,
    private patientService: PatientService,
    private router: Router
  ) {
    this.appointmentForm = this.fb.group({
      doctorId: ['', Validators.required],
      appointmentDate: ['', Validators.required],
      appointmentTime: ['', Validators.required],
      reason: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.patientService.getDoctors().subscribe(
      (data: Doctor[]) => {
        this.doctors = data;
      },
      (error) => {
        console.error('Error fetching doctors:', error);
      }
    );
  }

  onSubmit(): void {
    console.log('Form submitted!');
    if (this.appointmentForm.valid) {
      const appointmentData = {
        ...this.appointmentForm.value,
        appointmentDateTime: `${this.appointmentForm.value.appointmentDate}T${this.appointmentForm.value.appointmentTime}`
      };
      this.patientService.createAppointment(appointmentData).subscribe(
        (response) => {
          console.log('Appointment created:', response);
          // Redirect to patient dashboard or confirmation page
          this.router.navigate(['/patient/dashboard']);
        },
        (error) => {
          console.error('Error creating appointment:', error);
          // Handle error, show error message
        }
      );
    }
  }

  onCancel(): void {
    this.router.navigate(['/patient/dashboard']);
  }
}