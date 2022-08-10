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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Part> thisProductParts = FXCollections.observableArrayList();

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
    private ObservableList<Part> thisProductParts = FXCollections.observableArrayList();
    private static Part selectedPart = null;
    public static Part getSelectedPart(){ return  selectedPart; }

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

    public void add_part(ActionEvent actionEvent) {
        selectedPart = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        thisProductParts.add(selectedPart);
        thisPartTable.setItems(thisProductParts);
    }

    public void remove_part(ActionEvent actionEvent) {
        System.out.println("Remove part from product");
    }

    public void searchParts(ActionEvent actionEvent) {
    }
}
