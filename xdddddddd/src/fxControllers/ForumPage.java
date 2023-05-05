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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class ForumPage {
    @FXML
    public Button actionButton;
    @FXML
    public TextArea forumDescriptionArea;
    @FXML
    public TextField forumTitleField;

    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private ForumHib forumHib;
    private Forum selectedForum;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.forumHib = new ForumHib(entityManagerFactory);
    }

    public void setDataEdit(EntityManagerFactory entityManagerFactory, User currentUser, Forum selectedForum) {
        this.currentUser = currentUser;
        this.selectedForum = selectedForum;
        this.entityManagerFactory = entityManagerFactory;
        this.forumHib = new ForumHib(entityManagerFactory);
        fillFields();
    }

    public void validateForum() throws IOException {
        Forum forum = new Forum(forumTitleField.getText(), forumDescriptionArea.getText(), null, null, null);
        forumHib.createForum(forum);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) forumTitleField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    private void fillFields() {
        Forum forum = (Forum) forumHib.getForumById(selectedForum.getId());
        forumTitleField.setText(forum.getForumTitle());
        forumDescriptionArea.setText(forum.getDescription());
        //like
        actionButton.setOnAction(actionEvent -> {
            updateCar(forum);
        });
        actionButton.setText("Update");
    }

    private void updateCar(Forum forum) {
        forum.setForumTitle(forumTitleField.getText());
        forumHib.updateForum(forum);
    }

    public void cancelForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) forumTitleField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }
}
