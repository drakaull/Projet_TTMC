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
        boardGame = new BoardGame();
        pawn = new Pawn("Player1");		// boucle à faire avec le nombre de joueur à venir 
        pawnController = new PawnController(pawn);

        // Placer le pion à sa position de départ
        Case startCase = boardGame.getCase(pawn.getPosition());
        pawn1.setLayoutX(startCase.getX());
        pawn1.setLayoutY(startCase.getY());

        // Associer le bouton à l'action de déplacement
        buttonAdvance.setOnAction(event -> movePawn(1));
    }

    public void movePawn(int movement) {
        int oldX = pawn.getX();
        int oldY = pawn.getY();

        // Avancer le pion dans la logique du jeu
        pawnController.moveForward(movement, boardGame);

        int newX = pawn.getX();
        int newY = pawn.getY();

        // Animation vers la nouvelle position
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), pawn1);
        transition.setByX(newX - oldX);
        transition.setByY(newY - oldY);
        
        transition.setOnFinished(e -> {
            // Vérifie si le pion est sur la dernière case
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
        alert.showAndWait(); // Affiche la pop-up
    }


}
