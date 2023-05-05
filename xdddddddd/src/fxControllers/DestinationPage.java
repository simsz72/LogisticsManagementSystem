package fxControllers;

import hibernate.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import org.hibernate.annotations.Check;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public class DestinationPage {
    public Button actionButton;
    public TextField destinationAddressField;
    public ComboBox<Car> carBox;
    public ComboBox<Checkpoint> checkpointBox;
    public ComboBox<Driver> driverBox;
    public ComboBox<DriverManager> managerBox;
    public ComboBox<Cargo> cargoBox;
    public ComboBox<Status> statusBox;
    public ListView<DriverManager> destinationManagerList;
    public ListView<Checkpoint> destinationCheckpointList;
    public ListView<Cargo> destinationCargoList;
    public DatePicker departureDate;
    public DatePicker arrivalDate;
    public Button deleteCargoButton;
    public Button addCargoButton;
    public Button deleteCheckpointButton;
    public Button addCheckpointButton;
    public Button deleteManagerButton;
    public Button addManagerButton;

    private User currentUser;
    private EntityManagerFactory entityManagerFactory;
    private DestinationHib destinationHib;
    private Destination selectedDestination;
    //-------------------------------
    private List<Car> carList;
    private List<Checkpoint> checkpointList;
    private List<Driver> driverList;
    private List<DriverManager> driverManagerList;
    private List<Cargo> cargoList;
    //---------------------------------
    private CarHib carHib;
    private UserHib userHib;
    private CheckpointHib checkpointHib;
    private CargoHib cargoHib;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, CarHib carHib, UserHib userHib, CheckpointHib checkpointHib, CargoHib cargoHib) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.carHib = carHib;
        this.userHib = userHib;
        this.checkpointHib = checkpointHib;
        this.cargoHib = cargoHib;
        destinationManagerList.getItems().add((DriverManager) currentUser);
        fillComboBox();
    }

    public void setDataEdit(EntityManagerFactory entityManagerFactory, User currentUser, Destination selectedDestination,
                            CarHib carHib, UserHib userHib, CheckpointHib checkpointHib, CargoHib cargoHib) {
        this.currentUser = currentUser;
        this.selectedDestination = selectedDestination;
        this.entityManagerFactory = entityManagerFactory;
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.carHib = carHib;
        this.userHib = userHib;
        this.checkpointHib = checkpointHib;
        this.cargoHib = cargoHib;
        fillComboBox();
        fillFields();
        disableData();
    }

    private void disableData() {
        if(currentUser.getRole() == Role.DRIVER){
            destinationAddressField.setDisable(true);
            cargoBox.setDisable(true);
            driverBox.setDisable(true);
            statusBox.setDisable(true);
            departureDate.setDisable(true);
            arrivalDate.setDisable(true);
            managerBox.setDisable(true);
            destinationManagerList.setDisable(true);
            destinationCargoList.setDisable(true);
            addCargoButton.setDisable(true);
            addManagerButton.setDisable(true);
            deleteCargoButton.setDisable(true);
            deleteManagerButton.setDisable(true);
        }
    }


    private void fillFields() {
        Destination destination = (Destination) destinationHib.getDestinationById(selectedDestination.getId());
        destinationAddressField.setText(destination.getPlaceAddress());
        carBox.getSelectionModel().select(destination.getCar());
        driverBox.getSelectionModel().select(destination.getDriver());
        statusBox.getSelectionModel().select(destination.getStatus());
        arrivalDate.setValue(destination.getArrivalDate());
        departureDate.setValue(destination.getDepartureDate());

        destinationManagerList.getItems().setAll(destination.getManager());
        destinationCheckpointList.getItems().setAll(destination.getCheckpoints());
        destinationCargoList.getItems().setAll(destination.getCargo());

        actionButton.setOnAction(actionEvent -> {
            updateDestination(destination);
        });
        actionButton.setText("Update");
    }

    private void updateDestination(Destination destination) {
        destination.setPlaceAddress(destinationAddressField.getText());
        destination.setCar(carBox.getValue());
        destination.setDriver(driverBox.getValue());
        destination.setManager(destinationManagerList.getItems());
        destination.setCheckpoints(destinationCheckpointList.getItems());
        destination.setCargo(destinationCargoList.getItems());
        destination.setStatus(statusBox.getValue());
        destination.setDepartureDate(departureDate.getValue());
        destination.setArrivalDate(arrivalDate.getValue());
        destinationHib.updateDestination(destination);
    }

    public void cancelDestination() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) destinationAddressField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void validateDestination() throws IOException {
        Destination destination = new Destination(destinationAddressField.getText(), departureDate.getValue(), arrivalDate.getValue(), carBox.getValue(), destinationCheckpointList.getItems(), driverBox.getValue(), destinationManagerList.getItems(), destinationCargoList.getItems(), statusBox.getValue());
        destinationHib.createDestination(destination);
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, currentUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) destinationAddressField.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    private void fillComboBox(){
        driverManagerList = userHib.getAllDriverManagers();
        driverList = userHib.getAllDrivers();
        carList = carHib.getAllCars();
        cargoList = cargoHib.getAllCargos();
        checkpointList = checkpointHib.getAllCheckpoints();


        managerBox.getItems().setAll(driverManagerList);
        driverBox.getItems().setAll(driverList);
        carBox.getItems().setAll(carList);
        cargoBox.getItems().setAll(cargoList);
        checkpointBox.getItems().setAll(checkpointList);
        statusBox.getItems().setAll(Status.values());
    }

    public void deleteCheckpoint() {
        destinationCheckpointList.getItems().remove(destinationCheckpointList.getSelectionModel().getSelectedIndex());
    }

    public void addCheckpoint() {
        destinationCheckpointList.getItems().add(checkpointBox.getSelectionModel().getSelectedItem());
    }

    public void deleteManager() {
        destinationManagerList.getItems().remove(destinationManagerList.getSelectionModel().getSelectedIndex());
    }

    public void addManager() {
        destinationManagerList.getItems().add(managerBox.getSelectionModel().getSelectedItem());
    }

    public void addCargo() {
        destinationCargoList.getItems().add(cargoBox.getSelectionModel().getSelectedItem());
    }

    public void deleteCargo() {
        destinationCargoList.getItems().remove(destinationCargoList.getSelectionModel().getSelectedIndex());
    }
}
