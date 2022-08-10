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

public class AddPartController implements Initializable {
    public RadioButton in_house_radio;
    public RadioButton outsourced_radio;
    public Label machine_company;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void set_fields(Part part){
        part.setId(IdCreator.generate());
        String newName = nameField.getText();
        part.setName(newName);
        int newInv = Integer.parseInt(invField.getText());
        part.setStock(newInv);
        Double newPrice = Double.parseDouble(priceField.getText());
        part.setPrice(newPrice);
        int newMax = Integer.parseInt(maxField.getText());
        part.setMax(newMax);
        int newMin = Integer.parseInt(minField.getText());
        part.setMin(newMin);
    }

    public void on_in_house(ActionEvent actionEvent) {
        machine_company.setText("Machine ID");
    }
    public void on_outsourced(ActionEvent actionEvent) {
        machine_company.setText("Company Name");
    }
    public void on_save(ActionEvent actionEvent) throws IOException {
    if (add_part_toggle.getSelectedToggle() == outsourced_radio) {
        Outsourced newPart = new Outsourced(0, "none", 0.00, 0, 0, 0, "none");
        set_fields(newPart);
        String newCompany = toggleField.getText();
        newPart.setCompanyName(newCompany);
        Inventory.addPart(newPart);
    } else if (add_part_toggle.getSelectedToggle() == in_house_radio) {
        InHouse newPart = new InHouse(0, "none", 0.00, 0, 0, 0, 0000);
        set_fields(newPart);
        int newMachineId = Integer.parseInt(toggleField.getText());
        newPart.setMachineid(newMachineId);
        Inventory.addPart(newPart);
    }
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
