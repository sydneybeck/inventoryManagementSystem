package task.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * CLASS DESCRIPTION: This class controls the Main Form screen.
 *
 * FUTURE ENHANCEMENT: A future enhancement for the Main Form would be to include
 * an ordering feature for parts when inventory runs low.
 *
 * Another future enhancement would be an error dialog box if you click
 * the "Modify" buttons without selecting a part or a product.
 */
public class MainFormController implements Initializable {
    @FXML
    private TextField partSearchField;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partsInvCol;
    @FXML
    private TableColumn<Part, Double> partsPriceCol;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIDCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productsPriceCol;

    Stage stage;
    Parent scene;

    /**
     * METHOD DESCRIPTION: This method implements a search box that searches
     * the Parts Table and displays an error if no parts are found.
    */
    @FXML
    void partsSearchEnter(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> results = FXCollections.observableArrayList();
        String query = partSearchField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(query) ||
                    part.getName().contains(query)) {
                results.add(part);
            }
        }

        partsTable.setItems(results);

        if (results.size() == 0) {
            Alert noPart = new Alert(Alert.AlertType.ERROR);
            noPart.setTitle("ERROR");
            noPart.setContentText("No Parts Found");
            noPart.showAndWait();
        }
    }

    /**
     * METHOD DESCRIPTION: This method implements a search box that searches
     * the Products Table and displays an error if no products are found.
    */
    @FXML
    void productsSearchEnter(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> results = FXCollections.observableArrayList();
        String query = productSearchField.getText();

        for (Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(query) ||
                    product.getName().contains(query)) {
                results.add(product);
            }
        }

        productsTable.setItems(results);

        if (results.size() == 0) {
            Alert noPart = new Alert(Alert.AlertType.ERROR);
            noPart.setTitle("ERROR");
            noPart.setContentText("No Products Found");
            noPart.showAndWait();
        }
    }

    /**
     * METHOD DESCRIPTION: This method redirects the user to the Add Part screen.
    */
    @FXML
    void addPartsButtonClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * METHOD DESCRIPTION: This method redirects the user to the Modify Part screen.
     *
     *  RUNTIME ERROR: A runtime error occurs if you click the "Modify" button, but do not
     * have a part selected. This could be fixed by including a catch statement with an
     * error dialog box.
    */
    @FXML
    void modifyPartsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();

        ModifyPartController MPaController = loader.getController();
        MPaController.sendPart(partsTable.getSelectionModel().getSelectedIndex(), partsTable.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

        
    /**
     *  METHOD DESCRIPTION: This method redirects the user to the Add Product screen.
     */
    @FXML
    void addProductsButtonClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * METHOD DESCRIPTION: This method redirects the user to the Modify Product screen.
     *
     * RUNTIME ERROR: A runtime error occurs if you click the "Modify" button, but do not
     * have a product selected. This could be fixed by including a catch statement with an
     * error dialog box.
    */
    @FXML
    void modifyProductsButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();

        ModifyProductController MPrController = loader.getController();
        MPrController.sendProduct(productsTable.getSelectionModel().getSelectedItem(), productsTable.getSelectionModel().getSelectedIndex());

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * METHOD DESCRIPTION: This method deletes a part from the Parts Table, generates an error
     * dialog box if no part is selected, and generates a confirmation dialog box so the
     * user can confirm that they want to delete that part.
    */
    @FXML
    void deletePartsButtonClick(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partsTable.getItems().remove(selectedPart);
            }

        }
    }

    /**
     * METHOD DESCRIPTION: This method deletes a product from the Products Table, generates an error
     * dialog box if no product is selected, and generates a confirmation dialog box so the
     * user can confirm that they want to delete that product.
    */
    @FXML
    void deleteProductsButtonClick(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> asPartList = selectedProduct.getAssociatedParts();
                if (asPartList.size() >= 1) {
                    alert.setTitle("Error");
                    alert.setContentText("Please remove associated parts before deleting a product");
                    alert.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }

            }

        }
    }

    /**
     * METHOD DESCRIPTION: This method exits the entire application.
    */
    @FXML
    void exitButtonClick(ActionEvent event) {
        System.exit(0);
    }

    /**
     * METHOD DESCRIPTION: This method initilizes and populates the parts and products
     * tables with the sample data from the Inventory model.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

