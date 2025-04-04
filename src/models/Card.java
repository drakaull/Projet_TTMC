package models;

import java.util.ArrayList;
import java.util.List;

public class Card {
	private String theme; // global theme
	private String subject; // subject (football, geography, etc)
	private List<Question> questions; // the 4 questions

	public Card(String theme, String subject, List<Question> questions) {
		this.theme = theme;
		this.subject = subject;
		this.questions = questions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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

	public Question getQuestionByLevel(int difficulty) {
		return questions.stream().filter(q -> q.getDifficulty() == difficulty).findFirst().orElse(null);
	}
}
