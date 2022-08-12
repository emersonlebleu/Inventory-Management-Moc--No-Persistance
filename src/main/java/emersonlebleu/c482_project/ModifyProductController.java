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
     * @param product a product to be set */
    public void set_fields(Product product){
        int idNum = Integer.parseInt(idField.getText());
        product.setId(idNum);
        String newName = nameField.getText();
        product.setName(newName);
        int newInv = Integer.parseInt(invField.getText());
        product.setStock(newInv);
        Double newPrice = Double.parseDouble(priceField.getText());
        product.setPrice(newPrice);
        int newMax = Integer.parseInt(maxField.getText());
        product.setMax(newMax);
        int newMin = Integer.parseInt(minField.getText());
        product.setMin(newMin);
    }

    /** Saves the information into selectedProduct and loads main. */
    public void on_save(ActionEvent actionEvent) throws IOException {
        set_fields(selectedProduct);

        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }

    /** Returns to the main screen. */
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

        selectedProduct.addAssociatedPart(selectedPart);
        partsAdded.add(selectedPart);
        thisPartTable.setItems(thisProductParts);
    }

    /** Removes part from the current product parts table. Removes a part from the current parts, asks to confirm action, and reloads the current product parts table. */
    public void remove_part(ActionEvent actionEvent) {
        selectedPart = (Part) thisPartTable.getSelectionModel().getSelectedItem();
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

    /** Searches allParts for matching parts. Searches allParts for parts matching either a substring or ID, stores the matches in a sublist, and sets the table items to this sublist. */
    public void searchParts(ActionEvent actionEvent) {
        String searchCriteria = partSearchBar.getText();
        ObservableList subList = FXCollections.observableArrayList();

        if (searchCriteria != "") {
            subList = Inventory.lookupPart(searchCriteria);

            if (subList.size() == 0) {
                try {
                    subList.add(Inventory.lookupPart(Integer.parseInt(searchCriteria)));
                } catch (Exception e) { subList = FXCollections.observableArrayList(); }
            }
        } else {
            subList = Inventory.getAllParts();
        }

        allPartsTable.setItems(subList);
        partSearchBar.setText("");
    }
}
