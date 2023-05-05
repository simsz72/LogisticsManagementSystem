package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Destination> driverDestination;
    @OneToMany(mappedBy = "commentByDriver", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> driverComment;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Forum> inForum; //kuriame forume yra vairuotojas

    public Driver(Role role, String login, String password, String name, String surname, String phone, String email, LocalDate birthDate, String licenseNumber
            ,LocalDate licenseDate, DriverManager supervisor, boolean isAdmin) {
        super(role, login, password, name, surname, phone, email, birthDate, isAdmin);
        this.licenseNumber = licenseNumber;
        this.licenseDate = licenseDate;
        this.supervisor = supervisor;
    };
}
