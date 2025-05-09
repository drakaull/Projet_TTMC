package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameUI {

    // The JavaFX stage and scene objects used to manage the window
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Handles switching back to the game menu screen.
     * This is typically called when the user presses a "Back to Menu" button.
     *
     * @param event The ActionEvent triggered by the user's click
     * @throws IOException If the FXML file cannot be found or loaded
     */
    public void switchToScene(ActionEvent event) throws IOException {
        // Load the Game Menu UI
        root = FXMLLoader.load(getClass().getResource("gameMenuUI.fxml"));

        // Get the current stage (window) from the event
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene and make it fullscreen
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
