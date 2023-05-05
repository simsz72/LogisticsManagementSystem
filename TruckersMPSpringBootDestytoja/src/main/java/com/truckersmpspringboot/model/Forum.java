package com.truckersmpspringboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Forum implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String forumTitle;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "supervisorForum", cascade = CascadeType.ALL)
    private List<DriverManager> forumManagers;
    @ManyToMany(mappedBy = "inForum", cascade = CascadeType.ALL)
    private List<Driver> forumUsers; //forumui priskirti vairuotojai
    @OneToMany(mappedBy = "forumComment", cascade = CascadeType.ALL)
    private List<Comment> forumComment; //sukurti links

    public Forum(String forumTitle, List<DriverManager> forumManagers, List<Driver> forumUsers, List<Comment> forumComment) {
        this.forumTitle = forumTitle;
        this.forumManagers = forumManagers;
        this.forumUsers = forumUsers;
        this.forumComment = forumComment;
    }

    @Override
    public String toString() {
        return forumTitle;
    }
}