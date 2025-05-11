const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(bodyParser.json());

const PORT = 3000;

// Mock Data
const users = [
  {
    email: 'john@example.com',
    password: 'securePass',
    role: 'PATIENT',
    name: 'John Doe',
    token: 'mock-patient-jwt'
  },
  {
    email: 'jane@example.com',
    password: 'pass1234',
    role: 'DOCTOR',
    name: 'Dr. Jane Smith',
    token: 'mock-doctor-jwt'
  }
];

// 🔐 Login
app.post('/api/auth/login', (req, res) => {
    console.log("hello")
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
  const { email, password, name, role } = req.body;
  users.push({ email, password, name, role, token: `mock-${role.toLowerCase()}-jwt` });
  res.json({ message: 'User registered successfully.' });
});

// 🏠 Patient Dashboard
app.get('/api/patient/dashboard', (req, res) => {
  res.json({
    name: 'John Doe',
    upcomingAppointments: [
      {
        id: 12,
        dateTime: '2025-05-10T15:30',
        doctorName: 'Dr. Smith',
        reason: 'Routine Checkup'
      }
    ]
  });
});

// 📅 Doctors list
app.get('/api/doctors', (req, res) => {
  res.json([
    { id: 1, name: 'Dr. Jane Smith' },
    { id: 2, name: 'Dr. Alan Brown' }
  ]);
});

// 📅 Create appointment
app.post('/api/patient/appointments', (req, res) => {
  res.json({
    message: 'Appointment booked successfully.',
    appointmentId: 101
  });
});

// 📋 Treatments list
app.get('/api/patient/treatments', (req, res) => {
  res.json([
    {
      appointmentDate: '2025-04-10',
      doctorName: 'Dr. Jane Smith',
      diagnosis: 'Flu',
      prescription: 'Rest, fluids, paracetamol'
    }
  ]);
});

// 🏠 Doctor Dashboard
app.get('/api/doctor/dashboard', (req, res) => {
  res.json({
    name: 'Dr. Jane Smith',
    appointmentsToday: 5,
    pendingValidations: 2,
    recentTreatments: [
      {
        patientName: 'Alice Doe',
        date: '2025-05-05',
        diagnosis: 'Back pain'
      }
    ]
  });
});

// 📅 Doctor Appointments
app.get('/api/doctor/appointments', (req, res) => {
  res.json([
    {
      id: 22,
      patientName: 'John Doe',
      dateTime: '2025-05-10T15:30',
      reason: 'Fever',
      status: 'PENDING'
    }
  ]);
});

// ✅ Validate appointment
app.put('/api/doctor/appointments/:id/validate', (req, res) => {
  res.json({ message: 'Appointment validated.' });
});

// 🧾 Get appointment info
app.get('/api/doctor/appointments/:id', (req, res) => {
  res.json({
    patientName: 'John Doe',
    dateTime: '2025-05-10T15:30',
    reason: 'Migraine'
  });
});

// 🧾 Submit treatment
app.post('/api/doctor/treatments', (req, res) => {
  res.json({ message: 'Treatment file saved successfully.' });
});

app.listen(PORT, () => console.log(`Mock server running at http://localhost:${PORT}`));
