package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;
import view.*;

public class PrincipalController {
	private Card cardModel;
	private CardController cardController;
	private Case caseModel;
	private CaseController caseController;
	private Chronometer chronoModel;
	private ChronoController chronoController;
	private Game gameModel;
	private GameController gameController;
	private Pawn pawnModel;
	private PawnController pawnController;
	private Player playerModel;
	private PlayerController playerController;
	private Question QuestionModel;
	private QuestionController QuestionController;
	private GameMenuUI gameMenuView;
	private GameUI gameView;
	private PlayerChoiceUI playerChoiceView;
	private NbPlayersChoiceUI nbPlayerChoiceView;
	private RulesUI rulesView;
	private SettingsUI settingsView;

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

	public void modelsInitialization() {
		
	}
}
