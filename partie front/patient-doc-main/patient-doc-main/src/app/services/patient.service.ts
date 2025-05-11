import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor } from '../models/doctor.model';
import { Appointment } from '../models/appointment.model';
import { Treatment } from '../models/treatment.model';
import { PatientDashboard } from '../models/patient-dashboard.model';

export interface CreateAppointmentRequest {
  patientId: number;
  doctorId: number;
  dateTime: string;
  reason: string;
}

@Injectable({
  providedIn: 'root'
})
export class PatientService {
 private apiUrl = 'api/patient';

  constructor(private http: HttpClient) { }

  getPatientDashboard(): Observable<PatientDashboard> {
 return this.http.get<PatientDashboard>(`${this.apiUrl}/dashboard`);
  }

  getDoctors(): Observable<Doctor[]> {
 return this.http.get<Doctor[]>(`${this.apiUrl}/doctors`);
  }

  createAppointment(appointment: CreateAppointmentRequest): Observable<{ message: string, appointmentId: number }> {
 return this.http.post<{ message: string, appointmentId: number }>(`${this.apiUrl}/appointments`, appointment);
  }

  getPatientTreatments(): Observable<Treatment[]> {
 return this.http.get<Treatment[]>(`${this.apiUrl}/treatments`);
  }
}