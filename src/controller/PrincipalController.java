package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;
import view.*;

public class PrincipalController {

	public PrincipalController(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/gameMenuUI.fxml"));
			double screenWidth = javafx.stage.Screen.getPrimary().getBounds().getWidth();
			double screenHeight = javafx.stage.Screen.getPrimary().getBounds().getHeight();
			Scene scene = new Scene(root,1080,720);
			scene.getStylesheets().add(getClass().getResource("../application/application.css").toExternalForm());
			primaryStage.setTitle("TTMC");
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitHint("");
			primaryStage.show();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
