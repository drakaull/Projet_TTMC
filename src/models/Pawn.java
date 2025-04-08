package models;

public class Pawn {
    private int position; // numéro de case du jeu
    private String name;
    private int x,y;

    public Pawn(String nom) {
        this.name = nom;
        this.position = 0; // Début du plateau
        this.x = 150;
        this.y = 720;
    }

    public void moveForward(int movement, BoardGame boardgame) {
        position += movement;
        if (position >= boardgame.getSize()) {
            position = boardgame.getSize() - 1; // Ne dépasse pas la fin du plateau
        }
        
        Case newCase = boardgame.getCase(position);
        x = newCase.getX();
        y = newCase.getY();
    }

    // les getters
    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
}
