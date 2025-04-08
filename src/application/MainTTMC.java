package application;
	
import java.util.List;

import controller.GameController;
import controller.PrincipalController;
import exceptions.NoCardsLoadedException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import models.Card;
import utils.CardDataLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class MainTTMC extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception, NoCardsLoadedException {
		//cards loading
		List<Card> cards = CardDataLoader.loadCardsFromJson("../../resource/json/databaseQuestion.json");
		
		if (cards == null || cards.isEmpty()) {
			throw new NoCardsLoadedException();
		}
		
		GameController gameController = new GameController(cards);
		
		new PrincipalController(primaryStage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
