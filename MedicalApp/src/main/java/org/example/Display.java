package org.example;


import org.example.model.Doctor;
import org.example.model.Patient;
import org.example.service.AuthService;
import org.example.service.impl.AuthServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Display {


    private final AuthService loginService = new AuthServiceImpl();


     private final Scanner scanner = new Scanner(System.in);

     public void hello() {
        while (true) {

            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("  🌟 Welcome to the Medical Management System    ");
            System.out.println("  🏥 Taking care of your health 🏥              ");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("\nPlease select an option:");
            System.out.println("1. 🔑 Login");
            System.out.println("2. 📝 Sign Up");
            System.out.println("3. ❌ Exit");
            System.out.print("Your choice (1-3): ");


            int choice = scanner.nextInt();
            scanner.nextLine();

             switch (choice) {
                case 1 -> login();
                case 2 -> signUp();
                case 3 -> {
                    System.out.println("👋 Exiting. Have a healthy day!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }

     private void login() {
        System.out.println("\n🔓 Login:");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine() ;

        System.out.print("Enter your role (1 for Patient, 2 for Doctor, 3 for Reception): ");
        int roleOption = scanner.nextInt();
        scanner.nextLine();

         String role;
        if (roleOption == 1) {
            role = "Patient";
        } else if (roleOption == 2) {
            role = "Doctor";
        } else if (roleOption == 3) {
            role = "Reception";
        } else {
            role = null;
        }

        if (role == null) {
            System.out.println("⚠️ Invalid role selected!");
            return;
        }


        if (loginService.login(name, password, role)) {

            if ("Patient".equalsIgnoreCase(role)) {
                patientMenu(name);
            } else if ("Doctor".equalsIgnoreCase(role)) {
                doctorMenu(name);
            } else if ("Reception".equalsIgnoreCase(role)) {
                receptionMenu(name);
            }
        } else {
            System.out.println("❌ Login failed! Invalid credentials or role.");
        }
    }


    private void signUp() {
        System.out.println("\n📝 Sign Up:");
        System.out.print("Are you signing up as a (1) Patient or (2) Doctor or (3) Reception ? ");
        int roleOption = scanner.nextInt();
        scanner.nextLine();


        if (roleOption == 1) {

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            System.out.print("Enter your illness: ");
            String illness = scanner.nextLine();
            loginService.signupPatient(name, password, illness);
        } else if (roleOption == 2) {

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            loginService.signupDoctor(name, password, "Doctor");
        } else if (roleOption == 3) {

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            loginService.signupReception(name, password, "Reception");
        } else {
            System.out.println("⚠️ Invalid role selected!");
        }
    }

    private void patientMenu(String patientName) {
        while (true) {

            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + patientName + " (Patient)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View Your Doctor");
            System.out.println("2. ❌ Logout");
            System.out.print("Your choice (1-2): ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1 -> viewAssignedDoctor(patientName);
                case 2 -> {
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }


    private void viewAssignedDoctor(String patientName) {

        Patient patient = Memory.getPatients().stream()
                .filter(p -> p.getName().equals(patientName))
                .findFirst().orElse(null);

        if (patient != null) {

            Doctor assignedDoctor = Memory.getDoctors().stream()
                    .filter(d -> d.getPatients().contains(patient))
                    .findFirst().orElse(null);

            if (assignedDoctor != null) {
                 System.out.println("✅ Your Doctor: Dr. " + assignedDoctor.getName());
            } else {
                 System.out.println("❌ No doctor assigned yet.");
            }
        } else {
             System.out.println("❌ Patient not found!");
        }
    }


    private void doctorMenu(String doctorName) {
        while (true) {

            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + doctorName + " (Doctor)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View Patient List");
            System.out.println("2. ❌ Remove a Patient");
            System.out.println("3. ❌ Logout");
            System.out.print("Your choice (1-3): ");


            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1 -> viewDoctorPatients(doctorName);
                case 2 -> removePatientFromDoctor(doctorName);
                case 3 -> {
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }


    private void viewDoctorPatients(String doctorName) {

        Doctor doctor = Memory.getDoctors().stream()
                .filter(d -> d.getName().equals(doctorName))
                .findFirst().orElse(null);

        if (doctor != null && doctor.getPatients() != null) {

            System.out.println("🔍 Patients of Dr. " + doctorName + ":");
            doctor.getPatients().forEach(patient ->
                    System.out.println("- " + patient.getName() + " (Illness: " + patient.getIllness() + ")"));
        } else {

            System.out.println("❌ No patients found for Dr. " + doctorName);
        }
    }


    private void removePatientFromDoctor(String doctorName) {

        Doctor doctor = Memory.getDoctors().stream()
                .filter(d -> d.getName().equals(doctorName))
                .findFirst().orElse(null);

        if (doctor != null && doctor.getPatients() != null && !doctor.getPatients().isEmpty()) {

            System.out.println("🔍 Patients of Dr. " + doctorName + ":");
            for (int i = 0; i < doctor.getPatients().size(); i++) {
                System.out.println((i + 1) + ". " + doctor.getPatients().get(i).getName());
            }
            System.out.print("Enter the number of the patient to remove: ");

            int index = scanner.nextInt() - 1;
            scanner.nextLine();

            if (index >= 0 && index < doctor.getPatients().size()) {

                Patient removedPatient = doctor.getPatients().remove(index);
                System.out.println("✅ Removed patient: " + removedPatient.getName());
            } else {

                System.out.println("❌ Invalid selection!");
            }
        } else {

            System.out.println("❌ No patients to remove.");
        }
    }


    private void receptionMenu(String receptionName) {
        while (true) {

            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + receptionName + " (Reception)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View All Patients List");
            System.out.println("2. 📝 View All Doctors List");
            System.out.println("3. ➕ Assign Patient to Doctor");
            System.out.println("4. ❌ Remove Patient");
            System.out.println("5. ❌ Remove Doctor");
            System.out.println("6. ❌ Logout");
            System.out.print("Your choice (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllPatients();
                case 2 -> viewAllDoctors();
                case 3 -> assignPatientToDoctor();
                case 4 -> removePatient();
                case 5 -> removeDoctor();
                case 6 -> {
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }
    private void removePatient() {
        List<Patient> patients = Memory.getPatients();
        if (patients.isEmpty()) {
            System.out.println("❌ No patients available to remove.");
            return;
        }

        System.out.println("📝 All Patients:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }
        System.out.print("Select a patient by number to remove: ");

        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (patientIndex >= 0 && patientIndex < patients.size()) {
            Patient removedPatient = patients.remove(patientIndex);
            System.out.println("✅ Removed patient: " + removedPatient.getName());
        } else {
            System.out.println("❌ Invalid selection!");
        }
    }

    private void removeDoctor() {
        List<Doctor> doctors = Memory.getDoctors();
        if (doctors.isEmpty()) {
            System.out.println("❌ No doctors available to remove.");
            return;
        }

        System.out.println("📝 All Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).getName());
        }
        System.out.print("Select a doctor by number to remove: ");

        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (doctorIndex >= 0 && doctorIndex < doctors.size()) {
            Doctor removedDoctor = doctors.remove(doctorIndex);
            System.out.println("✅ Removed doctor: Dr. " + removedDoctor.getName());
        } else {
            System.out.println("❌ Invalid selection!");
        }
    }


    private void viewAllPatients() {
        System.out.println("🔍 All Patients:");
        Memory.getPatients().forEach(patient ->
                System.out.println("- " + patient.getName() + " (Illness: " + patient.getIllness() + ")"));
    }

    private void viewAllDoctors() {
        System.out.println("🔍 All Doctors:");
        Memory.getDoctors().forEach(doctor ->
                System.out.println("- Dr. " + doctor.getName()));
    }

    private void assignPatientToDoctor() {
        System.out.println("🔍 Available Patients:");
        List<Patient> patients = Memory.getPatients();
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }

        System.out.print("Select a patient by number: ");
        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        System.out.println("🔍 Available Doctors:");
        List<Doctor> doctors = Memory.getDoctors();
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).getName());
        }

        System.out.print("Select a doctor by number: ");
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (patientIndex >= 0 && patientIndex < patients.size() &&
                doctorIndex >= 0 && doctorIndex < doctors.size()) {

            Doctor doctor = doctors.get(doctorIndex);
            Patient patient = patients.get(patientIndex);
            doctor.getPatients().add(patient);
            System.out.println("✅ Assigned " + patient.getName() + " to Dr. " + doctor.getName());
        } else {
            System.out.println("❌ Invalid selection!");
        }
    }


}

