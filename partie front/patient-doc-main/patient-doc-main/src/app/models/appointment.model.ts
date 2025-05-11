export interface Appointment {
  id?: number;
  dateTime: string;
  doctorName?: string;
  reason: string;
  status?: string;
  patientName?: string;
}