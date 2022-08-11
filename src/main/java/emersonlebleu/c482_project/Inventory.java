package emersonlebleu.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Inventory class. Holds a list of products and parts for the application. */
/** @author Emerson Lebleu */
public class Inventory {

    /** A static list of parts. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** A static list of products. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Add part to allParts.
     * @param newPart a new part to be added */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Add product to allProducts.
     * @param newProduct a new product to be added. */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Finds a part in allParts based on an id.
     * @param partId an id to search based on
     * @return a sublist of parts that match the id (would only be one part or none/null) */
    public static Part lookupPart(int partId){
        for (Part part: allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /** Finds a product in allProducts based on an id.
     * @param productId an id to search based on
     * @return a sublist of products that match the id (would only be one product or none/null) */
    public static Product lookupProduct(int productId){
        for (Product product: allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /** Finds a part in allParts based on a string.
     * @param partName a string to search based on
     * @return a sublist of parts that match the string */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> subListParts = FXCollections.observableArrayList();
        for (Part part: allParts){
            if (part.getName().contains(partName)){
                subListParts.add(part);
            }
        }
        return subListParts;
    }

    /** Finds a product in allProducts based on a string.
     * @param productName a string to search based on
     * @return a sublist of products that match the string */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> subListProduct = FXCollections.observableArrayList();
        for (Product product: allProducts){
            if (product.getName().contains(productName)){ //change
                subListProduct.add(product);
            }
        }
        return subListProduct;
    }

    /** Updates a part at a particular index.
     * @param index the index of the part to be updated
     * @param selectedPart the part that will be used to update the part at the index*/
    public static void  updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Updates a product at a particular index.
     * @param index the index of the product to be updated
     * @param selectedProduct the product that will be used to update the product at the index*/
    public static void  updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** Deletes a part from the allParts list.
     * @param selectedPart the part to be deleted
     * @return whether or not removal was successful */
    public static boolean  deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /** Deletes a product from the allProducts list
     * @param selectedProduct the product to be deleted
     * @return whether or not removal was successful*/
    public static boolean  deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /** Gets and returns a list of all parts.
     @return a list of parts */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Gets and returns a list of all products.
     @return a list of products */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
