package az.medical;

import az.medical.model.Doctor;
import az.medical.model.Patient;
import az.medical.model.Reception;

import java.util.ArrayList;
import java.util.List;

public  class Main {
    public void showPatients() {
        List<Patient> patients = Memory.getPatients();
        for (Patient patient : patients) {
            System.out.println("Patient: " + patient.getName() + " - Illness: " + patient.getIllness());
        }
    }

        public static void main(String[] args) {
              List<Doctor> doctors = new ArrayList<>();
              List<Patient> patients = new ArrayList<>();
              List<Reception> receptions = new ArrayList<>();
            Display display = new Display();
            display.hello();
        }
    }
