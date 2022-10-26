package task.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * CLASS DESCRIPTION: This class creates a product class for the product elements and describes
 * the Product methods.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * METHOD DESCRIPTION: Describes the Product arguments.
    */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product ID.
    */
    public int getId() {
        return id;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product ID.
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product name.
    */
    public String getName() {
        return name;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product name.
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product price.
    */
    public double getPrice() {
        return price;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product name.
    */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product stock.
    */
    public int getStock() {
        return stock;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product stock.
    */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product min.
    */
    public int getMin() {
        return min;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product name.
    */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product max.
    */
    public int getMax() {
        return max;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product max.
    */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * METHOD DESCRIPTION: Getter for the product's associated parts.
    */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * METHOD DESCRIPTION: Setter for the product's associated parts.
    */
    public void setAssociatedParts (Part part) {
        associatedParts.add(part);
    }

    /**
     * METHOD DESCRIPTION: Adds the associated part to the associated parts list.
    */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * METHOD DESCRIPTION: Deletes an associated part.
    */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

}