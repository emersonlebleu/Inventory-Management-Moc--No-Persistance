package emersonlebleu.c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    public ToggleGroup add_part_toggle;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField toggleField;
    public TextField minField;
    public Button save_btn;
    public Button cancel_btn;
    public RadioButton in_house_radio;
    public RadioButton outsourced_radio;
    public Label machine_company;
    private Part selectedPart = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainController.getSelectedPart();

        if (selectedPart instanceof Outsourced ) {
            add_part_toggle.selectToggle(outsourced_radio);
            machine_company.setText("Company Name");
        } else if (selectedPart instanceof InHouse) {
            add_part_toggle.selectToggle(in_house_radio);
            machine_company.setText("Machine ID");
        }
    }

    public void on_in_house(ActionEvent actionEvent) {
        machine_company.setText("Machine ID");
    }

    public void on_outsourced(ActionEvent actionEvent) {
        machine_company.setText("Company Name");
    }

    public void on_save(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
    public void on_cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
}
