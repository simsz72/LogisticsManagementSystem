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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public final class Driver extends User implements Serializable {
    private String licenseNumber; //teisiu kodas
    private LocalDate licenseDate; //iki kada galioja teises
    @ManyToOne
    private DriverManager supervisor; //atsakingas manager
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Destination> driverDestination;
    @OneToMany(mappedBy = "commentByDriver", cascade = CascadeType.ALL)
    private List<Comment> driverComment;
    @ManyToMany
    private List<Forum> inForum; //kuriame forume yra vairuotojas

    public Driver(Role role, String login, String password, String name, String surname, String phone, String email, LocalDate birthDate, String licenseNumber
            ,LocalDate licenseDate, DriverManager supervisor, boolean isAdmin) {
        super(role, login, password, name, surname, phone, email, birthDate, isAdmin);
        this.licenseNumber = licenseNumber;
        this.licenseDate = licenseDate;
        this.supervisor = supervisor;
    };
}
