package com.truckersmpspringboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.BitSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public final class DriverManager extends User implements Serializable {
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<Driver> driverManagerDrivers; //priziurimi vairuotojai
    @ManyToOne
    private Forum driverManagerForum;
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Destination> driverManagerDestination;
    @OneToMany(mappedBy = "commentByDriverManager", cascade = CascadeType.ALL)
    private List<Comment> driverManagerComment;
    private String supervisorForum;

    public DriverManager(Role role, String login, String password, String name, String surname, String phone, String email, LocalDate birthDate, String supervisorForum, List<Driver> driverManagerDrivers, Forum driverManagerForum, List<Destination> driverManagerDestination, List<Comment> driverManagerComment, boolean isAdmin) {
        super(role, login, password, name, surname, phone, email, birthDate, isAdmin);
        this.driverManagerDrivers = driverManagerDrivers;
        this.driverManagerForum = driverManagerForum;
        this.driverManagerDestination = driverManagerDestination;
        this.driverManagerComment = driverManagerComment;
        this.supervisorForum = supervisorForum;
    }
}
