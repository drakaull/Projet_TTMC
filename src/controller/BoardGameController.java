package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import models.BoardGame;
import models.Case;
import models.Pawn;
import models.Player;

public class BoardGameController {

    @FXML
    private Circle pawn1; // Injection du pion depuis le FXML

    @FXML
    private Button buttonAdvance; // Injection du bouton depuis le FXML

    private Pawn pawn;
    private BoardGame boardGame;
    private PawnController pawnController;

    public void initialize() {
        // Initialisation du plateau et du pion
        Player player1 = new Player("Player1");
        boardGame = new BoardGame();
        pawn = new Pawn("Player1", 0, player1); // logique du jeu
        pawnController = new PawnController(pawn); // pas besoin de Node ici !

        // Position initiale graphique du pion (translate)
        pawn1.setLayoutX(0); // base du pion
        pawn1.setLayoutY(0);
        Case startCase = boardGame.getCase(pawn.getPosition());
        pawn1.setTranslateX(startCase.getX());
        pawn1.setTranslateY(startCase.getY());

        // Action du bouton
        buttonAdvance.setOnAction(event -> movePawn(1));
    }

    public void movePawn(int movement) {
        // Mise à jour logique du pion
        pawnController.moveForward(movement, boardGame);

        // Récupération de la nouvelle position cible
        Case newCase = boardGame.getCase(pawn.getPosition());
        double targetX = newCase.getX();
        double targetY = newCase.getY();

        // Animation avec position absolue
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), pawn1);
        transition.setToX(targetX);
        transition.setToY(targetY);

        transition.setOnFinished(e -> {
            if (pawn.getPosition() == boardGame.getTotalCases() - 1) {
                Platform.runLater(this::showVictoryPopup);
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
}
