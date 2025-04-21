package controller;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    private Pawn pawn;
    private BoardGame boardGame;
    private PawnController pawnController;
    private CardController cardController;

    public void initialize() {
        Player player1 = new Player("Player1");
        boardGame = new BoardGame();
        pawn = new Pawn("Player1", 0, player1);
        pawnController = new PawnController(pawn);
        cardController = new CardController(CardDataLoader.loadCardsFromJson());

        Case startCase = boardGame.getCase(pawn.getPosition());
        pawn1.setTranslateX(startCase.getX());
        pawn1.setTranslateY(startCase.getY());


        // 🎯 Launch the first question immediately
        Card randomCard = CardDataLoader.getRandomCard(); // or getCard()
        showDifficultyChoice(randomCard);

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


}
