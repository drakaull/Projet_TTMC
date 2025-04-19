package models;

public class Pawn {
	private String color;
	private int position; // numéro de case du jeu
    private int x,y;
    private Player owner;

    public Pawn(String nom) {
        this.position = 0; // Début du plateau
        this.x = 150;
        this.y = 720;
    }

   

    // les getters
    public int getPosition() {
        return position;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public Player getPlayer() {
    	return owner;
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
    
    public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

}
