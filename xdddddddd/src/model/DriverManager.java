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
import java.util.BitSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public final class DriverManager extends User implements Serializable {
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Driver> driverManagerDrivers; //priziurimi vairuotojai
    @ManyToOne
    private Forum driverManagerForum;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Destination> driverManagerDestination;
    @OneToMany(mappedBy = "commentByDriverManager", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
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
