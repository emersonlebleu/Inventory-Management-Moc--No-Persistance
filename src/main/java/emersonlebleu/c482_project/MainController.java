package emersonlebleu.c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("IMS initialized");
    }

    public void exitbuttonclick(ActionEvent actionEvent) {
        System.out.println("Exit Button Clicked");
    }
}