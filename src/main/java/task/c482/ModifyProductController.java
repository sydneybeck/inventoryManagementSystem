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

import static task.c482.Inventory.*;

/**
 * CLASS DESCRIPTION: This class controls the Modify Products Screen.
 */
public class ModifyProductController implements Initializable {
    @FXML
    private TextField productName;
    @FXML
    private TextField productID;
    @FXML
    private TextField productStock;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMax;
    @FXML
    private TextField productMin;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> asPartIDCol;
    @FXML
    private TableColumn<Part, String> asPartNameCol;
    @FXML
    private TableColumn<Part, Integer> asPartInvCol;
    @FXML
    private TableColumn<Part, Double> asPartPriceCol;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    Stage stage;
    Parent scene;

    Product newProduct;
    Product selectedProduct;
    int selectedIndex;

    /**
     * METHOD DESCRIPTION: This method adds a product from the top Parts Table to the
     * bottom Associated Parts Table.
    */
    public void addProductsButtonClick(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        selectedProduct.setAssociatedParts(selectedPart);
    }

    /**
     * METHOD DESCRIPTION: This method saves the new product, creates a random Product ID, adds it to the Main Form product table,
     * has logical checks for the min being less than the max, the inventory being between the min and max
     * values, and number format checks.
     */
    @FXML
    public void saveButtonClick(ActionEvent event) throws IOException {
        try {
            if (Integer.parseInt(productMin.getText()) >= Integer.parseInt(productMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be less than max");
                alert.showAndWait();
            } else if (Integer.parseInt(productMin.getText()) > Integer.parseInt(productStock.getText()) || Integer.parseInt(productStock.getText()) > Integer.parseInt(productMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be between the min and max values");
                alert.showAndWait();
            } else {
                int id = selectedProduct.getId();
                String name = productName.getText();
                int stock = Integer.parseInt(productStock.getText());
                double price = Double.parseDouble(productPrice.getText());
                int max = Integer.parseInt(productMax.getText());
                int min = Integer.parseInt(productMin.getText());

                selectedProduct.setId(id);
                selectedProduct.setName(name);
                selectedProduct.setPrice(price);
                selectedProduct.setMax(max);
                selectedProduct.setMin(min);
                selectedProduct.setStock(stock);

                updateProduct(selectedIndex, selectedProduct);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid value for each field");
            alert.showAndWait();
        }
}

    /**
     * METHOD DESCRIPTION: This method brings the user back to the main form when
     * they press the "Cancel" Button.
     */
    @FXML
    void cancelButtonClick (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * METHOD DESCRIPTION: This method removes an associated product from the bottom
     * table, has a logical check for if they have selected a part, and confirmation
     * dialog to ensure the user wants to delete the part.
    */
    @FXML
    void removeAssociatedButtonClick(ActionEvent event) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedPartsTable.getItems().remove(selectedPart);
            }

        }
    }

    /**
     * METHOD DESCRIPTION: This method updates the Parts table so that it is populated
     * with all the parts that are on the main screen as well.
    */
    public void updatePartTable() {
        partsTable.setItems(getAllParts());
    }

    /**
     * METHOD DESCRIPTION: This method populates the product text boxes with the values from
     * the selected product and the associated parts table with its associated parts.
    */
    public void sendProduct(Product product, int index) {
        selectedProduct = product;
        selectedIndex = index;

        if (product instanceof Product) {
            Product newProduct = selectedProduct;

            this.productID.setText((Integer.toString(newProduct.getId())));
            this.productName.setText(newProduct.getName());
            this.productStock.setText((Integer.toString(newProduct.getStock())));
            this.productPrice.setText((Double.toString(newProduct.getPrice())));
            this.productMax.setText((Integer.toString(newProduct.getMax())));
            this.productMin.setText((Integer.toString(newProduct.getMin())));
            associatedPartsTable.setItems(newProduct.getAssociatedParts());
            updateProduct(selectedIndex, newProduct);
        }
    }

    /**
     * METHOD DESCRIPTION: This method implements a search box that searches
     * the top Parts Table and displays an error if no parts are found.
    */
    @FXML
    void partsSearchEnter (ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> results = FXCollections.observableArrayList();
        String query = searchField.getText();

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
     * METHOD DESCRIPTION: This method initilizes and populates the Parts Table and
     * the Associated Parts Table for the selected product.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatePartTable();

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        newProduct = new Product(0, null, 0.0, 0,0,0);
        associatedPartsTable.setItems(newProduct.getAssociatedParts());

        asPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
