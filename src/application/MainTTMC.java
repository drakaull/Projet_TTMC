package application;
	
import java.util.Arrays;
import java.util.List;

import controller.GameController;
import controller.PrincipalController;
import exceptions.NoCardsLoadedException;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Card;
import models.Player;
import utils.CardDataLoader;

public class MainTTMC extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception, NoCardsLoadedException {
		//cards loading
		List<Card> cards = CardDataLoader.loadCardsFromJson();
		
		if (cards == null || cards.isEmpty()) {
			throw new NoCardsLoadedException();
		}
		
		List<Player> players = Arrays.asList(new Player("Player 1"), new Player("Player 2"));

		GameController gameController = new GameController(cards, players);

		
		new PrincipalController(primaryStage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
