package emersonlebleu.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class for the modify_product_view. */
public class ModifyProductController implements Initializable {
    public TableView allPartsTable;
    public TableColumn allPartsIdColumn;
    public TableColumn allPartsNameColumn;
    public TableColumn allPartsInventoryColumn;
    public TableColumn allPartsPriceColumn;
    public TableView thisPartTable;
    public TableColumn thisPartIdColumn;
    public TableColumn thisPartNameColumn;
    public TableColumn thisPartInventoryColumn;
    public TableColumn thisPartPriceColumn;
    public TextField partSearchBar;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;

    /** Selected product variable. Used to store the product being modified. */
    private Product selectedProduct = null;

    /** Selected part variable. Used to store the selected part from the allParts and thisPart tables. */
    private static Part selectedPart = null;

    /** Observable list of parts. Used to store the current product's list of parts. */
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();

    /** List of parts added. Used to revert product if modify is canceled. */
    private ObservableList<Part> partsAdded = FXCollections.observableArrayList();

    /** List of parts removed. Used to revert product if modify is canceled. */
    private ObservableList<Part> partsRemoved = FXCollections.observableArrayList();

    /** Sets up the fields and tables with appropriate information. Stores the product selected on main view into variable for this view populates the fields, and the tables with information. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = MainController.getSelectedProduct();

        idField.setText(Integer.toString(selectedProduct.getId()));
        nameField.setText(selectedProduct.getName());
        invField.setText(Integer.toString(selectedProduct.getStock()));
        priceField.setText(Double.toString(selectedProduct.getPrice()));
        maxField.setText(Integer.toString(selectedProduct.getMax()));
        minField.setText(Integer.toString(selectedProduct.getMin()));

        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        thisProductParts = selectedProduct.getAllAssociatedParts();
        thisPartTable.setItems(selectedProduct.getAllAssociatedParts());

        thisPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        thisPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        thisPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        thisPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Sets the fields of a product. This assures that they are the correct type does the proper casting where needed.
     * @param product a product to be set
     * @return boolean if all successful then true if not false */
    public boolean set_fields(Product product){
        boolean pass = true;

        int idNum = Integer.parseInt(idField.getText());
        product.setId(idNum);

        try {
            String newName = nameField.getText();
            nameField.setStyle("-fx-text-fill: black;");
            product.setName(newName);
        } catch (Exception e) {
            nameField.setText("Expected: String");
            nameField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            int newInv = Integer.parseInt(invField.getText());
            invField.setStyle("-fx-text-fill: black;");
            product.setStock(newInv);
        } catch (Exception e) {
            invField.setText("Expected: Integer");
            invField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            Double newPrice = Double.parseDouble(priceField.getText());
            priceField.setStyle("-fx-text-fill: black;");
            product.setPrice(newPrice);
        } catch (Exception e) {
            priceField.setText("Expected: Double");
            priceField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            int newMax = Integer.parseInt(maxField.getText());
            maxField.setStyle("-fx-text-fill: black;");
            product.setMax(newMax);
        } catch (Exception e) {
            maxField.setText("Expected: Integer");
            maxField.setStyle("-fx-text-fill: red;");
            pass = false;
        }

        try {
            int newMin = Integer.parseInt(minField.getText());
            minField.setStyle("-fx-text-fill: black;");
            product.setMin(newMin);
        } catch (Exception e) {
            minField.setText("Expected: Integer");
            minField.setStyle("-fx-text-fill: red;");
            pass = false;
        }
        return pass;
    }

    /** Validates the min max inventory relationship for a product.
     * @param product a product to be validated
     * @return a boolean for validation of success */
    private boolean valMinMaxInv(Product product){
        boolean pass = true;
        boolean minVer = true;
        boolean invVer = true;

        if (product.getMin() >= product.getMax()){
            minVer = false;
            pass = false;
        }

        if (product.getStock() >= product.getMax() || product.getStock() <= product.getMin()){
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

    /** Saves the information into selectedProduct and loads main. */
    public void on_save(ActionEvent actionEvent) throws IOException {
        set_fields(selectedProduct);
        if (set_fields(selectedProduct)) {
            if (valMinMaxInv(selectedProduct)) {
                Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                Scene scene = new Scene(root, 895.0, 395.0);
                stage.setTitle("IMS: Main");
                stage.setScene(scene);

                stage.show();
            }
        }
    }

    /** Returns to the main screen. Reverts the associated parts and loads the main screen. */
    public void on_cancel(ActionEvent actionEvent) throws IOException {
        for (Part part: partsAdded) {
            selectedProduct.deleteAssociatedPart(part);
        }
        for (Part part: partsRemoved) {
            selectedProduct.addAssociatedPart(part);
        }

        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }

    /** Adds part to the table of current product. */
    public void add_part(ActionEvent actionEvent) {
        selectedPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            //----------------No Selection Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        } else {
            selectedProduct.addAssociatedPart(selectedPart);
            partsAdded.add(selectedPart);
            thisPartTable.setItems(thisProductParts);
        }
    }

    /** Removes part from the current product parts table. Removes a part from the current parts, asks to confirm action, and reloads the current product parts table. */
    public void remove_part(ActionEvent actionEvent) {
        selectedPart = (Part) thisPartTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            //----------------No Selection Error--------------------//
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        } else {
            //--------------Remove Confirmation Box----------------------//
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you'd like to remove this part?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                selectedProduct.deleteAssociatedPart(selectedPart);
                partsRemoved.add(selectedPart);
            } else {
                //Do nothing
            }
            thisPartTable.setItems(thisProductParts);
        }
    }

    /** Searches allParts for matching parts. Searches allParts for parts matching either a substring or ID, stores the matches in a sublist, and sets the table items to this sublist. */
    public void searchParts(ActionEvent actionEvent) {
        String searchCriteria = partSearchBar.getText();
        ObservableList subList = FXCollections.observableArrayList();

        if (searchCriteria != "") {
            subList = Inventory.lookupPart(searchCriteria);

            if (subList.size() == 0) {
                try {
                    subList.add(Inventory.lookupPart(Integer.parseInt(searchCriteria)));
                } catch (Exception e) {
                    //----------------No Selection Error--------------------//
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Parts Found:");
                    alert.setHeaderText(null);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setContentText("Sorry no matching parts were found.");

                    alert.showAndWait();
                }
            }
        } else {
            subList = Inventory.getAllParts();
        }

        allPartsTable.setItems(subList);
        partSearchBar.setText("");
    }
}
