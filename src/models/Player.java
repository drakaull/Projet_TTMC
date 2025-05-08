package models;

/**
 * Represents a player in the game.
 */
public class Player {
    // The name of the player
    private String name;

    // The player's current score
    private int score;

    // The player's current position on the board
    private int position;

    // The pawn associated with the player on the board
    private Pawn pawn;

    /**
     * Constructor to create a player with a given name.
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Gets the player's name.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's score.
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     * @param score the new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the player's position on the board.
     * @return the current position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the player's position on the board.
     * @param position the new position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gets the player's pawn.
     * @return the pawn associated with this player
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Sets the pawn associated with the player.
     * @param pawn the new pawn
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
}
