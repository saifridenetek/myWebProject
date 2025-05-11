import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Appointment } from '../../../models/appointment.model';
import { DoctorService } from '../../../services/doctor.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-appointments-list',
  imports: [CommonModule, RouterModule, FormsModule ],
  templateUrl: './appointments-list.component.html',
  styleUrls: ['./appointments-list.component.css']
})
export class AppointmentsListComponent implements OnInit {
  currentPage: number = 1;
  itemsPerPage: number = 5;

  appointments: Appointment[] = [];
  filteredAppointments: Appointment[] = [];
  paginatedAppointments: Appointment[] = [];
  selectedStatus: string = 'All';

  constructor(private doctorService: DoctorService, private router: Router) {}

  ngOnInit(): void {
    this.fetchAppointments();
  }

  fetchAppointments(): void {
    this.doctorService.getAppointments().subscribe(
      (data: Appointment[]) => {
        this.appointments = data;
        this.applyFilter(); // Apply filter and pagination after data arrives
      },
      (error) => {
        console.error('Error fetching appointments:', error);
      }
    );
  }

  applyFilter(): void {
    if (this.selectedStatus === 'All') {
      this.filteredAppointments = [...this.appointments];
    } else {
      this.filteredAppointments = this.appointments.filter(
        appointment => appointment.status === this.selectedStatus
      );
    }
    this.currentPage = 1;
    this.updatePagination();
  }

  updatePagination(): void {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    const end = start + this.itemsPerPage;
    this.paginatedAppointments = this.filteredAppointments.slice(start, end);
  }

  nextPage(): void {
    if ((this.currentPage * this.itemsPerPage) < this.filteredAppointments.length) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  validateAppointment(appointmentId: number | undefined): void {
    if (appointmentId !== undefined) {
      this.doctorService.validateAppointment(appointmentId).subscribe(
        (response) => {
          console.log('Appointment validated:', response);
          this.fetchAppointments();
        },
        (error) => {
          console.error('Error validating appointment:', error);
        }
      );
    }
  }

  fillTreatmentFile(appointmentId: number | undefined): void {
    if (appointmentId !== undefined) {
      this.router.navigate(['/doctor/treatments/new'], {
        queryParams: { appointmentId }
      });
    }
  }
}
