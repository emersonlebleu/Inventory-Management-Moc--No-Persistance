package emersonlebleu.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/** This class creates a product instance. */

/** @author Emerson Lebleu */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    /** Gets and returns the int id.
     @return the id */
    public int getId() { return id; }

    /** Takes an int and sets the id.
      @param id the id to set */
    public void setId(int id) { this.id = id; }

    /** Returns a string name.
      @return the name */
    public String getName() { return name; }

    /** Takes a string and sets name.
      @param name the name to set */
    public void setName(String name) { this.name = name; }

    /** Returns a double representing price.
      @return the price */
    public double getPrice() { return price; }

    /** Takes a double and sets the price property.
      @param price the price to set */
    public void setPrice(double price) { this.price = price; }

    /** Returns an int representing number of particular item said to be "in stock".
      @return the stock */
    public int getStock() { return stock; }

    /** Takes an int and sets the stock property.
      @param stock the stock to set */
    public void setStock(int stock) { this.stock = stock; }

    /** Returns an int the minimum.
      @return the min */
    public int getMin() { return min; }

    /** Takes an int sets minimum to this.
      @param min the min to set */
    public void setMin(int min) { this.min = min; }

    /** Returns an int the maximum.
      @return the max */
    public int getMax() { return max; }

    /** Takes an int sets maximum to this.
      @param max the max to set */
    public void setMax(int max) { this.max = max; }

    /** Takes a part and adds it to a list of associated parts.
      @param part the part to add */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Takes a part and removes that part from the list of associated parts.
      @param selectedPart the part to delete */
    public boolean deleteAssociatedPart(Part selectedPart) {
        return associatedParts.remove(selectedPart);
    }

    /** Returns the list of associated parts.
      @return an ObservableList of parts */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
