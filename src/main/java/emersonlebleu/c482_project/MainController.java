package emersonlebleu.c482_project;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/** This is the class for the main controller. This is the starting point view for the application. */

public class MainController implements Initializable {
    public TableView partsTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryColumn;
    public TableColumn partPriceColumn;
    public TableView productsTable;
    public TableColumn productIdColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryColumn;
    public TableColumn productPriceColumn;
    public TextField partSearchBar;
    public TextField productSearchBar;
    private static boolean fisrtLoad = true;


//    public class IdCreator {
//
//    }

    static int currId = 0;
    /** This method is used to generate unique ids.
     It uses the static variable currId and increments for the life of the program.
     @return returns int currId */
    public static int generate() {
        currId++;
        return currId;
    }

    /** This method contains default data. While not required it is not prohibited to input data, data is fresh
     each new startup of the program but only runs on first initialization of main page. */
    private void initalData(){
        if (!fisrtLoad){
            return;
        }
        Outsourced one = new Outsourced(generate(), "Bolt-Small", .16, 500, 1, 100000, "InterCom Inc.");
        Inventory.addPart(one);
        InHouse two = new InHouse(generate(), "Washer-Medium", .16, 1000, 1, 100000, 2043);
        Inventory.addPart(two);
        Outsourced three = new Outsourced(generate(), "Nut", .16, 600, 1, 100000, "InterCom Inc.");
        Inventory.addPart(three);
        Outsourced four = new Outsourced(generate(), "Screw-Metal", .10, 1000, 1, 100000, "Screws & More");
        Inventory.addPart(four);
        Outsourced five = new Outsourced(generate(), "Screw-Plastic", .10, 1000, 1, 100000, "Screws & More");
        Inventory.addPart(five);
        InHouse six = new InHouse(generate(), "Bolt-Medium", .30, 400, 1, 100000, 2043);
        Inventory.addPart(six);

        Product productOne = new Product(generate(), "BWN Combo Small", 1.50, 300, 1, 100000);
        Inventory.addProduct(productOne);
        productOne.addAssociatedPart(one);
        productOne.addAssociatedPart(four);
        Product productTwo = new Product(generate(), "BWN Combo Medium", 3.15, 200, 1, 100000);
        Inventory.addProduct(productTwo);
        fisrtLoad = false;
    }
    /** This is the initialize method of the main controller page. This calls the initial data and fills and formats the table views. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initalData();

        partsTable.setItems(Inventory.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** When button clicked the program closes. */
    public void exitbuttonclick(ActionEvent actionEvent) {
        Platform.exit();
    }

    private static Part selectedPart = null;
    public static Part getSelectedPart(){ return  selectedPart; }
    private static Product selectedProduct = null;
    public static Product getSelectedProduct(){
        return  selectedProduct;
    }

    public static ObservableList<Part> searchPartsString(String sCriteria){
        ObservableList<Part> subList = FXCollections.observableArrayList();
        ObservableList<Part> mainList = Inventory.getAllParts();

        for (Part part: mainList) {
            if (part.getName().contains(sCriteria)){
                subList.add(part);
            }
        }
        return subList;
    }
    public static ObservableList<Part> searchPartsId(int sCriteria){
        ObservableList<Part> subList = FXCollections.observableArrayList();
        ObservableList<Part> mainList = Inventory.getAllParts();

        for (Part part: mainList) {
            if (part.getId() == sCriteria){
                subList.add(part);
            }
        }
        return subList;
    }

    public static ObservableList<Product> searchProductsString(String sCriteria){
        ObservableList<Product> subList = FXCollections.observableArrayList();
        ObservableList<Product> mainList = Inventory.getAllProducts();

        for (Product product: mainList) {
            if (product.getName().contains(sCriteria)){
                subList.add(product);
            }
        }
        return subList;
    }
    public static ObservableList<Product> searchProductsId(int sCriteria){
        ObservableList<Product> subList = FXCollections.observableArrayList();
        ObservableList<Product> mainList = Inventory.getAllProducts();

        for (Product product: mainList) {
            if (product.getId() == sCriteria){
                subList.add(product);
            }
        }
        return subList;
    }

    public void to_add_part(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("add_part_view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 488.0 , 463.0);
            stage.setTitle("IMS: Add Part");
            stage.setScene(scene);

            stage.show();
    }

    public void to_modify_part(ActionEvent actionEvent) throws IOException{
        selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("modify_part_view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 488.0 , 463.0);
            stage.setTitle("IMS: Modify Part");
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println("Error: Sorry, No Part Selected. Please select a part and try again.");
        }
    }

    public void to_add_product(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_product_view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 949.0 , 603.0);
        stage.setTitle("IMS: Add Product");
        stage.setScene(scene);

        stage.show();
    }

    public void to_modify_product(ActionEvent actionEvent) throws IOException{
        selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("modify_product_view.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 949.0 , 603.0);
            stage.setTitle("IMS: Modify Product");
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println("Error: Sorry, No Product Selected. Please select a product and try again.");
        }

    }
    /** Deletes a product from the inventory if conditions are satisfied. If product has an associated part an error dialog will populate.
      Has confirmation box, if confirmed product will be deleted. */
    public void delete_product(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        } else if (selectedProduct.getAllAssociatedParts().size() != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Associated Part Delete Error");
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setContentText("Sorry, this product has an associated part. You cannot delete it.");

            alert.showAndWait();
        } else {
            //--------------Delete Confirmation Box----------------------//
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you'd like to delete this product?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes) {
                Inventory.deleteProduct(selectedProduct);
            } else {
                //Do nothing
            }
        }
    }
    /** Deletes a part from the inventory. Has confirmation box, if confirmed part will be deleted. */
    public void delete_part(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        //--------------Delete Confirmation Box----------------------//
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you'd like to delete this part?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            Inventory.deletePart(selectedPart);
        } else {
            //Do nothing
        }
    }

    public void look_up_part(ActionEvent actionEvent) {
        String searchCriteria = partSearchBar.getText();
        ObservableList subList = FXCollections.observableArrayList();

        if (searchCriteria != "") {
            subList = searchPartsString(searchCriteria);

            if (subList.size() == 0) {
                try {
                    subList = searchPartsId(Integer.parseInt(searchCriteria));
                } catch (Exception e) { subList = FXCollections.observableArrayList(); }
            }
        } else {
            subList = Inventory.getAllParts();
        }

        partsTable.setItems(subList);
        partSearchBar.setText("");
    }

    public void look_up_product(ActionEvent actionEvent) {
        String searchCriteria = productSearchBar.getText();
        ObservableList subList = FXCollections.observableArrayList();

        if (searchCriteria != "") {
            subList = searchProductsString(searchCriteria);

            if (subList.size() == 0) {
                try {
                    subList = searchProductsId(Integer.parseInt(searchCriteria));
                } catch (Exception e) { subList = FXCollections.observableArrayList(); }
            }
        } else {
            subList = Inventory.getAllProducts();
        }

        productsTable.setItems(subList);
        productSearchBar.setText("");
    }
}