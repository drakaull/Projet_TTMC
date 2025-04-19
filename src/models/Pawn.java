package models;

public class Pawn {
    private String color;
    private int position;
    private Player owner;

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
