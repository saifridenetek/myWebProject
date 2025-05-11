const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3001;
app.use(cors());
app.use(bodyParser.json());

const readData = (file) => JSON.parse(fs.readFileSync(path.join(__dirname, 'data', file)));
const writeData = (file, data) => fs.writeFileSync(path.join(__dirname, 'data', file), JSON.stringify(data, null, 2));

// -------------------- AUTH --------------------

// 🔐 Login
app.post('/api/auth/login', (req, res) => {
  const users = readData('users.json');
  const { email, password } = req.body;
  const user = users.find(u => u.email === email && u.password === password);
  if (user) {
    res.json({ token: user.token, role: user.role, name: user.name });
  } else {
    res.status(401).json({ message: 'Invalid credentials' });
  }
});

// 📝 Registration
app.post('/api/auth/register', (req, res) => {
  const users = readData('users.json');
  const { email, password, name, role } = req.body;

  if (users.some(u => u.email === email)) {
    return res.status(400).json({ message: 'User already exists' });
  }

  const newUser = {
    email,
    password,
    name,
    role,
    token: `mock-${role.toLowerCase()}-${Date.now()}`
  };
  users.push(newUser);
  writeData('users.json', users);

  res.json({ message: 'User registered successfully.' });
});

// -------------------- PATIENT --------------------

// 🏠 Patient Dashboard
app.get('/api/patient/dashboard', (req, res) => {
  const users = readData('users.json');
  const appointments = readData('appointments.json');
  const patient = users.find(u => u.role === 'PATIENT');

  const upcomingAppointments = appointments.filter(a => a.patientEmail === patient.email);

  res.json({
    name: patient.name,
    upcomingAppointments
  });
});

// 📅 List of doctors
app.get('/api/doctors', (req, res) => {
  const doctors = readData('doctors.json');
  res.json(doctors);
});

// 📅 Book Appointment
app.post('/api/patient/appointments', (req, res) => {
  const appointments = readData('appointments.json');
  const newAppointment = {
    id: Date.now(),
    ...req.body,
    status: 'PENDING'
  };
  appointments.push(newAppointment);
  writeData('appointments.json', appointments);

  res.json({
    message: 'Appointment booked successfully.',
    appointmentId: newAppointment.id
  });
});

// 📋 Patient Treatment Files
app.get('/api/patient/treatments', (req, res) => {
  const { patientEmail } = req.query;
  const treatments = readData('treatments.json');
  const doctors = readData('doctors.json');

  const patientTreatments = treatments
    .filter(t => t.patientEmail === patientEmail)
    .map(t => ({
      appointmentDate: t.appointmentDate,
      doctorName: doctors.find(d => d.id === t.doctorId)?.name || 'Unknown',
      diagnosis: t.diagnosis,
      prescription: t.prescription
    }));

  res.json(patientTreatments);
});

// -------------------- DOCTOR --------------------

// 🏠 Doctor Dashboard
app.get('/api/doctor/dashboard', (req, res) => {
  const users = readData('users.json');
  const doctor = users.find(u => u.role === 'DOCTOR');
  const appointments = readData('appointments.json');
  const treatments = readData('treatments.json');

  const today = new Date().toISOString().split('T')[0];
  const todaysAppointments = appointments.filter(a =>
    a.doctorId === 1 && a.dateTime.startsWith(today)
  );
  const pending = todaysAppointments.filter(a => a.status === 'PENDING');
  const recentTreatments = treatments.slice(-3).map(t => ({
    patientName: users.find(u => u.email === t.patientEmail)?.name,
    date: t.appointmentDate,
    diagnosis: t.diagnosis
  }));

  res.json({
    name: doctor.name,
    appointmentsToday: todaysAppointments.length,
    pendingValidations: pending.length,
    recentTreatments
  });
});

// 📅 Doctor Appointments List
app.get('/api/doctor/appointments', (req, res) => {
  const appointments = readData('appointments.json');
  const users = readData('users.json');

  const list = appointments
    .filter(a => a.doctorId === 1)
    .map(a => ({
      id: a.id,
      patientName: users.find(u => u.email === a.patientEmail)?.name || 'Unknown',
      dateTime: a.dateTime,
      reason: a.reason,
      status: a.status
    }));

  res.json(list);
});

// ✅ Validate Appointment
app.put('/api/doctor/appointments/:id/validate', (req, res) => {
  const appointments = readData('appointments.json');
  const id = parseInt(req.params.id);

  const index = appointments.findIndex(a => a.id === id);
  if (index === -1) return res.status(404).json({ message: 'Appointment not found' });

  appointments[index].status = 'VALIDATED';
  writeData('appointments.json', appointments);

  res.json({ message: 'Appointment validated.' });
});

// 🧾 Get Appointment Info
app.get('/api/doctor/appointments/:id', (req, res) => {
  const id = parseInt(req.params.id);
  const appointments = readData('appointments.json');
  const appointment = appointments.find(a => a.id === id);

  if (!appointment) return res.status(404).json({ message: 'Not found' });

  const users = readData('users.json');
  const patient = users.find(u => u.email === appointment.patientEmail);

  res.json({
    patientName: patient?.name || 'Unknown',
    dateTime: appointment.dateTime,
    reason: appointment.reason
  });
});

// 🧾 Submit Treatment File
app.post('/api/doctor/treatments', (req, res) => {
  const treatments = readData('treatments.json');
  const newTreatment = {
    id: Date.now(),
    ...req.body
  };
  treatments.push(newTreatment);
  writeData('treatments.json', treatments);

  res.json({ message: 'Treatment file saved successfully.' });
});

app.listen(PORT, () => console.log(`Mock server running at http://localhost:${PORT}`));
