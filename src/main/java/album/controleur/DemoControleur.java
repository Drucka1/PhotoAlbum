package album.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DemoControleur {

    @FXML
    private Label versionLabel;

    @FXML
    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

        versionLabel.setText("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
    }

}