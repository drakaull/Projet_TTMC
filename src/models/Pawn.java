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
    
    // les setters 
    public void setPosition(int position) {
    	this.position = position;
    }
    
    public void setX(int x) {
    	this.x=x;
    }
    public void setY(int y) {
    	this.y=y;
    }
}
