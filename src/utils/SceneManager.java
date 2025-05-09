package utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    // The main application stage (window), stored statically so it can be accessed globally
    private static Stage primaryStage;

    /**
     * Sets the primary stage for the application.
     * This method should be called once (e.g., in the start method of your main Application class).
     * 
     * @param stage The primary stage to store
     */
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Loads and displays a new scene from the given FXML path.
     * It replaces the current scene in the primary stage.
     * 
     * @param fxmlPath Path to the FXML file relative to the classpath (e.g., "/view/MainMenu.fxml")
     * @throws IOException If the FXML file cannot be loaded
     */
    public static void switchScene(String fxmlPath) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
        Parent root = loader.load();

        // Set the scene on the primary stage and show it
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
