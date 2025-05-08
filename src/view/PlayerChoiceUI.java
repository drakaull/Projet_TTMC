package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class PlayerChoiceUI {

	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private Slider difficultySlider;

	
	public void switchToScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("nbPlayerChoiceUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}
	public void switchToScene2(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("gameUI.fxml"));
		root = loader.load();

		controller.BoardGameController boardGameController = loader.getController();
		int difficulty = (int) difficultySlider.getValue();
		boardGameController.setDifficultyLevel(difficulty);  // Transfer 

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	
}
