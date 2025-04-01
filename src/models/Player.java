package models;

import javafx.scene.paint.Color;

public class Player {
	private String pseudo;
	private int score;
	private Color color;
	
	public Player(String pseudo, int score, Color color) {
		super();
		setColor(color);
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	
}
