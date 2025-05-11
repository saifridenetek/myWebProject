import { Component, OnInit } from '@angular/core';
import { PatientService } from '../../../services/patient.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Appointment } from '../../../models/appointment.model';

@Component({
  selector: 'app-patient-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './patient-dashboard.component.html',
  styleUrl: './patient-dashboard.component.css'
})
export class PatientDashboardComponent implements OnInit {
  patientName: string = '';
  upcomingAppointments: Appointment[] = [];

  constructor(private patientService: PatientService, private router: Router) {}

  ngOnInit(): void {
    this.patientService.getPatientDashboard().subscribe(data => {
      this.patientName = data.name;
      this.upcomingAppointments = data.upcomingAppointments;
    });
  }

  onBookAppointment() {
    this.router.navigate(['/patient/appointments/new']);
  }

  onViewTreatments() {
    this.router.navigate(['/patient/treatments']);
  }

  onLogout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}
