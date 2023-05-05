package fxControllers;

import hibernate.CargoHib;
import hibernate.CommentHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CommentPage {

    @FXML
    public TextArea commentTextArea;
    @FXML
    public Button actionButton;

    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private CommentHib commentHib;
    private Comment selectedComment;
    private Forum selectedForum;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Forum selectedForum) {
        this.currentUser = currentUser;
        this.selectedForum = selectedForum;
        this.entityManagerFactory = entityManagerFactory;
        this.commentHib = new CommentHib(entityManagerFactory);
    }

    public void setDataEdit(EntityManagerFactory entityManagerFactory, User currentUser, Comment selectedComment) {
        this.currentUser = currentUser;
        this.selectedComment = selectedComment;
        this.entityManagerFactory = entityManagerFactory;
        this.commentHib = new CommentHib(entityManagerFactory);
        fillFields();
    }

    public void validateComment() throws IOException {
        Comment comment;
        if(currentUser.getRole() == Role.DRIVERMANAGER)
        {
            comment = new Comment(commentTextArea.getText(), null, selectedForum, (DriverManager) currentUser, null);
        }
        else
        {
            comment = new Comment(commentTextArea.getText(), null, selectedForum, null, (Driver) currentUser);
        }
        commentHib.createComment(comment);
    }

    private void fillFields() {
        Comment comment = (Comment) commentHib.getCommentById(selectedComment.getId());
        commentTextArea.setText(comment.getCommentText());
        //like
        actionButton.setOnAction(actionEvent -> {
            updateCar(comment);
        });
        actionButton.setText("Update");
    }

    private void updateCar(Comment comment) {
        comment.setCommentText(commentTextArea.getText());
        commentHib.updateComment(comment);
    }

    public void cancelComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) commentTextArea.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }
}
