package az.medical;

import az.medical.service.AuthService;
import az.medical.service.Impl.AuthServiceImpl;

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
        String password = scanner.nextLine();

        System.out.print("Enter your role (1 for Patient, 2 for Doctor,3 for Reception): ");
        int roleOption = scanner.nextInt();
        scanner.nextLine();

        String role;
        if (roleOption == 1) {
            role = "Patient";
        } else if (roleOption == 2) {
            role = "Doctor";
        }else if (roleOption == 3) {
            role = "Reception";
        }  else {
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
        }else if (roleOption == 3) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            loginService.signupReception(name, password, "Reception");
        }  else {
            System.out.println("⚠️ Invalid role selected!");
        }
    }

    private void patientMenu(String patientName) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + patientName + " (Patient)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 🏥 View Health Information");
            System.out.println("2. 📅 Schedule Appointment");
            System.out.println("3. ❌ Logout");
            System.out.print("Your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> System.out.println("🔍 Viewing health information...");
                case 2 -> System.out.println("📅 Scheduling an appointment...");
                case 3 -> {
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }

    private void doctorMenu(String doctorName) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + doctorName + " (Doctor)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View Patient List");
            System.out.println("2. 📅 Manage Appointments");
            System.out.println("3. ❌ Logout");
            System.out.print("Your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> System.out.println("🔍 Viewing patient list...");
                case 2 -> System.out.println("📅 Managing appointments...");
                case 3 -> {
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }
    private void receptionMenu(String receptionName) {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + receptionName + " (Reception)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View All Patients List");
            System.out.println("2. 📝 View All Doctors List");
            System.out.println("3. 📅 Manage Patients List");
            System.out.println("4. ❌ Logout");
            System.out.print("Your choice (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> System.out.println("🔍 Viewing patient list...");
                case 2 -> System.out.println("📅 Managing appointments...");
                case 3 -> {
                    System.out.println("👋 Logging out. Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again.");
            }
        }
    }
}
