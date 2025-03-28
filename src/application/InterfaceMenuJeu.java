package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InterfaceMenuJeu {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfaceChoixNombreJoueur.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToScene2(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfaceSettings.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToScene3(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("interfaceRules.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
