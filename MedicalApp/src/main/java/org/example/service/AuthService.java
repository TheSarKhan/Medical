package org.example.service;

public interface AuthService {
    boolean login(String name, String password,String role);
    boolean signupDoctor(String name, String password,String role);
    boolean signupPatient(String name, String password, String ill);
    boolean signupReception(String name, String password, String role);
}
