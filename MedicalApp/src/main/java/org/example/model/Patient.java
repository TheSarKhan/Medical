package org.example.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient {
    int id;
    String name;
    String password;
    String illness;
    String role;


    public Patient() {}
}
