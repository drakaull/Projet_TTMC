package application;

public class Pion {
    private int position; // numéro de case du jeu
    private String nom;
    private int x,y;

    public Pion(String nom) {
        this.nom = nom;
        this.position = 0; // Début du plateau
        this.x = 0;
        this.y = 0;
    }

    public void avancer(int deplacement, int taillePlateau) {
        position += deplacement;
        if (position >= taillePlateau) {
            position = taillePlateau - 1; // Ne dépasse pas la fin du plateau
        }
        
        Case nouvelleCase = plateau.getCase(position);
    }

    public int getPosition() {
        return position;
    }

    public String getNom() {
        return nom;
    }
}
