package model;

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
    private String description;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "supervisorForum", cascade = CascadeType.ALL)
    private List<DriverManager> forumManagers;
    @ManyToMany(mappedBy = "inForum", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Driver> forumUsers; //forumui priskirti vairuotojai
    @OneToMany(mappedBy = "forumComment", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> forumComment; //sukurti links

    public Forum(String forumTitle, String description, List<DriverManager> forumManagers, List<Driver> forumUsers, List<Comment> forumComment) {
        this.forumTitle = forumTitle;
        this.description = description;
        this.forumManagers = forumManagers;
        this.forumUsers = forumUsers;
        this.forumComment = forumComment;
    }

    @Override
    public String toString() {
        return forumTitle;
    }
}
