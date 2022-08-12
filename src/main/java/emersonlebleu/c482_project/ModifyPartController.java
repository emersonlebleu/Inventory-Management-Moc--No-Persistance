package emersonlebleu.c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller class for the modify_part_view. */
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

    /** Creates a selected part instance. Will be loaded with data from the main controller selectedPart. */
    private Part selectedPart = null;

    /** Sets fields to values from the main controller's selectedPart. */
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

    /** Sets text of the toggle field to machine ID. */
    public void on_in_house(ActionEvent actionEvent) {
        machine_company.setText("Machine ID");
    }
    /** Sets text of the toggle field to company name */
    public void on_outsourced(ActionEvent actionEvent) {
        machine_company.setText("Company Name");
    }

    /** Sets the fields of a product. This assures that they are the correct type does the proper casting where needed.
     * @param part is a part to set
     * @return boolean if all successful then true if not false */
    public boolean set_fields(Part part){
        boolean pass = true;

        int newId = Integer.parseInt(idField.getText());
        part.setId(newId);

        try {
            String newName = nameField.getText();
            nameField.setStyle("-fx-text-fill: black;");
            part.setName(newName);
        } catch (Exception e) {
            nameField.setText("Expected: String");
            nameField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            int newInv = Integer.parseInt(invField.getText());
            invField.setStyle("-fx-text-fill: black;");
            part.setStock(newInv);
        } catch (Exception e) {
            invField.setText("Expected: Integer");
            invField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            Double newPrice = Double.parseDouble(priceField.getText());
            priceField.setStyle("-fx-text-fill: black;");
            part.setPrice(newPrice);
        } catch (Exception e) {
            priceField.setText("Expected: Double");
            priceField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            int newMax = Integer.parseInt(maxField.getText());
            maxField.setStyle("-fx-text-fill: black;");
            part.setMax(newMax);
        } catch (Exception e) {
            maxField.setText("Expected: Integer");
            maxField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            int newMin = Integer.parseInt(minField.getText());
            minField.setStyle("-fx-text-fill: black;");
            part.setMin(newMin);
        } catch (Exception e) {
            minField.setText("Expected: Integer");
            minField.setStyle("-fx-text-fill: red;");
            pass = false;
        }
        return pass;
    }

    /** Validates the min max inventory relationship for a product.
     * @param part a product to be validated
     * @return a boolean for validation of success */
    private boolean valMinMaxInv(Part part){
        boolean pass = true;
        boolean minVer = true;
        boolean invVer = true;

        if (part.getMin() >= part.getMax()){
            minVer = false;
            pass = false;
        }

        if (part.getStock() >= part.getMax() || part.getStock() <= part.getMin()){
            pass = false;
            invVer = false;
        }

        if (minVer == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min/Inventory/Max Error");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("The Min must be less than Max.");
            alert.showAndWait();
        } else if (invVer == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Min/Inventory/Max Error");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Inventory must be between Min and Max.");
            alert.showAndWait();
        }
        return pass;
    }

    /** Updates the selected part with fields from view as data. Stores the appropriate information into the fields of selectedPart depending on which type of part it is and which type of part is now selected, loads the main view. */
    public void on_save(ActionEvent actionEvent) throws IOException {
        boolean pass = true;
        //Checks if a part has changed from outsourced or in house and performs re-assignment
        if (selectedPart instanceof Outsourced && add_part_toggle.getSelectedToggle() == outsourced_radio) {
            set_fields(selectedPart);
            String newCompany = toggleField.getText();
            ((Outsourced) selectedPart).setCompanyName(newCompany);

            if (set_fields(selectedPart) && valMinMaxInv(selectedPart)) {} else { return; }

        } else if (selectedPart instanceof InHouse && add_part_toggle.getSelectedToggle() == in_house_radio) {
            set_fields(selectedPart);

            try {
                int newMachineId = Integer.parseInt(toggleField.getText());
                toggleField.setStyle("-fx-text-fill: black;");
                ((InHouse) selectedPart).setMachineid(newMachineId);
            } catch (Exception e) {
                toggleField.setText("Expected: Integer");
                toggleField.setStyle("-fx-text-fill: red;");
                pass = false;
            }

            if (set_fields(selectedPart) && (pass) && valMinMaxInv(selectedPart)) {} else { return; }

        } else if (selectedPart instanceof InHouse && add_part_toggle.getSelectedToggle() == outsourced_radio) {
            Outsourced changedPart = new Outsourced(0, "none", 0.00, 0, 0, 0, "none");
            set_fields(changedPart);
            String newCompany = toggleField.getText();
            changedPart.setCompanyName(newCompany);

            if (set_fields(changedPart) && valMinMaxInv(changedPart)) {
                Inventory.deletePart(selectedPart);
                Inventory.addPart(changedPart);
            } else { return; }

        } else if (selectedPart instanceof Outsourced && add_part_toggle.getSelectedToggle() == in_house_radio) {
            InHouse changedPart = new InHouse(0, "none", 0.00, 0, 0, 0, 0000);
            set_fields(changedPart);

            try {
                int newMachineId = Integer.parseInt(toggleField.getText());
                toggleField.setStyle("-fx-text-fill: black;");
                changedPart.setMachineid(newMachineId);
            } catch (Exception e) {
                toggleField.setText("Expected: Integer");
                toggleField.setStyle("-fx-text-fill: red;");
                pass = false;
            }

            if (set_fields(changedPart) && (pass) && valMinMaxInv(changedPart)) {
                Inventory.addPart(changedPart);
                Inventory.deletePart(selectedPart);
            } else { return; }
        }

        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }

    /** Returns to the main screen. */
    public void on_cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
}
