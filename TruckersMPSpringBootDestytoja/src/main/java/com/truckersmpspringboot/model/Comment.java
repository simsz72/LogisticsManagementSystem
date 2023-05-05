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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentText;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Forum forumComment;
    @ManyToOne
    private DriverManager commentByDriverManager;
    @ManyToOne
    private Driver commentByDriver;

    public Comment(String commentText, List<Comment> replies, Comment parentComment, Forum forumComment, DriverManager commentByDriverManager, Driver commentByDriver) {
        this.commentText = commentText;
        this.replies = replies;
        this.parentComment = parentComment;
        this.forumComment = forumComment;
        this.commentByDriverManager = commentByDriverManager;
        this.commentByDriver = commentByDriver;
    }
    @Override
    public String toString() {
        return commentText;
    }
}
