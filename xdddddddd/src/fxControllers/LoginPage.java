package fxControllers;

import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginPage {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public CheckBox managerCheck;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckersMP");
    UserHib userHib = new UserHib(entityManagerFactory);

    public void validate() throws IOException {
        User user = userHib.getUserByLoginData(loginField.getText(), passwordField.getText(), managerCheck.isSelected());
        if(user != null){
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
            Parent parent = fxmlLoader.load();
            MainPage mainpage = fxmlLoader.getController();
            mainpage.setData(entityManagerFactory, user);

            Scene scene = new Scene(parent);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("TruckersMP");
            stage.setScene(scene);
            stage.show();
        } else {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User login report", "No such user or wrong credentials");
            }
        }

    public void openRegistration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationPage registrationPage = fxmlLoader.getController();
        registrationPage.setDataRegister(entityManagerFactory);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }
}
