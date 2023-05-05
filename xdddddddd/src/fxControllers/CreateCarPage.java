package fxControllers;

import hibernate.CarHib;
import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Car;
import model.Driver;
import model.Role;
import model.User;
import org.hibernate.annotations.Check;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

public class CreateCarPage {
    @FXML
    public TextField plateNumberField;
    @FXML
    public TextField carModelField;
    @FXML
    public TextField weightField;
    @FXML
    public TextField heightField;
    @FXML
    public TextField carDriverField;
    @FXML
    public CheckBox isDamagedCheck;
    @FXML
    public Button actionButton;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private CarHib carHib;
    private Driver selectedDriver;
    private Car selectedCar;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.carHib = new CarHib(entityManagerFactory);
    }

    public void setDataEdit(EntityManagerFactory entityManagerFactory, User currentUser, Car selectedCar) {
        this.currentUser = currentUser;
        this.selectedCar = selectedCar;
        this.entityManagerFactory = entityManagerFactory;
        this.carHib = new CarHib(entityManagerFactory);
        fillFields();
    }
    public void cancelCar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) carDriverField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void validateCar() throws IOException {
        Car car = new Car(plateNumberField.getText(), carModelField.getText(), weightField.getText(), heightField.getText(), null, false, null);
        carHib.createCar(car);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) carDriverField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    private void fillFields() {
        Car car = (Car) carHib.getCarById(selectedCar.getId());
        plateNumberField.setText(car.getPlateNumber());
        carModelField.setText(car.getCarModel());
        weightField.setText(car.getWeight());
        //like
        actionButton.setOnAction(actionEvent -> {
            updateCar(car);
        });
        actionButton.setText("Update");
    }

    private void updateCar(Car car) {
        car.setPlateNumber(plateNumberField.getText());
        carHib.updateCar(car);
    }

}
