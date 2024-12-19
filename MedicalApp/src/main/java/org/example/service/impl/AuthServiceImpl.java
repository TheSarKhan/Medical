package org.example.service.impl;

import org.example.Memory;
import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.model.Reception;
import org.example.service.AuthService;

import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {


    @Override
    public boolean login(String name, String password, String role) {
        if ("Doctor".equalsIgnoreCase(role)) {
            for (Doctor doctor : Memory.getDoctors()) {
                if (doctor.getName().equals(name) && doctor.getPassword().equals(password)) {
                    System.out.println("🔓 Doctor login successful: " + name);
                    return true;
                }
            }
        } else if ("Patient".equalsIgnoreCase(role)) {
            for (Patient patient : Memory.getPatients()) {
                if (patient.getName().equals(name) && patient.getPassword().equals(password)) {
                    System.out.println("🔓 Patient login successful: " + name);
                    return true;
                }
            }
        } else if ("Reception".equalsIgnoreCase(role)) {
            for (Reception reception : Memory.getReceptions()) {
                if (reception.getName().equals(name) && reception.getPassword().equals(password)) {
                    System.out.println("🔓 Reception login successful: " + name);
                    return true;
                }
            }
        }

        System.out.println("❌ Login failed! Invalid credentials.");
        return false;
    }


    @Override
    public boolean signupDoctor(String name, String password, String role) {
        for (Doctor doctor : Memory.getDoctors()) {
            if (doctor.getName().equals(name)) {
                System.out.println("⚠️ Doctor name already exists!");
                return false;
            }
        }


        Doctor newDoctor = new Doctor();
        newDoctor.setId(Memory.getDoctors().size() + 1);
        newDoctor.setName(name);
        newDoctor.setPassword(password);
        newDoctor.setRole(role);
        newDoctor.setPatients(new ArrayList<>());

        Memory.addDoctor(newDoctor);
        System.out.println("✅ Doctor registered successfully: " + name);
        return true;
    }


    @Override
    public boolean signupPatient(String name, String password, String illness) {
        for (Patient patient : Memory.getPatients()) {
            if (patient.getName().equals(name)) {
                System.out.println("⚠️ Patient name already exists!");
                return false;
            }
        }

        Patient newPatient = new Patient();
        newPatient.setId(Memory.getPatients().size() + 1);
        newPatient.setName(name);
        newPatient.setPassword(password);
        newPatient.setIllness(illness);
        newPatient.setRole("Patient");

        Memory.addPatient(newPatient);
        System.out.println("✅ Patient registered successfully: " + name);
        return true;
    }


    @Override
    public boolean signupReception(String name, String password, String role) {
        for (Reception reception : Memory.getReceptions()) {
            if (reception.getName().equals(name)) {
                System.out.println("⚠️ Reception name already exists!");
                return false;
            }
        }

        Reception newReception = new Reception();
        newReception.setId(Memory.getReceptions().size() + 1);
        newReception.setName(name);
        newReception.setPassword(password);
        newReception.setRole(role);

        Memory.addReception(newReception);
        System.out.println("✅ Reception registered successfully: " + name);
        return true;
    }
}
