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
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String plateNumber;
    private String carModel;
    private String weight;
    private String height;
    @OneToOne
    private Driver carDriver;
    private boolean isDamaged;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Destination> carDestination;

    public Car(String plateNumber, String carModel, String weight, String height, Driver carDriver, boolean isDamaged, List<Destination> carDestination) {
        this.plateNumber = plateNumber;
        this.carModel = carModel;
        this.weight = weight;
        this.height = height;
        this.carDriver = carDriver;
        this.isDamaged = isDamaged;
        this.carDestination = carDestination;
    }

    @Override
    public String toString() {
        return carModel + " " + plateNumber;
    }
}
