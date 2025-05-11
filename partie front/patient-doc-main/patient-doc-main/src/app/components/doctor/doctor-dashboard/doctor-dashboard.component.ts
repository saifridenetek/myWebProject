import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DoctorService, RecentTreatment } from '../../../services/doctor.service';


@Component({
  selector: 'app-doctor-dashboard',
  imports: [CommonModule],
  templateUrl: './doctor-dashboard.component.html',
  styleUrls: ['./doctor-dashboard.component.css']
})
export class DoctorDashboardComponent implements OnInit {
  welcomeMessage: string = 'Welcome, Doctor!';
  totalAppointmentsToday: number = 0;
  pendingValidations: number = 0;
  recentTreatments: RecentTreatment[] = []; // Replace 'any' with your treatment model

  constructor(private router: Router, private doctorService: DoctorService) { }

  ngOnInit(): void {
    this.doctorService.getDoctorDashboard().subscribe({
      next: (data) => {
        this.welcomeMessage = `Welcome, Dr. ${data.name}!`; //name: string;
        this.totalAppointmentsToday = data.appointmentsToday;// appointmentsToday: number;
        this.pendingValidations = data.pendingValidations; //pendingValidations: number;
        this.recentTreatments = data.recentTreatments; // recentTreatments: RecentTreatment[];
      },
      error: (error) => {
        console.error('Error fetching doctor dashboard data:', error);
        // Handle error, e.g., show an error message
      }
    });
  }

  viewAppointmentsList(): void {
    // Navigate to appointments page
    this.router.navigate(['/doctor/appointments']);
  }

  viewPatient(): void {
    // Navigate to patient treatment files page
    this.router.navigate(['/doctor/patients']);
  }

  logout(): void {
    // Handle logout logic (clear session, navigate to login)
    console.log('Doctor logged out');
    this.router.navigate(['/login']);
  }
}