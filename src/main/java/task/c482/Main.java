package task.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JAVADOC LOCATION: JavaDoc index is located in the src folder
 *
 * CLASS DESCRIPTION: This is an Inventory Management System where parts and products can
 * be created and modified, and parts can be associated with corresponding products.
 *
 * FUTURE ENHANCEMENT: A future enhancement for this software would be an ordering system that
 * communicates with the parts, products, and inventory features.
*/

public class Main extends Application {
    /**
     * METHOD DESCRIPTION: This method creates the "Main Form" FXML stage and loads the start-up scene.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * METHOD DESCRIPTION: This method adds sample part and product data to the application.
     *
     * RUNTIME ERROR: A runtime error I encountered was when I forgot to add the company names to the sample parts. This
     * created a mismatch between the expected number of arguments and the number of arguments I inputted.
     */
    public static void main(String[] args) {
        Part motherboard = new InHouse(1, "Motherboard", 500.0, 100, 50, 500, 1);
        Part ram = new InHouse(2, "RAM", 300.0, 90, 30, 300, 2);
        Part storage = new InHouse(3, "Storage", 200.0, 120, 60, 600, 3);
        Part processor = new Outsourced(4, "Processor", 800.0, 50, 40, 400, "IBM");
        Part monitor = new Outsourced(5, "Monitor", 400.0, 20, 10, 100, "Dell");


        Product desktop = new Product(1, "Desktop Computer", 3000.0, 6, 2, 15);
        Product workstation = new Product(2, "Workstation Computer", 8000.0, 3, 1, 10);

        Inventory.addPart(motherboard);
        Inventory.addPart(ram);
        Inventory.addPart(storage);
        Inventory.addPart(processor);
        Inventory.addPart(monitor);

        Inventory.addProduct(desktop);
        Inventory.addProduct(workstation);

        launch(args);
    }
}
