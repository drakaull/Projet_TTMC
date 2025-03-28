package application;

public class Pion {
    private int position; // numéro de case du jeu
    private String nom;
    private int x,y;

    public Pion(String nom) {
        this.nom = nom;
        this.position = 0; // Début du plateau
        this.x = 150;
        this.y = 720;
    }

    public void avancer(int deplacement, Plateau plateau) {
        position += deplacement;
        if (position >= plateau.getTaille()) {
            position = plateau.getTaille() - 1; // Ne dépasse pas la fin du plateau
        }
        
        Case nouvelleCase = plateau.getCase(position);
        x = nouvelleCase.getX();
        y = nouvelleCase.getY();
    }

    // les getters
    public int getPosition() {
        return position;
    }

    public String getNom() {
        return nom;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
}
