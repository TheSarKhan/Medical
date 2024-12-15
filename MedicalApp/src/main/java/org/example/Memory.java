package org.example;



import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Reception;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    // Memory'de tutulan veriler
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Patient> patients = new ArrayList<>();
    private static List<Reception> receptions = new ArrayList<>();

    // Getters ve setters
    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static List<Patient> getPatients() {
        return patients;
    }

    public static List<Reception> getReceptions() {
        return receptions;
    }

    // Veriye CRUD işlemleri ekleyelim

    // CREATE işlemi
    public static void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public static void addPatient(Patient patient) {
        patients.add(patient);
    }

    public static void addReception(Reception reception) {
        receptions.add(reception);
    }

    // READ işlemi (Listeleri almak için)
    public static Doctor getDoctorById(int id) {
        return doctors.stream().filter(doctor -> doctor.getId() == id).findFirst().orElse(null);
    }

    public static Patient getPatientById(int id) {
        return patients.stream().filter(patient -> patient.getId() == id).findFirst().orElse(null);
    }

    public static Reception getReceptionById(int id) {
        return receptions.stream().filter(reception -> reception.getId() == id).findFirst().orElse(null);
    }

    // UPDATE işlemi
    public static void updateDoctor(int id, Doctor updatedDoctor) {
        Doctor doctor = getDoctorById(id);
        if (doctor != null) {
            doctor.setName(updatedDoctor.getName());
            doctor.setPassword(updatedDoctor.getPassword());
            // diğer doktor bilgileri güncellenebilir
        }
    }

    public static void updatePatient(int id, Patient updatedPatient) {
        Patient patient = getPatientById(id);
        if (patient != null) {
            patient.setName(updatedPatient.getName());
            patient.setPassword(updatedPatient.getPassword());
            patient.setIllness(updatedPatient.getIllness());
            // diğer hasta bilgileri güncellenebilir
        }
    }

    public static void updateReception(int id, Reception updatedReception) {
        Reception reception = getReceptionById(id);
        if (reception != null) {
            reception.setName(updatedReception.getName());
            reception.setPassword(updatedReception.getPassword());
            reception.setRole(updatedReception.getRole());
            // diğer resepsiyon bilgileri güncellenebilir
        }
    }

    // DELETE işlemi
    public static boolean deleteDoctor(int id) {
        return doctors.removeIf(doctor -> doctor.getId() == id);
    }

    public static boolean deletePatient(int id) {
        return patients.removeIf(patient -> patient.getId() == id);
    }

    public static boolean deleteReception(int id) {
        return receptions.removeIf(reception -> reception.getId() == id);
    }
}
