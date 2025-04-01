package models;

import java.util.List;

public class Card {
	private String theme;
    private String subject;
    private List<Question> card;
	public List<Question> getCard() {
		return card;
	}
	public void setCard(List<Question> card) {
		this.card = card;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
