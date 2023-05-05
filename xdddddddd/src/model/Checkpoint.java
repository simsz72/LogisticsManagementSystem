package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String checkpointAddress;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    @ManyToMany(mappedBy = "checkpoints", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Destination> cpDestination;

    public Checkpoint(String checkpointAddress, LocalDate arrivalDate, LocalDate departureDate, List<Destination> cpDestination) {
        this.checkpointAddress = checkpointAddress;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.cpDestination = cpDestination;
    }

    @Override
    public String toString() {
        return checkpointAddress;
    }
}
