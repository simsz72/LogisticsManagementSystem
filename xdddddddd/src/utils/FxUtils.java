package utils;

import javafx.scene.control.Alert;

public class FxUtils {
    public static void generateAlert(Alert.AlertType alertType, String header, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TruckersMp");
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
