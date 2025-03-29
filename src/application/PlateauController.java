package application;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PlateauController {
    
    @FXML
    private Circle pawn1; // Injection du pion depuis le FXML
    
    @FXML
    private Button buttonAdvance; // Injection du bouton depuis le FXML

    private Pion pion;
    private Plateau plateau;

    public void initialize() {
        // Initialisation du plateau et du pion
        plateau = new Plateau();
        pion = new Pion("Joueur1");

        // Placer le pion à sa position de départ
        Case caseDepart = plateau.getCase(pion.getPosition());
        pawn1.setLayoutX(caseDepart.getX());
        pawn1.setLayoutY(caseDepart.getY());

        // Associer le bouton à l'action de déplacement
        buttonAdvance.setOnAction(event -> deplacerPion(1));
    }

    public void deplacerPion(int deplacement) {
        int oldX = pion.getX();
        int oldY = pion.getY();

        // Avancer le pion dans la logique du jeu
        pion.avancer(deplacement, plateau);

        int newX = pion.getX();
        int newY = pion.getY();

        // Animation vers la nouvelle position
        TranslateTransition transition = new TranslateTransition(Duration.millis(500), pawn1);
        transition.setByX(newX - oldX);
        transition.setByY(newY - oldY);
        
        transition.setOnFinished(e -> {
            // Mise à jour définitive de la position
            pawn1.setLayoutX(newX);
            pawn1.setLayoutY(newY);
        });

        transition.play();
    }
}
