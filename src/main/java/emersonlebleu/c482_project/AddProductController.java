package emersonlebleu.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.ResourceBundle;

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
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void set_fields(Product product){
        product.setId(MainController.IdCreator.generate());
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

    private static Part selectedPart = null;
    public static Part getSelectedPart(){ return  selectedPart; }

    public void on_save(ActionEvent actionEvent) throws IOException {

        Product newProduct = new Product( 0, "none", 0.00, 0, 0, 0);
        set_fields(newProduct);
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
    public void on_cancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 895.0 , 395.0);
        stage.setTitle("IMS: Main");
        stage.setScene(scene);

        stage.show();
    }

    public void add_part(ActionEvent actionEvent) {
        selectedPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        thisProductParts.add(selectedPart);
        thisPartTable.setItems(thisProductParts);
    }

    public void remove_part(ActionEvent actionEvent) {
        selectedPart = (Part) thisPartTable.getSelectionModel().getSelectedItem();

        thisProductParts.remove(selectedPart);
        thisPartTable.setItems(thisProductParts);
    }

    public void searchParts(ActionEvent actionEvent) {
        String searchCriteria = partSearchBar.getText();
        ObservableList subList = FXCollections.observableArrayList();

        if (searchCriteria != "") {
            subList = MainController.searchPartsString(searchCriteria);

            if (subList.size() == 0) {
                try {
                    subList = MainController.searchPartsId(Integer.parseInt(searchCriteria));
                } catch (Exception e) { subList = FXCollections.observableArrayList(); }
            }
        } else {
            subList = Inventory.getAllParts();
        }

        allPartsTable.setItems(subList);
        partSearchBar.setText("");
    }
}
