// Nouveau BoardGameController avec gestion multi-joueurs

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
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
    private AnchorPane gamePane;

    @FXML
    private Label currentPlayerLabel;


    private GameController gameController;
    private BoardGame boardGame;
    private Map<Player, Circle> playerCircles = new HashMap<>();
    private AudioClip backgroundMusic;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        initializeGame();
    }

    private void initializeGame() {
        boardGame = new BoardGame();
        List<Player> players = gameController.getPlayers();

        for (Player player : players) {
            Pawn pawn = new Pawn(player.getName(), 0, player);
            player.setPawn(pawn);

            Circle circle = new Circle(10);
            circle.setId(player.getName());
            gamePane.getChildren().add(circle);
            playerCircles.put(player, circle);

            Case start = boardGame.getCase(0);
            circle.setTranslateX(start.getX());
            circle.setTranslateY(start.getY());
        }

        //startBackgroundMusic();
        updatePlayerLabel();
        askNewCard();
    }
    
    private void startBackgroundMusic() {
    	try {
    		URL musicPath = getClass().getResource("../resource/music/background.mp3");
    		if(musicPath == null)
    			System.out.println("Erreur : fichier mp3 non trouvé!");
    		backgroundMusic = new AudioClip(musicPath.toExternalForm());
    		backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
    		backgroundMusic.setVolume(1);
    		backgroundMusic.play();
    	}
    	catch(Exception e) {
    		System.out.println("music error : "+e.getMessage());
    	}
    }

    
    public void nextPlayer() {
        gameController.nextPlayer();
        updatePlayerLabel();
    }



    public void movePawn(int movement) {
        Player currentPlayer = gameController.getCurrentPlayer();
        PawnController pawnController = new PawnController(currentPlayer.getPawn());
        pawnController.moveForward(movement, boardGame);

        Case newCase = boardGame.getCase(currentPlayer.getPawn().getPosition());
        Circle circle = playerCircles.get(currentPlayer);

        TranslateTransition transition = new TranslateTransition(Duration.millis(100), circle);
        transition.setToX(newCase.getX());
        transition.setToY(newCase.getY());

        transition.setOnFinished(e -> {
            if (currentPlayer.getPawn().getPosition() == boardGame.getTotalCases() - 1) {
                showVictoryPopup(currentPlayer.getName());
            } else {
                gameController.nextPlayer();
                updatePlayerLabel();
                askNewCard();
            }
        });

        transition.play();
    }

    public void askNewCard() {
        Card card = CardDataLoader.getRandomCard();
        Platform.runLater(() -> showDifficultyChoice(card));
    }

    private void updatePlayerLabel() {
    	currentPlayerLabel.setText("Au tour de : " + gameController.getCurrentPlayer().getName());
    }

    private void showVictoryPopup(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText(winner + " a gagné la partie !");
        alert.showAndWait();
    }

    private void showDifficultyChoice(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionDifficultyUI.fxml"));
            Parent root = loader.load();

            QuestionController controller = loader.getController();
            controller.setCard(card);
            controller.setBoardGameController(this);
            controller.setPlayerName(gameController.getCurrentPlayer().getName());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


} 
