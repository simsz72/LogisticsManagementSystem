package fxControllers;

import hibernate.CargoHib;
import hibernate.CommentHib;
import hibernate.ForumHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public class ChosenForumPage {


    @FXML
    public Text forumDescription;
    @FXML
    public Text forumText;
    public ListView <Comment>commentList;
    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private ForumHib forumHib;
    private Forum selectedForum;
    private CommentHib commentHib;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Forum selectedForum) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedForum = selectedForum;
        this.forumHib = new ForumHib(entityManagerFactory);
        this.commentHib = new CommentHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Forum forum = (Forum) forumHib.getForumById(selectedForum.getId());
        forumText.setText(forum.getForumTitle());
        forumDescription.setText(forum.getDescription());
        List<Comment> allComments = commentHib.getAllComments();
        allComments.forEach(d -> commentList.getItems().add(d));
    }

    public void createComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/comment-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentPage commentpage = fxmlLoader.getController();
        commentpage.setData(entityManagerFactory, currentUser, selectedForum);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(commentList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
