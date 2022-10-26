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
 * CLASS DESCRIPTION: This is the class that controls the Add Product screen.
 *
 *  FUTURE ENHANCEMENT: A future enhancement for the Add Products screen is to
 * show a confirmation dialog for the cancel button to ensure that users want
 * to cancel out of the screen.
 */
public class AddProductController implements Initializable {
    Product newProduct;
    ObservableList<Part> asPartItems = FXCollections.observableArrayList();
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
     * METHOD DESCRIPTION: This method saves the new product, creates a random Product ID, adds it to the Main Form product table,
     * has logical checks for the min being less than the max, the inventory being between the min and max
     * values, and number format checks.
     */
    @FXML
    void saveButtonClick(ActionEvent event) throws IOException {
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
                String name = productName.getText();
                int stock = Integer.parseInt(productStock.getText());
                double price = Double.parseDouble(productPrice.getText());
                int max = Integer.parseInt(productMax.getText());
                int min = Integer.parseInt(productMin.getText());
                int randID = (int) (Math.random() * 100);

                Product product = new Product(randID, name, price, stock, min, max);

                for (Part part : asPartItems) {
                    if (part != asPartItems)
                        product.addAssociatedPart(part);
                }

                Inventory.getAllProducts().add(product);

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
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**
     * METHOD DESCRIPTION: This method adds a product from the top Parts Table to the
     * bottom Associated Parts Table.
    */
    @FXML
    public void addProductsButtonClick(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(selectedPart);
        asPartItems = newProduct.getAssociatedParts();
    }

    /**
     * METHOD DESCRIPTION: This method removes an associated product from the bottom
     * table, has a logical check for if they have selected a part, and confirmation
     * dialog to ensure the user wants to delete the part.
    */
    @FXML
    void removeAssociatedButtonClick(ActionEvent actionEvent) {
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
        partsTable.setItems(Inventory.getAllParts());
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

        newProduct = new Product(0, null, 0.0, 0,0,0);
        asPartItems = newProduct.getAssociatedParts();
        associatedPartsTable.setItems(newProduct.getAssociatedParts());

        asPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
