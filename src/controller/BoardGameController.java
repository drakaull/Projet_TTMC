package controller;

import java.io.IOException;
import java.net.URL;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.BoardGame;
import models.Card;
import models.Case;
import models.Pawn;
import models.Player;
import utils.CardDataLoader;

public class BoardGameController {

    @FXML
    private Circle pawn1; // Injection du pion depuis le FXML
    @FXML
    private Label currentPlayerLabel;
    private Pawn pawn;
    private BoardGame boardGame;
    private PawnController pawnController;
    private CardController cardController;
    private GameController gameController;
    private AudioClip backgroundMusic;

    public void initialize() {
    	startBackgroundMusic();
        Player player1 = new Player("Player1");
        boardGame = new BoardGame();
        pawn = new Pawn("Player1", 0, player1);
        pawnController = new PawnController(pawn, pawn1);
        cardController = new CardController(CardDataLoader.loadCardsFromJson());

        Case startCase = boardGame.getCase(pawn.getPosition());
        pawn1.setTranslateX(startCase.getX());
        pawn1.setTranslateY(startCase.getY());


        // 🎯 Launch the first question immediately
        Card randomCard = CardDataLoader.getRandomCard(); // or getCard()
        showDifficultyChoice(randomCard);
        

    }
    
    private void startBackgroundMusic() {
    	try {
    		URL musicPath = getClass().getResource("/resource/song/background.mp3");
    		backgroundMusic = new AudioClip(musicPath.toExternalForm());
    		backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
    		backgroundMusic.setVolume(0.7);
    		backgroundMusic.play();
    	}
    	catch(Exception e) {
    		System.out.println("music error : "+e.getMessage());
    	}
    }

    
    public void nextPlayer() {
        gameController.nextPlayer();
        updateCurrentPlayerLabel();
    }



    public void movePawn(int movement) {
        // Update pawn's logical position
        pawnController.moveForward(movement, boardGame);

        // Get new position
        Case newCase = boardGame.getCase(pawn.getPosition());
        double targetX = newCase.getX();
        double targetY = newCase.getY();

        // Animate pawn movement
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), pawn1);
        transition.setToX(targetX);
        transition.setToY(targetY);

        transition.setOnFinished(e -> {
            if (pawn.getPosition() == boardGame.getTotalCases() - 1) {
                Platform.runLater(this::showVictoryPopup);
            } else {
                // 🎯 Launch next question automatically!
            	String currentTheme = boardGame.getCase(pawn.getPosition()).getType();
            	
                Card nextCard = cardController.pickCardForTheme(currentTheme);
                Platform.runLater(() -> showDifficultyChoice(nextCard));
            }
        });

        transition.play();
    }


    private void showVictoryPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("XXX a gagné la partie !");
        alert.showAndWait();
    }
    @FXML
    private void showDifficultyChoice(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionDifficultyUI.fxml"));
            Parent root = loader.load();

            QuestionController controller = loader.getController();
            controller.setCard(card);
            controller.setBoardGameController(this);
            controller.setPlayerName(pawn.getOwner().getName()); // Assuming this returns the player's name

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void askNewCard() {
        Card nextCard = CardDataLoader.getRandomCard();
        Platform.runLater(() -> showDifficultyChoice(nextCard));
    }
    
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        this.boardGame = gameController.getBoardGame();

        Player current = gameController.getCurrentPlayer();
        this.pawn = current.getPawn();

        // Si tu as plusieurs cercles (pawn1, pawn2, etc.), tu peux les associer ici
        this.pawnController = new PawnController(pawn, pawn1);

        Case startCase = boardGame.getCase(pawn.getPosition());
        pawn1.setTranslateX(startCase.getX());
        pawn1.setTranslateY(startCase.getY());

        askNewCard();
    }



    public void updateCurrentPlayerLabel() {
        Player current = gameController.getCurrentPlayer();
        currentPlayerLabel.setText("It's your turn, " + current.getName() + "!");
    }


}
