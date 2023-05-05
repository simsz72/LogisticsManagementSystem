package com.truckersmpspringboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String placeAddress;
    @ManyToOne
    private Car car;
    @ManyToMany
    private List<Checkpoint> checkpoints;
    @ManyToOne
    private Driver driver;
    @ManyToOne
    private DriverManager manager;
    @ManyToOne
    private Cargo cargo;


    public Destination(String placeAddress, Car car, List<Checkpoint> checkpoints, Driver driver, DriverManager manager, Cargo cargo) {
        this.placeAddress = placeAddress;
        this.car = car;
        this.checkpoints = checkpoints;
        this.driver = driver;
        this.manager = manager;
        this.cargo = cargo;
    }
    @Override
    public String toString() {
        return placeAddress;
    }
}
