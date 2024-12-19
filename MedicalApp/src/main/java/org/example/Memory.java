package org.example;



import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Reception;

import java.util.ArrayList;
import java.util.List;

public class Memory {
     private static List<Doctor> doctors = new ArrayList<>();
    private static List<Patient> patients = new ArrayList<>();
    private static List<Reception> receptions = new ArrayList<>();

     public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static List<Patient> getPatients() {
        return patients;
    }

    public static List<Reception> getReceptions() {
        return receptions;
    }




    public static void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public static void addPatient(Patient patient) {
        patients.add(patient);
    }

    public static void addReception(Reception reception) {
        receptions.add(reception);
    }


    public static Doctor getDoctorById(int id) {
        return doctors.stream().filter(doctor -> doctor.getId() == id).findFirst().orElse(null);
    }

    public static Patient getPatientById(int id) {
        return patients.stream().filter(patient -> patient.getId() == id).findFirst().orElse(null);
    }

    public static Reception getReceptionById(int id) {
        return receptions.stream().filter(reception -> reception.getId() == id).findFirst().orElse(null);
    }


 }
