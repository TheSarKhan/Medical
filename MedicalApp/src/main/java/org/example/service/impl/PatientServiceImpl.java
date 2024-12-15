package org.example.service.impl;

import org.example.Memory;
import org.example.model.Patient;
import org.example.service.PatientService;

import java.util.List;
import java.util.Objects;

public class PatientServiceImpl implements PatientService {

    @Override
    public String showIllnessByName(String name){
        List<Patient> patients = Memory.getPatients();
        Patient patient = patients.stream().filter(p -> Objects.equals(p.getName(), name)).findFirst().orElse(null);

        if (patient != null) {
            return patient.getIllness();
        }

        return "❌ Patient not found";
    }
}
