package fxControllers;

import hibernate.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import org.hibernate.annotations.Check;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML
    public ListView<DriverManager> managerList;
    @FXML
    public ListView<Driver> driverList;
    @FXML
    public Tab userTab;
    @FXML
    public TabPane allTabs;
    @FXML
    public Tab carTab;
    @FXML
    public ListView<Car> carList;
    @FXML
    public ListView<Cargo> cargoList;
    @FXML
    public ListView<Comment> commentList;
    @FXML
    public ListView<Destination> destinationList;
    @FXML
    public ListView<Forum> forumList;
    @FXML
    public ListView<Checkpoint> checkpointList;
    public ComboBox<Driver> driverComboBox;
    public ComboBox<Car> carComboBox;
    public ComboBox<Status> statusComboBox;
    @FXML
    public Button actionButton;
    @FXML
    public Tab commentTab;
    public ListView<Destination>userDestinationList;
    public Button userUpdateButton;
    public Button userDeleteButton;
    public Tab cargoTab;
    public Button createDestinationButton;
    public Button updateDestinationButton;
    public Button deleteDestinationButton;
    public Button deleteForumButton;
    public Button updateForumButton;
    public Button myProfileButton;
    private List<Destination> destinations;
    @FXML
    private BarChart<String, Integer> chart;
    //----------------------------
    private List<Car> carFilterList;
    private List<Driver> driverFilterList;
    //-------------------------------
    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private CarHib carHib;
    private CargoHib cargoHib;
    private CommentHib commentHib;
    private DestinationHib destinationHib;
    private ForumHib forumHib;
    private CheckpointHib checkpointHib;
    private User user;
    private Role role;
    private DriverManager manager;
    private Driver driver;
    public void setData (EntityManagerFactory entityManagerFactory, User user){
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
        this.carHib = new CarHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.commentHib = new CommentHib(entityManagerFactory);
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.forumHib = new ForumHib(entityManagerFactory);
        this.checkpointHib = new CheckpointHib(entityManagerFactory);
        this.user = user;
        if(user.getClass().equals(DriverManager.class)){
            manager = (DriverManager) user;
        }
        else if(user.getClass().equals(Driver.class)){
            driver = (Driver) user;
        }
        fillAllLists();
        fillComboBox();
        disableData();
    }


    private void disableData() {
        if(user.getRole() == Role.DRIVERMANAGER){
            managerList.setVisible(false);
            allTabs.getTabs().remove(commentTab);
            destinationList.setVisible(false);
            deleteForumButton.setVisible(false);
            updateForumButton.setVisible(false);
            userUpdateButton.setVisible(false);
            userDeleteButton.setVisible(false);
        }
        else if(user.getRole() == Role.DRIVER){
            managerList.setVisible(false);
            driverList.setVisible(false);
            allTabs.getTabs().remove(carTab);
            allTabs.getTabs().remove(commentTab);
            allTabs.getTabs().remove(cargoTab);
            destinationList.setVisible(false);
            userDeleteButton.setVisible(false);
            userUpdateButton.setVisible(false);
            deleteDestinationButton.setVisible(false);
            updateDestinationButton.setVisible(false);
            createDestinationButton.setVisible(false);
            driverComboBox.setDisable(true);
            deleteForumButton.setVisible(false);
            updateForumButton.setVisible(false);
        }
        else{
            userDestinationList.setVisible(false);
        }
    }

    private void fillAllLists() {
        destinations = destinationHib.getAllDestinations();

        List<Driver> allDrivers = userHib.getAllDrivers();
        allDrivers.forEach(d -> driverList.getItems().add(d));
        if(user.getRole() == Role.DRIVER){
            for(Destination d : destinations){
                if(d.getDriver().getId() == user.getId()){
                    userDestinationList.getItems().add(d);
                }
            }
        }
        else if(user.getRole() == Role.DRIVERMANAGER) {
            for(Destination d : destinations) {
                for (int i = 0; i < d.getManager().size(); i++) {
                    if (d.getManager().get(i).getId() == user.getId()) {
                        userDestinationList.getItems().add(d);
                    }
                }
            }
        }
        List<Car> allCars = carHib.getAllCars();
        allCars.forEach(c -> carList.getItems().add(c));
        List<Cargo> allCargos = cargoHib.getAllCargos();
        allCargos.forEach(c -> cargoList.getItems().add(c));
        List<Comment> allComments = commentHib.getAllComments();
        allComments.forEach(d -> commentList.getItems().add(d));
        List<Destination> allDestinations= destinationHib.getAllDestinations();
        allDestinations.forEach(d -> destinationList.getItems().add(d));
        List<DriverManager> allDriverManagers = userHib.getAllDriverManagers();
        allDriverManagers.forEach(d -> managerList.getItems().add(d));
        List<Forum> allForums = forumHib.getAllForums();
        allForums.forEach(f -> forumList.getItems().add(f));
        List<Checkpoint> allCheckpoints = checkpointHib.getAllCheckpoints();
        allCheckpoints.forEach(c -> checkpointList.getItems().add(c));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void showUserDetails() {
    }

    public void deleteUser() throws IOException {
        userHib.removeUser(driverList.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void updateUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationPage registrationPage = fxmlLoader.getController();
        if(myProfileButton.isHover())
        {registrationPage.setData(entityManagerFactory, user, this.driver);}
        else
        {registrationPage.setData(entityManagerFactory, user, driverList.getSelectionModel().getSelectedItem());}

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void deleteCar() throws IOException {
        carHib.removeCar(carList.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void updateCar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/create-car-page.fxml"));
        Parent parent = fxmlLoader.load();
        CreateCarPage createCarPage = fxmlLoader.getController();
        createCarPage.setDataEdit(entityManagerFactory, user, carList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void createCar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/create-car-page.fxml"));
        Parent parent = fxmlLoader.load();
        CreateCarPage createCarPage = fxmlLoader.getController();
        createCarPage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void showCarDetails() {
    }

    public void deleteCargo() throws IOException {
        cargoHib.removeCargo(cargoList.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void updateCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/cargo-page.fxml"));
        Parent parent = fxmlLoader.load();
        CargoPage cargoPage = fxmlLoader.getController();
        cargoPage.setDataEdit(entityManagerFactory, user, cargoList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void createCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/cargo-page.fxml"));
        Parent parent = fxmlLoader.load();
        CargoPage CargoPage = fxmlLoader.getController();
        CargoPage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void showCargoDetails() {
    }

    public void deleteComment() throws IOException {
        commentHib.removeComment(commentList.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void updateComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/comment-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentPage commentPage = fxmlLoader.getController();
        commentPage.setDataEdit(entityManagerFactory, user, commentList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/comment-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentPage commentpage = fxmlLoader.getController();
        commentpage.setData(entityManagerFactory, user, null);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }


    public void showCommentDetails() {
    }

    public void deleteDestination() throws IOException {
        if(user.getRole() == Role.ADMIN){
            if (destinationList.getSelectionModel().getSelectedItem().getStatus() == Status.ONGOING) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Error", "You cannot delete ongoing destinations");
            }
        }
        else if(user.getRole() != Role.ADMIN){
            if (userDestinationList.getSelectionModel().getSelectedItem().getStatus() == Status.ONGOING) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Error", "You cannot delete ongoing destinations");
            }
        }
        else{
            destinationHib.removeDestination(destinationList.getSelectionModel().getSelectedItem());
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
            Parent parent = fxmlLoader.load();
            MainPage mainpage = fxmlLoader.getController();
            mainpage.setData(entityManagerFactory, user);

            Scene scene = new Scene(parent);
            Stage stage = (Stage) managerList.getScene().getWindow();
            stage.setTitle("TruckersMP");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void updateDestination() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/destination-page.fxml"));
        Parent parent = fxmlLoader.load();
        DestinationPage destinationPage = fxmlLoader.getController();
        if(user.getRole() != Role.ADMIN)
            destinationPage.setDataEdit(entityManagerFactory, user, userDestinationList.getSelectionModel().getSelectedItem(), carHib, userHib, checkpointHib, cargoHib);
        else
            destinationPage.setDataEdit(entityManagerFactory, user, destinationList.getSelectionModel().getSelectedItem(), carHib, userHib, checkpointHib, cargoHib);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createDestination() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/destination-page.fxml"));
        Parent parent = fxmlLoader.load();
        DestinationPage destinationPage = fxmlLoader.getController();
        destinationPage.setData(entityManagerFactory, user, carHib, userHib, checkpointHib, cargoHib);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void showDestinationDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/destination-page.fxml"));
        Parent parent = fxmlLoader.load();
        DestinationPage destinationPage = fxmlLoader.getController();
        if(user.getRole() != Role.ADMIN)
            destinationPage.setDataEdit(entityManagerFactory, user, userDestinationList.getSelectionModel().getSelectedItem(), carHib, userHib, checkpointHib, cargoHib);
        else
            destinationPage.setDataEdit(entityManagerFactory, user, destinationList.getSelectionModel().getSelectedItem(), carHib, userHib, checkpointHib, cargoHib);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteForum() throws IOException {
        forumHib.removeForum(forumList.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void updateForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/forum-page.fxml"));
        Parent parent = fxmlLoader.load();
        ForumPage forumPage = fxmlLoader.getController();
        forumPage.setDataEdit(entityManagerFactory, user, forumList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/forum-page.fxml"));
        Parent parent = fxmlLoader.load();
        ForumPage forumPage = fxmlLoader.getController();
        forumPage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void showForumDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/chosen-forum-page.fxml"));
        Parent parent = fxmlLoader.load();
        ChosenForumPage chosenForumPage = fxmlLoader.getController();
        chosenForumPage.setData(entityManagerFactory, user, forumList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void deleteCheckpoint() throws IOException {
        checkpointHib.removeCheckpoint(checkpointList.getSelectionModel().getSelectedItem());
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public void updateCheckpoint() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/checkpoint-page.fxml"));
        Parent parent = fxmlLoader.load();
        CheckpointPage checkpointPage = fxmlLoader.getController();
        checkpointPage.setDataEdit(entityManagerFactory, user, checkpointList.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void createCheckpoint() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/checkpoint-page.fxml"));
        Parent parent = fxmlLoader.load();
        CheckpointPage checkpointPage = fxmlLoader.getController();
        checkpointPage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(managerList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showCheckpointDetails() {
    }

    public void displayChart() {
        Destination destination;
        if(user.getRole() != Role.ADMIN)
        {
            destination = userDestinationList.getSelectionModel().getSelectedItem();
        }
        else
        {
            destination = destinationList.getSelectionModel().getSelectedItem();
        }
        List<DriverManager> driverManagers = destination.getManager();
        int size1 = driverManagers.size();
        List<Cargo> cargo = destination.getCargo();
        int size2 = cargo.size();
        List<Checkpoint> checkpoint = destination.getCheckpoints();
        int size3 = checkpoint.size();
        chart.getData().clear();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Pending");
        series1.getData().add(new XYChart.Data("Driver Managers", size1));
        series1.getData().add(new XYChart.Data("Cargos", size2));
        series1.getData().add(new XYChart.Data("Checkpoints", size3));

        chart.getData().addAll(series1);
    }

    public void refreshPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
        Parent parent = fxmlLoader.load();
        MainPage mainpage = fxmlLoader.getController();
        mainpage.setData(entityManagerFactory, user);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) managerList.getScene().getWindow();
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public List<Destination> getDestinationsWithFilter(List<Destination> destinations, Driver driver, Car car, Status status){
        List<Destination> result = new ArrayList<>();

        for(Destination d : destinations){
            if((status == null || d.getStatus().equals(status)) && (driver == null || d.getDriver().getId() == driver.getId()) && (car == null || d.getCar().getId() == car.getId())){
                result.add(d);
            }
        }
        return result;
    }

    private void fillComboBox(){
        driverFilterList = userHib.getAllDrivers();
        carFilterList = carHib.getAllCars();

        driverComboBox.getItems().setAll(driverFilterList);
        statusComboBox.getItems().setAll(Status.values());
        carComboBox.getItems().setAll(carFilterList);
        actionButton.setOnAction(actionEvent -> {
            if(user.getRole() == Role.DRIVER){
                userDestinationList.getItems().setAll(getDestinationsWithFilter(driver.getDriverDestination(), driver, carComboBox.getValue(), statusComboBox.getValue()));
            }
            else if(user.getRole() == Role.DRIVERMANAGER) {
                userDestinationList.getItems().setAll(getDestinationsWithFilter(manager.getDriverManagerDestination(), driverComboBox.getValue(), carComboBox.getValue(), statusComboBox.getValue()));
            }
            else{
                destinationList.getItems().setAll(getDestinationsWithFilter(destinationHib.getAllDestinations(), driverComboBox.getValue(), carComboBox.getValue(), statusComboBox.getValue()));
            }
        });
    }
}
