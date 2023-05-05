package fxControllers;

import hibernate.CheckpointHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Checkpoint;
import model.User;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CheckpointPage {
    @FXML
    public TextField checkpointAddressField;
    @FXML
    public DatePicker checkpointArrivalDate;
    @FXML
    public DatePicker checkpointDepartureDate;
    @FXML

    public Button actionButton;

    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private CheckpointHib checkpointHib;
    private Checkpoint selectedCheckpoint;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.checkpointHib = new CheckpointHib(entityManagerFactory);
    }

    public void setDataEdit(EntityManagerFactory entityManagerFactory, User currentUser, Checkpoint selectedCheckpoint) {
        this.currentUser = currentUser;
        this.selectedCheckpoint = selectedCheckpoint;
        this.entityManagerFactory = entityManagerFactory;
        this.checkpointHib = new CheckpointHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Checkpoint checkpoint = (Checkpoint) checkpointHib.getCheckpointById(selectedCheckpoint.getId());
        checkpointAddressField.setText(checkpoint.getCheckpointAddress());
        //like
        actionButton.setOnAction(actionEvent -> {
            updateCheckpoint(checkpoint);
        });
        actionButton.setText("Update");
    }

    private void updateCheckpoint(Checkpoint checkpoint) {
        checkpoint.setCheckpointAddress(checkpointAddressField.getText());
        checkpointHib.updateCheckpoint(checkpoint);
    }

    public void cancelCheckpoint() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) checkpointAddressField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void validateCheckpoint() throws IOException {
        Checkpoint checkpoint = new Checkpoint(checkpointAddressField.getText(), checkpointArrivalDate.getValue(), checkpointDepartureDate.getValue(), null);
        checkpointHib.createCheckpoint(checkpoint);
    }
}
