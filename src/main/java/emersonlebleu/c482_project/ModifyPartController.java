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
            toggleField.setText(((Outsourced) selectedPart).getCompanyName());
        } else if (selectedPart instanceof InHouse) {
            add_part_toggle.selectToggle(in_house_radio);
            machine_company.setText("Machine ID");
            toggleField.setText(Integer.toString(((InHouse) selectedPart).getMachineid()));
        }

        idField.setText(Integer.toString(selectedPart.getId()));
        nameField.setText(selectedPart.getName());
        invField.setText(Integer.toString(selectedPart.getStock()));
        priceField.setText(Double.toString(selectedPart.getPrice()));
        maxField.setText(Integer.toString(selectedPart.getMax()));
        minField.setText(Integer.toString(selectedPart.getMin()));
    }

    public void on_in_house(ActionEvent actionEvent) {
        machine_company.setText("Machine ID");
        int newMachineId = Integer.parseInt(toggleField.getText());

    }

    public void on_outsourced(ActionEvent actionEvent) {
        machine_company.setText("Company Name");
        String newCompany = toggleField.getText();

    }
    private void set_fields(){
        int newId = Integer.parseInt(idField.getText());
        String newName = nameField.getText();
        selectedPart.setName(newName);
        int newInv = Integer.parseInt(invField.getText());
        selectedPart.setStock(newInv);
        Double newPrice = Double.parseDouble(priceField.getText());
        selectedPart.setPrice(newPrice);
        int newMax = Integer.parseInt(maxField.getText());
        selectedPart.setMax(newMax);
        int newMin = Integer.parseInt(minField.getText());
        selectedPart.setMin(newMin);
    }

    public void on_save(ActionEvent actionEvent) throws IOException {

        //Checks if a part has changed from outsourced or in house and performs re-assignment
        if (selectedPart instanceof Outsourced && add_part_toggle.getSelectedToggle() == outsourced_radio) {
            set_fields();
            String newCompany = toggleField.getText();
            ((Outsourced) selectedPart).setCompanyName(newCompany);
        } else if (selectedPart instanceof InHouse && add_part_toggle.getSelectedToggle() == in_house_radio) {
            set_fields();
            int newMachineId = Integer.parseInt(toggleField.getText());
            ((InHouse) selectedPart).setMachineid(newMachineId);
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
