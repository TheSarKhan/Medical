package org.example.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reception {
    int id;
    String name;
    String password;
    String role;
    public Reception(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = "admin";
    }
    public Reception() {}
}
