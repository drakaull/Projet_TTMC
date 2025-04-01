package application;
	
import controller.PrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class MainTTMC extends Application {
	@Override
	public void start(Stage primaryStage) {
		new PrincipalController(primaryStage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
