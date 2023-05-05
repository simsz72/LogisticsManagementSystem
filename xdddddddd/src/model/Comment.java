package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentText;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Forum forumComment;
    @ManyToOne
    private DriverManager commentByDriverManager;
    @ManyToOne
    private Driver commentByDriver;

    public Comment(String commentText, Comment parentComment, Forum forumComment, DriverManager commentByDriverManager, Driver commentByDriver) {
        this.commentText = commentText;
        this.replies = replies;
        this.parentComment = parentComment;
        this.forumComment = forumComment;
        this.commentByDriverManager = commentByDriverManager;
        this.commentByDriver = commentByDriver;
    }
    @Override
    public String toString() {
        if(commentByDriver != null)
        {
            return commentByDriver + "\n" + commentText;
        }
        else
        {
            return commentByDriverManager + "\n" + commentText;
        }
    }
}
