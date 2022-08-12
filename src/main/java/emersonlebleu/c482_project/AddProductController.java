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
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Optional;
import java.util.ResourceBundle;
 /** Controller class for the add_product_view. */
public class AddProductController implements Initializable {
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

    /** Variable that holds list of parts for current product. */
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();

    /** Sets up tables with appropriate information. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainController.generate();
        idField.setText(String.valueOf(MainController.currId));

        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        thisPartTable.setItems(thisProductParts);

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
        product.setId(MainController.currId);

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

     /** Selected part variable. Used to store the selected part from the allParts and thisPart tables. */
    private static Part selectedPart = null;

    /** Saves a new product. Saves the information into a new product, adds product to the inventory, and loads main. */
    public void on_save(ActionEvent actionEvent) throws IOException {

        Product newProduct = new Product( 0, "none", 0.00, 0, 0, 0);
        set_fields(newProduct);

        if (set_fields(newProduct)) {
            for (Part part: thisProductParts) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(newProduct);

            Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 895.0 , 395.0);
            stage.setTitle("IMS: Main");
            stage.setScene(scene);

            stage.show();
        }
    }

    /** Returns to the main screen. */
    public void on_cancel(ActionEvent actionEvent) throws IOException {
        MainController.currId -= 1;

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
            thisProductParts.add(selectedPart);
            thisPartTable.setItems(thisProductParts);
        }
    }

    /** Removes selected part from product after confirmation. */
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
                thisProductParts.remove(selectedPart);
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
                } catch (Exception e) { subList = FXCollections.observableArrayList(); }
            }
        } else {
            subList = Inventory.getAllParts();
        }

        allPartsTable.setItems(subList);
        partSearchBar.setText("");
    }
}
