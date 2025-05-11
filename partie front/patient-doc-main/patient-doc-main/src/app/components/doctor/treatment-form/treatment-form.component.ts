import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Appointment } from '../../../models/appointment.model';
import { DoctorService } from '../../../services/doctor.service';

@Component({
  selector: 'app-treatment-form',
  imports: [ReactiveFormsModule,CommonModule, RouterModule],
  templateUrl: './treatment-form.component.html',
  styleUrls: ['./treatment-form.component.css']
})
export class TreatmentFormComponent implements OnInit {

  appointmentId: number | null = null;
  appointmentDetails: Appointment | undefined;
  treatmentForm!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private doctorService: DoctorService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('appointmentId');
    if (id) {
      this.appointmentId = +id; // Convert string ID from route to number
    }


    this.treatmentForm = this.fb.group({
      diagnosis: ['', Validators.required],
      prescription: ['', Validators.required]
    });

    if (this.appointmentId) {
      // Fetch appointment details using the ID
      this.doctorService.getAppointmentInfo(this.appointmentId).subscribe(
        (data: Appointment) => {
          this.appointmentDetails = data;
        },
        (error) => {
          console.error('Error fetching appointment details:', error);
          // Handle error, e.g., show an error message
        }
      );
    }
  }

  onSubmit(): void {
    console.log('this.treatmentForm.valid', this.treatmentForm.valid)
    console.log('this.appointmentId', this.appointmentId)
    if (this.treatmentForm.valid && this.appointmentId) {
      console.log('Treatment Form Submitted:', this.treatmentForm.value)
      const treatmentData = {
        appointmentId: this.appointmentId,
        diagnosis: this.treatmentForm.value.diagnosis,
        prescription: this.treatmentForm.value.prescription
      };
      console.log('hello')
      this.doctorService.submitTreatment(treatmentData).subscribe(
        (response) => {
          console.log('Treatment file submitted successfully:', response);
          // Handle success, e.g., show a success message and navigate back
          // Redirect to doctor dashboard or confirmation page
          this.router.navigate(['/doctor/dashboard']);
        },
        (error) => {
          console.error('Error submitting treatment file:', error);
          // Handle error, e.g., show an error message
        }
      );
    }
  }

  onCancel(): void {
    this.router.navigate(['/doctor/appointments']);
  }

}