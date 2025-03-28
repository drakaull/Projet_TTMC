package application;

import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PlateauController {
	
	private Circle pionCircle;
	
	private Pion pion;
	private Plateau plateau;
	
	public void initialize() {
		// initialisation du plateau et du pion
		plateau = new Plateau();
		pion = new Pion("Joueur1");
		
		// placer le pion à sa position de départ
		Case caseDepart = plateau.getCase(pion.getPosition());
		pionCircle.setLayoutX(caseDepart.getX());
		pionCircle.setLayoutY(caseDepart.getY());
	}
	
	public void deplacerPion(int deplacement) {
		pion.avancer(deplacement, plateau);
		
		// animation vers la nouvelle zone
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.millis(500)); // temps depl en millisec
		transition.setToX(pion.getX() - pionCircle.getLayoutX());
		transition.setToY(pion.getY() - pionCircle.getLayoutY());
		
		// appliquer le déplacement à la position réelle du Circle
		transition.setOnFinished(e -> {
			// mets à jour la position du Circle (permanente)
			pionCircle.setLayoutX(pion.getX());
			pionCircle.setLayoutY(pion.getY());
			
			// réinitialisation de la translation pour le prochain dépl
			pionCircle.setTranslateX(0);
			pionCircle.setTranslateY(deplacement);
			
		});
		
		transition.play();
	}
}
