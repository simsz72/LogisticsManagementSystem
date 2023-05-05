package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    @ManyToOne
    private Car car;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Checkpoint> checkpoints;
    @ManyToOne
    private Driver driver;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DriverManager> manager;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cargo> cargo;
    private Status status;


    public Destination(String placeAddress, LocalDate departureDate, LocalDate arrivalDate, Car car, List<Checkpoint> checkpoints, Driver driver, List <DriverManager> manager, List <Cargo> cargo, Status status) {
        this.placeAddress = placeAddress;
        this.car = car;
        this.checkpoints = checkpoints;
        this.driver = driver;
        this.manager = manager;
        this.cargo = cargo;
        this.status = status;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
    @Override
    public String toString() {
        return placeAddress + " (" + status + ")";
    }
}
