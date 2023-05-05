import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TruckersMP");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch();}
}
