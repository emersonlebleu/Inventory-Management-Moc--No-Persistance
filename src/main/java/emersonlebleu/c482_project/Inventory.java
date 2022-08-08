package emersonlebleu.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId){
        for (Part part: allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId){
        for (Product product: allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> subListParts = FXCollections.observableArrayList();
        for (Part part: allParts){
            if (part.getName().contains(partName)){
                subListParts.add(part);
            }
        }
        return subListParts;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> subListProduct = FXCollections.observableArrayList();
        for (Product product: allProducts){
            if (product.getName().contains(productName)){ //change
                subListProduct.add(product);
            }
        }
        return subListProduct;
    }

    public static void  updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void  updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean  deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean  deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
