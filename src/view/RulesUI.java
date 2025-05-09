package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RulesUI {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Switches from the Rules screen back to the main game menu.
     * 
     * @param event The event triggered by the user (e.g., clicking the "Back" button)
     * @throws IOException if the FXML file cannot be loaded
     */
    public void switchToScene(ActionEvent event) throws IOException {
        // Load the game menu FXML
        root = FXMLLoader.load(getClass().getResource("gameMenuUI.fxml"));

        // Get the current stage from the event source
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new scene using the loaded layout
        scene = new Scene(root);

        // Set the scene and apply fullscreen mode
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
