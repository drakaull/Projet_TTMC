package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameMenuUI {

    // The current JavaFX window and scene
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Handles the "Play" button click.
     * Loads the scene where the user can choose the number of players.
     * 
     * @param event The click event triggering the scene change
     * @throws IOException If the FXML file cannot be loaded
     */
    public void switchToScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("nbPlayerChoiceUI.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     * Handles the "Rules" button click.
     * Loads the rules UI.
     * 
     * @param event The click event
     * @throws IOException If the FXML file cannot be loaded
     */
    public void switchToScene3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("rulesUI.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    /**
     * Handles the "Exit" button click.
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        javafx.application.Platform.exit(); // or System.exit(0);
    }
}
