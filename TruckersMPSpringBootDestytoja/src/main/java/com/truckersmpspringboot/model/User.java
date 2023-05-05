package com.truckersmpspringboot.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated
    private Role role;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private boolean isAdmin;

    public User(Role role, String login, String password, String name, String surname, String phone, String email, LocalDate birthDate, boolean isAdmin) {
        this.role = role;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}