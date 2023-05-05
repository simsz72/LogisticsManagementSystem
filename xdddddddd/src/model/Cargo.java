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
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String height;
    private String weight;
    private String cargoName;

    @ManyToMany(mappedBy = "cargo")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Destination> destAddress;


    public Cargo(String height, String weight, String cargoName) {
        this.height = height;
        this.weight = weight;
        this.cargoName = cargoName;
    }

    @Override
    public String toString() {
        return cargoName + " " + weight + " " + height;
    }
}
