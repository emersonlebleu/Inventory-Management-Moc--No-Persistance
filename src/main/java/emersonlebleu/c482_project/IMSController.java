package emersonlebleu.c482_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IMSController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}