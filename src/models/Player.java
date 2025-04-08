package models;

import javafx.scene.paint.Color;

public class Player {
	private String pseudo;
	private int score;
	private Pawn pawn;
	
	public Player(String pseudo, int score, Color color) {
		super();
		setPseudo(pseudo);
		setScore(score);
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if (score > 0) {
			this.score = score;
		} else {
			this.score = 0;
		}
	}

	public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public Pawn getPawn() {
        return pawn;
    }
	
	
	
	
}
