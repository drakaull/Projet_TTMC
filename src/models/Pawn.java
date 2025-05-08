package models;

public class Pawn {
    private String color; // color of the pawn (used for display)
    private int position; // The position of the pawn on the board (index)
    private Player owner; // The player who owns this pawn

    // Constructor to create a pawn with color, position and owner
    public Pawn(String color, int position, Player owner) {
        this.color = color;
        this.position = position;
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
