package emersonlebleu.c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button to_addPart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void exitbuttonclick(ActionEvent actionEvent) {
        System.out.println("Exit Button Clicked");
    }

    public void to_add_part(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_part_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 488.0 , 463.0);
        stage.setTitle("IMS: Add Part");
        stage.setScene(scene);

        stage.show();
    }

    public void to_modify_part(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("modify_part_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 488.0 , 463.0);
        stage.setTitle("IMS: Modify Part");
        stage.setScene(scene);

        stage.show();
    }
}