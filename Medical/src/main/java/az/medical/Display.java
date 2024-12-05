package az.medical;

import az.medical.model.Doctor;
import az.medical.model.Patient;
import az.medical.service.AuthService;
import az.medical.service.Impl.AuthServiceImpl;
import az.medical.service.Impl.PatientServiceImpl;
import az.medical.service.PatientService;

import java.util.List;
import java.util.Scanner;

public class Display {

    // Kullanıcı giriş ve kayıt işlemleri için servis sınıfları
    private final AuthService loginService = new AuthServiceImpl();
    private final PatientService patientService = new PatientServiceImpl();

    // Kullanıcıdan veri almak için Scanner nesnesi
    private final Scanner scanner = new Scanner(System.in);

    // Ana menüyü başlatır
    public void hello() {
        while (true) {
            // Ana menü görünümü
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("  🌟 Welcome to the Medical Management System    ");
            System.out.println("  🏥 Taking care of your health 🏥              ");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("\nPlease select an option:");
            System.out.println("1. 🔑 Login"); // Giriş yapma seçeneği
            System.out.println("2. 📝 Sign Up"); // Kayıt olma seçeneği
            System.out.println("3. ❌ Exit"); // Programdan çıkış seçeneği
            System.out.print("Your choice (1-3): ");

            // Kullanıcı seçimi alınır
            int choice = scanner.nextInt();
            scanner.nextLine(); // Scanner'ın buffer'ını temizler

            // Seçime göre işlem yapılır
            switch (choice) {
                case 1 -> login(); // Giriş yapma işlemi
                case 2 -> signUp(); // Kayıt olma işlemi
                case 3 -> {
                    System.out.println("👋 Exiting. Have a healthy day!"); // Program sonlandırılır
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again."); // Hatalı seçim
            }
        }
    }

    // Kullanıcı giriş işlemleri
    private void login() {
        System.out.println("\n🔓 Login:");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine(); // Kullanıcı adı alınır

        System.out.print("Enter your password: ");
        String password = scanner.nextLine(); // Kullanıcı şifresi alınır

        System.out.print("Enter your role (1 for Patient, 2 for Doctor, 3 for Reception): ");
        int roleOption = scanner.nextInt(); // Kullanıcı rolü seçimi
        scanner.nextLine();

        // Seçilen role göre isimlendirme yapılır
        String role;
        if (roleOption == 1) {
            role = "Patient";
        } else if (roleOption == 2) {
            role = "Doctor";
        } else if (roleOption == 3) {
            role = "Reception";
        } else {
            role = null; // Hatalı seçim
        }

        // Geçersiz rol seçimi durumunda uyarı
        if (role == null) {
            System.out.println("⚠️ Invalid role selected!");
            return;
        }

        // Giriş doğrulaması
        if (loginService.login(name, password, role)) {
            // Rol bazlı menüye yönlendirme
            if ("Patient".equalsIgnoreCase(role)) {
                patientMenu(name);
            } else if ("Doctor".equalsIgnoreCase(role)) {
                doctorMenu(name);
            } else if ("Reception".equalsIgnoreCase(role)) {
                receptionMenu(name);
            }
        } else {
            System.out.println("❌ Login failed! Invalid credentials or role."); // Hatalı giriş
        }
    }

    // Kayıt olma işlemleri
    private void signUp() {
        System.out.println("\n📝 Sign Up:");
        System.out.print("Are you signing up as a (1) Patient or (2) Doctor or (3) Reception ? ");
        int roleOption = scanner.nextInt(); // Kullanıcı rol seçimi
        scanner.nextLine();

        // Role göre işlem yapılır
        if (roleOption == 1) {
            // Hasta kaydı
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            System.out.print("Enter your illness: ");
            String illness = scanner.nextLine();
            loginService.signupPatient(name, password, illness); // Hasta kaydı servisi çağrılır
        } else if (roleOption == 2) {
            // Doktor kaydı
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            loginService.signupDoctor(name, password, "Doctor"); // Doktor kaydı servisi çağrılır
        } else if (roleOption == 3) {
            // Resepsiyonist kaydı
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            loginService.signupReception(name, password, "Reception"); // Resepsiyon kaydı
        } else {
            System.out.println("⚠️ Invalid role selected!"); // Hatalı seçim
        }
    }

    // Hasta menüsü
    private void patientMenu(String patientName) {
        while (true) {
            // Hasta için menü görünümü
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + patientName + " (Patient)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View Your Doctor"); // Hastanın doktorunu görüntüleme seçeneği
            System.out.println("2. ❌ Logout"); // Oturum kapatma seçeneği
            System.out.print("Your choice (1-2): ");

            int choice = scanner.nextInt(); // Kullanıcı seçimi
            scanner.nextLine();

            // Seçime göre işlem yapılır
            switch (choice) {
                case 1 -> viewAssignedDoctor(patientName); // Hastanın atanmış doktorunu görüntüler
                case 2 -> {
                    System.out.println("👋 Logging out. Goodbye!"); // Oturum kapatılır
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again."); // Hatalı seçim
            }
        }
    }

    // Belirli bir hastanın atanmış doktorunu görüntüler
    private void viewAssignedDoctor(String patientName) {
        // Hasta adını alarak Memory'den hastayı bulmaya çalışıyoruz
        Patient patient = Memory.getPatients().stream()
                .filter(p -> p.getName().equals(patientName)) // Hasta adı eşleşiyor mu kontrolü
                .findFirst().orElse(null);

        if (patient != null) {
            // Eğer hasta varsa, doktorların listesinde bu hastayı içeren doktoru arıyoruz
            Doctor assignedDoctor = Memory.getDoctors().stream()
                    .filter(d -> d.getPatients().contains(patient)) // Doktor hastayı içeriyor mu kontrolü
                    .findFirst().orElse(null);

            if (assignedDoctor != null) {
                // Eğer atanmış bir doktor varsa, doktorun adı yazdırılır
                System.out.println("✅ Your Doctor: Dr. " + assignedDoctor.getName());
            } else {
                // Hasta atanmış bir doktora sahip değilse uyarı mesajı
                System.out.println("❌ No doctor assigned yet.");
            }
        } else {
            // Hasta bulunamazsa hata mesajı
            System.out.println("❌ Patient not found!");
        }
    }

    // Doktorlar için özel menü
    private void doctorMenu(String doctorName) {
        while (true) {
            // Menü başlığı
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + doctorName + " (Doctor)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View Patient List"); // Doktorun hastalarını görüntüle
            System.out.println("2. ❌ Remove a Patient"); // Hastayı listeden kaldır
            System.out.println("3. ❌ Logout"); // Çıkış yap
            System.out.print("Your choice (1-3): ");

            // Kullanıcı seçim yapıyor
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Kullanıcı seçimine göre işlem yapılıyor
            switch (choice) {
                case 1 -> viewDoctorPatients(doctorName); // Hastaları görüntüle
                case 2 -> removePatientFromDoctor(doctorName); // Hastayı kaldır
                case 3 -> {
                    System.out.println("👋 Logging out. Goodbye!"); // Çıkış mesajı
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again."); // Geçersiz seçim mesajı
            }
        }
    }

    // Doktorun hastalarını listeleme işlemi
    private void viewDoctorPatients(String doctorName) {
        // Doktorun Memory'deki verisini alıyoruz
        Doctor doctor = Memory.getDoctors().stream()
                .filter(d -> d.getName().equals(doctorName)) // Doktor adı eşleşiyor mu kontrolü
                .findFirst().orElse(null);

        if (doctor != null && doctor.getPatients() != null) {
            // Eğer doktorun hastaları varsa listeleme işlemi
            System.out.println("🔍 Patients of Dr. " + doctorName + ":");
            doctor.getPatients().forEach(patient ->
                    System.out.println("- " + patient.getName() + " (Illness: " + patient.getIllness() + ")"));
        } else {
            // Eğer doktorun hastası yoksa veya hata oluşursa mesaj
            System.out.println("❌ No patients found for Dr. " + doctorName);
        }
    }

    // Doktorun hastayı listeden kaldırma işlemi
    private void removePatientFromDoctor(String doctorName) {
        // Doktoru Memory'den buluyoruz
        Doctor doctor = Memory.getDoctors().stream()
                .filter(d -> d.getName().equals(doctorName)) // Doktor adı eşleşiyor mu kontrolü
                .findFirst().orElse(null);

        if (doctor != null && doctor.getPatients() != null && !doctor.getPatients().isEmpty()) {
            // Eğer doktorun hastaları varsa listeleme işlemi
            System.out.println("🔍 Patients of Dr. " + doctorName + ":");
            for (int i = 0; i < doctor.getPatients().size(); i++) {
                System.out.println((i + 1) + ". " + doctor.getPatients().get(i).getName());
            }
            System.out.print("Enter the number of the patient to remove: "); // Hastayı kaldırmak için seçim

            int index = scanner.nextInt() - 1; // Kullanıcının girdiği numarayı alıyoruz
            scanner.nextLine();

            if (index >= 0 && index < doctor.getPatients().size()) {
                // Geçerli bir seçim yapıldıysa hastayı kaldırıyoruz
                Patient removedPatient = doctor.getPatients().remove(index);
                System.out.println("✅ Removed patient: " + removedPatient.getName());
            } else {
                // Geçersiz seçim mesajı
                System.out.println("❌ Invalid selection!");
            }
        } else {
            // Eğer doktorun hastası yoksa mesaj
            System.out.println("❌ No patients to remove.");
        }
    }

    // Reception kullanıcıları için menü
    private void receptionMenu(String receptionName) {
        while (true) {
            // Menü başlığı
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("       🌟 Welcome, " + receptionName + " (Reception)");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. 📝 View All Patients List"); // Tüm hastaları listele
            System.out.println("2. 📝 View All Doctors List"); // Tüm doktorları listele
            System.out.println("3. ➕ Assign Patient to Doctor"); // Hastayı doktora ata
            System.out.println("4. ❌ Logout"); // Çıkış yap
            System.out.print("Your choice (1-4): "); // Kullanıcı seçimi

            int choice = scanner.nextInt(); // Kullanıcı seçimi alınır
            scanner.nextLine();

            // Seçime göre işlemler
            switch (choice) {
                case 1 -> viewAllPatients(); // Tüm hastaları listele
                case 2 -> viewAllDoctors(); // Tüm doktorları listele
                case 3 -> assignPatientToDoctor(); // Hasta atama
                case 4 -> {
                    System.out.println("👋 Logging out. Goodbye!"); // Çıkış mesajı
                    return;
                }
                default -> System.out.println("⚠️ Invalid option! Please try again."); // Geçersiz seçim mesajı
            }
        }
    }

    // Tüm hastaları görüntüleme işlemi
    private void viewAllPatients() {
        System.out.println("🔍 All Patients:");
        Memory.getPatients().forEach(patient -> // Tüm hastalar listelenir
                System.out.println("- " + patient.getName() + " (Illness: " + patient.getIllness() + ")"));
    }

    // Tüm doktorları görüntüleme işlemi
    private void viewAllDoctors() {
        System.out.println("🔍 All Doctors:");
        Memory.getDoctors().forEach(doctor -> // Tüm doktorlar listelenir
                System.out.println("- Dr. " + doctor.getName()));
    }

    // Hastayı doktora atama işlemi
    private void assignPatientToDoctor() {
        System.out.println("🔍 Available Patients:");
        List<Patient> patients = Memory.getPatients(); // Tüm hastalar alınıyor
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }

        System.out.print("Select a patient by number: "); // Hasta seçimi
        int patientIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        System.out.println("🔍 Available Doctors:");
        List<Doctor> doctors = Memory.getDoctors(); // Tüm doktorlar alınıyor
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).getName());
        }

        System.out.print("Select a doctor by number: "); // Doktor seçimi
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (patientIndex >= 0 && patientIndex < patients.size() &&
                doctorIndex >= 0 && doctorIndex < doctors.size()) {
            // Geçerli seçimlerle atama işlemi
            Doctor doctor = doctors.get(doctorIndex);
            Patient patient = patients.get(patientIndex);
            doctor.getPatients().add(patient);
            System.out.println("✅ Assigned " + patient.getName() + " to Dr. " + doctor.getName());
        } else {
            System.out.println("❌ Invalid selection!");
        }
    }

    // Hastanın hastalığını görüntüleme
    public void viewPatientIllness(String patientName) {
        // Memory'den hastalığı alıyoruz
        String illness = patientService.showIllnessByName(patientName);

        // Hastalık bilgisi yazdırılıyor
        System.out.println("Patient's illness: " + illness);
    }
}

