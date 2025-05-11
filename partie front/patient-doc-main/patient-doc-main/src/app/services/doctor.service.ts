import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../models/appointment.model';


interface SubmitTreatmentRequest {
  appointmentId: number;
  diagnosis: string;
  prescription: string;
}

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private apiUrl = 'api/doctor';

  constructor(private http: HttpClient) { }

  getDoctorDashboard(): Observable<DoctorDashboard> {
 return this.http.get<DoctorDashboard>(`${this.apiUrl}/dashboard`);
  }

  getAppointments(status: string = 'ALL'): Observable<Appointment[]> {
 return this.http.get<Appointment[]>(`${this.apiUrl}/appointments`, { params: { status } });
  }

  validateAppointment(appointmentId: number): Observable<{ message: string }> {
 return this.http.put<{ message: string }>(`${this.apiUrl}/appointments/${appointmentId}/validate`, {});
  }

  getAppointmentInfo(appointmentId: number): Observable<Appointment> {
 return this.http.get<Appointment>(`${this.apiUrl}/appointments/${appointmentId}`);
  }

  submitTreatment(requestBody: SubmitTreatmentRequest): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.apiUrl}/treatments`, requestBody);
  }
}

// models used in DoctorService

export interface DoctorDashboard {
  name: string;
  appointmentsToday: number;
  pendingValidations: number;
  recentTreatments: RecentTreatment[];
}

export interface RecentTreatment {
  patientName: string;
  date: string;
  diagnosis: string;
}
export interface AppointmentInfo {
  patientName: string;
  dateTime: string;
  reason: string;
}