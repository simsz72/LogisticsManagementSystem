package fxControllers;

import hibernate.CarHib;
import hibernate.CargoHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Car;
import model.Cargo;
import model.Destination;
import model.User;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CargoPage {

    @FXML
    public TextField cargoNameField;
    @FXML
    public TextField cargoHeightField;
    @FXML
    public TextField cargoWeightField;
    @FXML
    public DatePicker cargoPickupDate;
    @FXML
    public DatePicker cargoDeliveryDate;
    @FXML
    public Button actionButton;

    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private CargoHib cargoHib;
    private Cargo selectedCargo;
    private Destination destination;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.cargoHib = new CargoHib(entityManagerFactory);
    }

    public void setDataEdit(EntityManagerFactory entityManagerFactory, User currentUser, Cargo selectedCargo) {
        this.currentUser = currentUser;
        this.selectedCargo = selectedCargo;
        this.entityManagerFactory = entityManagerFactory;
        this.cargoHib = new CargoHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Cargo cargo = (Cargo) cargoHib.getCargoById(selectedCargo.getId());
        cargoNameField.setText(cargo.getCargoName());
        cargoWeightField.setText(cargo.getWeight());
        cargoHeightField.setText(cargo.getHeight());

        actionButton.setOnAction(actionEvent -> {
            updateCargo(cargo);
        });
        actionButton.setText("Update");
    }

    private void updateCargo(Cargo cargo) {
        cargo.setCargoName(cargoNameField.getText());
        cargo.setHeight(cargoHeightField.getText());
        cargo.setWeight(cargoWeightField.getText());
        cargoHib.updateCargo(cargo);
    }

    public void cancelCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) cargoHeightField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void validateCargo() throws IOException {
        Cargo cargo = new Cargo(cargoHeightField.getText(), cargoWeightField.getText(), cargoNameField.getText());
        cargoHib.createCargo(cargo);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) cargoHeightField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }
}
