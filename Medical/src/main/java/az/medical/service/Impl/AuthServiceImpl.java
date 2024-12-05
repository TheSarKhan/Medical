package az.medical.service.Impl;

import az.medical.model.Doctor;
import az.medical.model.Patient;
import az.medical.model.Reception;
import az.medical.service.AuthService;

import java.util.ArrayList;
import java.util.List;

public class AuthServiceImpl implements AuthService {

    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Reception> receptions = new ArrayList<>();


    @Override
    public boolean login(String name, String password, String role) {
        if ("Doctor".equalsIgnoreCase(role)) {
            for (Doctor doctor : doctors) {
                if (doctor.getName().equals(name) && doctor.getPassword().equals(password)) {
                    System.out.println("🔓 Doctor login successful: " + name);
                    return true;
                }
            }
        } else if ("Patient".equalsIgnoreCase(role)) {
            for (Patient patient : patients) {
                if (patient.getName().equals(name) && patient.getPassword().equals(password)) {
                    System.out.println("🔓 Patient login successful: " + name);
                    return true;
                }
            }
        } else if ("Reception".equalsIgnoreCase(role)) {
            for (Reception reception : receptions) {
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
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(name)) {
                System.out.println("⚠️ Doctor name already exists!");
                return false;
            }
        }
        Doctor newDoctor = new Doctor();
        newDoctor.setId(doctors.size() + 1);
        newDoctor.setName(name);
        newDoctor.setPassword(password);
        newDoctor.setRole(role);
        newDoctor.setPatients(new ArrayList<>());

        doctors.add(newDoctor);
        System.out.println("✅ Doctor registered successfully: " + name);
        return true;
    }

    @Override
    public boolean signupPatient(String name, String password, String illness) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name)) {
                System.out.println("⚠️ Patient name already exists!");
                return false;
            }
        }

        Patient newPatient = new Patient();
        newPatient.setId(patients.size() + 1);
        newPatient.setName(name);
        newPatient.setPassword(password);
        newPatient.setIllness(illness);
        newPatient.setRole("Patient");

        patients.add(newPatient);
        System.out.println("✅ Patient registered successfully: " + name);
        return true;
    }
    @Override
    public boolean signupReception(String name, String password, String role) {
        for (Reception reception : receptions) {
            if (reception.getName().equals(name)) {
                System.out.println("⚠️ Reception name already exists!");
                return false;
            }
        }
        Reception newReception = new Reception();
        newReception.setId(doctors.size() + 1);
        newReception.setName(name);
        newReception.setPassword(password);
        newReception.setRole(role);

        receptions.add(newReception);
        System.out.println("✅ Reception registered successfully: " + name);
        return true;
    }
}
