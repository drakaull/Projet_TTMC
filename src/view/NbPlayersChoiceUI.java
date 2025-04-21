package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.BoardGameController;
import controller.GameController;
import controller.PlayerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import models.Card;
import models.Player;
import utils.CardDataLoader;

public class NbPlayersChoiceUI {
	
	private Slider playerSlider;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToScene(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("gameMenuUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void switchToScene2(ActionEvent event) {
	    int nbPlayers = (int) playerSlider.getValue();

	    // Génération des noms : tu peux aussi les saisir si tu veux
	    List<String> names = new ArrayList<>();
	    for (int i = 1; i <= nbPlayers; i++) {
	        names.add("Player " + i);
	    }

	    PlayerController playerController = new PlayerController();
	    List<Player> players = playerController.createPlayersWithPawns(names);

	    List<Card> allCards = CardDataLoader.loadCardsFromJson();

	    GameController game = new GameController(allCards, players);

	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BoardGame.fxml"));
	        Parent root = loader.load();

	        BoardGameController controller = loader.getController();
	        controller.setGameController(game); // Passe les joueurs ici

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
