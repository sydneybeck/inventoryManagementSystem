package task.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * CLASS DESCRIPTION: This class stores all the parts and products and describes inventory
 * methods.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * METHOD DESCRIPTION: Adds a part to the allParts Observable List
    */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * METHOD DESCRIPTION: Adds a product to the allProducts Observable List
    */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * METHOD DESCRIPTION: Gets all the parts.
    */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * METHOD DESCRIPTION: Gets all the products.
    */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * METHOD DESCRIPTION: Looks up part based on ID.
    */
    public static Part lookupPart(int partId) {
        Part partFound = null;
        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * METHOD DESCRIPTION: Looks up product based on ID.
    */
    public static Product lookupProduct(int productId) {
        Product productFound = null;
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * METHOD DESCRIPTION: Looks up part based on name.
    */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /**
     * METHOD DESCRIPTION: Looks up product base on name.
    */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * METHOD DESCRIPTION: Updates a part with new values in the Observable List
     * of parts.
    */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * METHOD DESCRIPTION: Updates a product with new values in the Observable List
     * of products.
    */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * METHOD DESCRIPTION: Removes a part from the Observable list of parts.
    */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * METHOD DESCRIPTION: Removes a product from the Observable list of products.
    */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
}
