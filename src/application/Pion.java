package application;

public class Pion {
    private int position;
    private String nom;

    public Pion(String nom) {
        this.nom = nom;
        this.position = 0; // Début du plateau
    }

    public void avancer(int deplacement, int taillePlateau) {
        position += deplacement;
        if (position >= taillePlateau) {
            position = taillePlateau - 1; // Ne dépasse pas la fin du plateau
        }
    }

    public int getPosition() {
        return position;
    }

    public String getNom() {
        return nom;
    }
}
