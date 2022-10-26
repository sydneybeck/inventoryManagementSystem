package task.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * CLASS DESCRIPTION: This is the class that controls the Add Part screen.
 *
 *  FUTURE ENHANCEMENT: A future enhancement for the Add Parts screen is to
 * show a confirmation dialog for the cancel button to ensure that users want
 * to cancel out of the screen.
 */

public class AddPartController implements Initializable {
    @FXML
    private RadioButton inhouse;
    @FXML
    private RadioButton outsourced;
    @FXML
    private Label machineIDCompanyName;
    @FXML
    private TextField partName;
    @FXML
    private TextField partStock;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partMachineID;
    @FXML
    private TextField partMin;

    Stage stage;
    Parent scene;

    /**
     * METHOD DESCRIPTION: This method toggles the Machine ID or Company Name label based
     * on if the "In-House" or "Outsourced" radio button is selected.
     */
    public void radioClick(ActionEvent actionEvent) {
        if (outsourced.isSelected())
            this.machineIDCompanyName.setText("Company Name");
        else
            this.machineIDCompanyName.setText("Machine ID");
    }

    /**
     * METHOD DESCRIPTION: This method brings the user back to the main form screen
     * when they click the "Cancel" button.
     */
    @FXML
    void cancelButtonClick(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * METHOD DESCRIPTION: This method saves the inputted values, returns the user to the Main Form screen
     * and has logical checks for number formatting, the min being greater than the max, and the inventory
     * level being between the min and the max.
     *
     *  RUNTIME ERROR: A runtime error I encountered was that I tried to use the "min", "max", and "stock" variables
     * before they were declared in my else block. To fix it, I used parse and get text methods.
     */
    @FXML
    void saveButtonClick(ActionEvent event) throws IOException {
        try {
            if (Integer.parseInt(partMin.getText()) >= Integer.parseInt(partMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be less than max");
                alert.showAndWait();
            } else if (Integer.parseInt(partMin.getText()) > Integer.parseInt(partStock.getText()) || Integer.parseInt(partStock.getText()) > Integer.parseInt(partMax.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be between the min and max values");
                alert.showAndWait();
            } else {
                String name = partName.getText();
                int stock = Integer.parseInt(partStock.getText());
                double price = Double.parseDouble(partPrice.getText());
                int max = Integer.parseInt(partMax.getText());
                int min = Integer.parseInt(partMin.getText());
                int machineID = 0;
                String companyName;
                int randID = (int) (Math.random() * 100);

                if (inhouse.isSelected()) {
                    machineID = Integer.parseInt(partMachineID.getText());
                    InHouse addPart = new InHouse(randID, name, price, stock, min, max, machineID);
                    Inventory.addPart(addPart);
                }

                if (outsourced.isSelected()) {
                    companyName = partMachineID.getText();
                    Outsourced addPart = new Outsourced(randID, name, price, stock, min, max, companyName);
                    Inventory.addPart(addPart);
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }

        catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a valid value for each field");
                alert.showAndWait();
            }

        }
    }
