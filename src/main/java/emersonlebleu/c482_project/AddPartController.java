package emersonlebleu.c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller class for the add_part_view. */
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

    /** Initializer for add part allows user to see ID generated. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainController.generate();
        idField.setText(String.valueOf(MainController.currId));
    }

    /** Sets the fields of a part. This assures that they are the correct type does the proper casting where needed or displays error.
     * @param part is a part to set
     * @return boolean if all successful then true if not false */
    public boolean set_fields(Part part){
        boolean pass = true;
        part.setId(MainController.currId);

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

    /** Sets text of the toggle field to machine ID. */
    public void on_in_house(ActionEvent actionEvent) {
        machine_company.setText("Machine ID");
    }
    /** Sets text of the toggle field to company name */
    public void on_outsourced(ActionEvent actionEvent) {
        machine_company.setText("Company Name");
    }

    /** Adds a new part with fields from view as data. Creates a new part, stores the appropriate information into the fields, loads the main view, displays messages if incorrect input is given. */
    public void on_save(ActionEvent actionEvent) throws IOException {
        boolean pass = true;
    if (add_part_toggle.getSelectedToggle() == outsourced_radio) {
        Outsourced newPart = new Outsourced(0, "none", 0.00, 0, 0, 0, "none");
        set_fields(newPart);
        String newCompany = toggleField.getText();
        newPart.setCompanyName(newCompany);

        if (set_fields(newPart) && valMinMaxInv(newPart)) {
            Inventory.addPart(newPart);
        } else { return; }
    } else if (add_part_toggle.getSelectedToggle() == in_house_radio) {
        InHouse newPart = new InHouse(0, "none", 0.00, 0, 0, 0, 0000);
        set_fields(newPart);

        try {
            int newMachineId = Integer.parseInt(toggleField.getText());
            toggleField.setStyle("-fx-text-fill: black;");
            newPart.setMachineid(newMachineId);
        } catch (Exception e) {
            toggleField.setText("Expected: Integer");
            toggleField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        if (set_fields(newPart) && (pass) && valMinMaxInv(newPart)) {
            Inventory.addPart(newPart);
        } else { return; }
    }
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }

    /** Returns to the main screen. Reloads main screen and resets the id counter to the previous state. */
    public void on_cancel(ActionEvent actionEvent) throws IOException {
        MainController.currId -= 1;

        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }
}
