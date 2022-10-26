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

import static task.c482.Inventory.updatePart;
import task.c482.Outsourced;

/**
 * CLASS DESCRIPTION: This class controls the Modify Part Screen.
*/
public class ModifyPartController implements Initializable {
    @FXML
    private RadioButton inhouse;
    @FXML
    private RadioButton outsourced;
    @FXML
    private TextField partName;
    @FXML
    private TextField partStock;
    @FXML
    private TextField partID;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMax;
    @FXML
    private TextField machineIDCompanyName;
    @FXML
    private TextField partMin;
    @FXML
    private Label machineIDLabel;

    Part selectedPart;
    int selectedIndex;

    Stage stage;
    Parent scene;

    /**
     * METHOD DESCRIPTION: This method toggles the Machine ID or Company Name label based
     * on if the "In-House" or "Outsourced" radio button is selected.
     */
    public void radioClick() {
        if (outsourced.isSelected())
            this.machineIDLabel.setText("Company Name");
        else
            this.machineIDLabel.setText("Machine ID");
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

    /**
     * METHOD DESCRIPTION: This method populates the part text boxes with the values from
     * the selected part.
    */
    public void sendPart(int index, Part part) {
        selectedPart = part;
        selectedIndex = index;

        if (part instanceof InHouse) {
            InHouse newPart = (InHouse) part;
            inhouse.setSelected(true);
            machineIDCompanyName.setText(String.valueOf(((InHouse) part).getMachineId()));

            this.partName.setText(String.valueOf(newPart.getName()));
            this.partStock.setText(String.valueOf(newPart.getStock()));
            this.partPrice.setText(String.valueOf(newPart.getPrice()));
            this.partMin.setText(String.valueOf(newPart.getMin()));
            this.partMax.setText(String.valueOf(newPart.getMax()));
            updatePart(selectedIndex, newPart);
        }
        if (part instanceof Outsourced) {
            Outsourced newPart = (Outsourced) part;
            outsourced.setSelected(true);
            machineIDCompanyName.setText(((Outsourced) part).getCompanyName());

            this.partName.setText(String.valueOf(newPart.getName()));
            this.partStock.setText(String.valueOf(newPart.getStock()));
            this.partPrice.setText(String.valueOf(newPart.getPrice()));
            this.partMin.setText(String.valueOf(newPart.getMin()));
            this.partMax.setText(String.valueOf(newPart.getMax()));
            updatePart(selectedIndex, newPart);
        }
    }

    /**
     * METHOD DESCRIPTION: This method saves the inputted values, returns the user to the Main Form screen
     * and has logical checks for number formatting, the min being greater than the max, and the inventory
     * level being between the min and the max.
     *
     * RUNTIME ERROR: A runtime error I encountered was that I tried to use the "min", "max", and "stock" variables
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
                int id = selectedPart.getId();
                String name = partName.getText();
                int stock = Integer.parseInt(partStock.getText());
                double price = Double.parseDouble(partPrice.getText());
                int max = Integer.parseInt(partMax.getText());
                int min = Integer.parseInt(partMin.getText());

                if (inhouse.isSelected()) {
                    int machineID = Integer.parseInt(machineIDCompanyName.getText());
                    InHouse addPart = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.getAllParts().set(selectedIndex, addPart);
                }

                if (outsourced.isSelected()) {
                    String companyName = machineIDCompanyName.getText();
                    Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.getAllParts().set(selectedIndex, addPart);
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

    /**
     * METHOD DESCRIPTION: This method initilizes and ensures that the radio button
     * associated with the part toggles whether the label says "Company Name" or
     * "Machine ID"
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (outsourced.isSelected())
            this.machineIDLabel.setText("Company Name");
        else
            this.machineIDLabel.setText("Machine ID");
    }
}
