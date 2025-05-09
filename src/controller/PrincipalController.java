package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;
import view.*;

public class PrincipalController {

    // Constructor that initializes the main game menu interface
    public PrincipalController(Stage primaryStage) {
        try {
            // Load the main game menu layout from FXML file
            Parent root = FXMLLoader.load(getClass().getResource("../view/gameMenuUI.fxml"));

            // Get screen dimensions (optional if using full screen anyway)
            double screenWidth = javafx.stage.Screen.getPrimary().getBounds().getWidth();
            double screenHeight = javafx.stage.Screen.getPrimary().getBounds().getHeight();

            // Create a new scene with default resolution
            Scene scene = new Scene(root, 1080, 720);

            // Apply the CSS stylesheet to the scene
            scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());

            // Set window properties
            primaryStage.setTitle("TTMC"); // Window title
            primaryStage.setScene(scene); // Attach scene to stage
            primaryStage.setResizable(true); // Allow window resizing
            primaryStage.setFullScreen(true); // Launch in full screen
            primaryStage.setFullScreenExitHint(""); // Remove fullscreen exit hint

            // Display the stage
            primaryStage.show();

        } catch(IOException e) {
            // Print error stack trace if FXML loading fails
            e.printStackTrace();
        }
    }
}
