package fxControllers;

import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Driver;
import model.DriverManager;
import model.Role;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class RegistrationPage {
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField loginField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button actionButton;
    public CheckBox isManagerRegister;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private UserHib userHib;
    private Driver selectedDriver;
    //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckersMP");
    //UserHib userHib = new UserHib(entityManagerFactory);


    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Driver selectedDriver) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedDriver = selectedDriver;
        this.userHib = new UserHib(entityManagerFactory);
        fillFields();
        isManagerRegister.setDisable(true);
    }

    private void fillFields() {
        Driver driver = (Driver) userHib.getDriverById(selectedDriver.getId());
        loginField.setText(driver.getLogin());
        passwordField.setText(driver.getPassword());
        nameField.setText(driver.getName());
        surnameField.setText(driver.getSurname());
        //like
        actionButton.setOnAction(actionEvent -> {
            updateUser(driver);
        });
        actionButton.setText("Update");
    }
    private void updateUser(Driver driver) {
        driver.setLogin(loginField.getText());
        driver.setPassword(passwordField.getText());
        driver.setName(nameField.getText());
        driver.setSurname(surnameField.getText());
        userHib.updateUser(driver);
    }

    public void cancelSignUp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setDataRegister(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
    }

    public void validateSignUp() throws IOException {
        if (loginField.getText().equals("") || passwordField.getText().equals(""))
        {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Registration error", "Please fill at least login and password");
        }
        else{
            if(isManagerRegister.isSelected() && loginField.getText().equals("admin") && passwordField.getText().equals("admin")){
                DriverManager driverManager = new DriverManager(Role.ADMIN, loginField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), birthDateField.getValue(),
                        null, null, null, null, null, true);
                userHib.createUser(driverManager);
            }
            else if(isManagerRegister.isSelected()) {
                DriverManager driverManager = new DriverManager(Role.DRIVERMANAGER, loginField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), birthDateField.getValue(),
                        null, null, null, null, null, false);
                userHib.createUser(driverManager);

            }
            else {
                Driver driver = new Driver(Role.DRIVER, loginField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneField.getText(), emailField.getText(), birthDateField.getValue(),
                        null, null, null, false);
                userHib.createUser(driver);

            }
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/login-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
