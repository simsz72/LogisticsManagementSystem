package com.truckersmpspringboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String height;
    private String weight;
    private String cargoName;
    private LocalDate deliveryDate;
    private LocalDate pickupDate;
    @OneToOne
    private Destination destAddress;
    private String customer;
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL)
    private List<Destination> cargoDestination;

    public Cargo(String height, String weight, String cargoName, LocalDate deliveryDate, LocalDate pickupDate, Destination destAddress, String customer, List<Destination> cargoDestination) {
        this.height = height;
        this.weight = weight;
        this.cargoName = cargoName;
        this.deliveryDate = deliveryDate;
        this.pickupDate = pickupDate;
        this.destAddress = destAddress;
        this.customer = customer;
        this.cargoDestination = cargoDestination;
    }

    @Override
    public String toString() {
        return cargoName + " " + weight + " " + height;
    }
}
