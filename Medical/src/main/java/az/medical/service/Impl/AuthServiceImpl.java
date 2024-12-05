package az.medical.service.Impl;

import az.medical.Memory;
import az.medical.model.Doctor;
import az.medical.model.Patient;
import az.medical.model.Reception;
import az.medical.service.AuthService;

import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {


    @Override
    public boolean login(String name, String password, String role) {
        if ("Doctor".equalsIgnoreCase(role)) { // Kullanıcı rolü "Doctor" ise
            for (Doctor doctor : Memory.getDoctors()) {
                if (doctor.getName().equals(name) && doctor.getPassword().equals(password)) {
                    System.out.println("🔓 Doctor login successful: " + name);
                    return true;
                }
            }
        } else if ("Patient".equalsIgnoreCase(role)) { // Kullanıcı rolü "Patient" ise
            for (Patient patient : Memory.getPatients()) {
                if (patient.getName().equals(name) && patient.getPassword().equals(password)) {
                    System.out.println("🔓 Patient login successful: " + name);
                    return true;
                }
            }
        } else if ("Reception".equalsIgnoreCase(role)) { // Kullanıcı rolü "Reception" ise
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

        // Yeni doktor nesnesi oluşturulur ve bilgileri atanır
        Doctor newDoctor = new Doctor();
        newDoctor.setId(Memory.getDoctors().size() + 1); // ID otomatik artar
        newDoctor.setName(name);
        newDoctor.setPassword(password);
        newDoctor.setRole(role);
        newDoctor.setPatients(new ArrayList<>()); // Doktor başlangıçta hasta içermez

        Memory.addDoctor(newDoctor); // Yeni doktor belleğe eklenir
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

        // Yeni hasta nesnesi oluşturulur ve bilgileri atanır
        Patient newPatient = new Patient();
        newPatient.setId(Memory.getPatients().size() + 1); // ID otomatik artar
        newPatient.setName(name);
        newPatient.setPassword(password);
        newPatient.setIllness(illness);
        newPatient.setRole("Patient");

        Memory.addPatient(newPatient); // Yeni hasta belleğe eklenir
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

        // Yeni resepsiyonist nesnesi oluşturulur ve bilgileri atanır
        Reception newReception = new Reception();
        newReception.setId(Memory.getReceptions().size() + 1); // ID otomatik artar
        newReception.setName(name);
        newReception.setPassword(password);
        newReception.setRole(role);

        Memory.addReception(newReception); // Yeni resepsiyonist belleğe eklenir
        System.out.println("✅ Reception registered successfully: " + name);
        return true;
    }
}
