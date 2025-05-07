package models;

import java.util.List;

import exceptions.InvalidCardException;

public class Card {
	private String theme; 				// global theme
	private String subject; 			// subject (football, geography, etc)
	private List<Question> questions; 	// the 4 questions

	public Card(String theme, String subject, List<Question> questions) throws InvalidCardException {
        if (theme == null || theme.trim().isEmpty()) {
            throw new InvalidCardException();
        }
        if (subject == null || subject.trim().isEmpty()) {
            throw new InvalidCardException();
        }
        if (questions == null || questions.isEmpty()) {
            throw new InvalidCardException();
        }
        if (questions.size() < 4) {
            throw new InvalidCardException();
        }

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

	public Question getQuestionByLevel(int difficulty) throws InvalidCardException {
	    return questions.stream()
	            .filter(q -> q.getDifficulty() == difficulty)
	            .findFirst()
	            .orElseThrow(() -> new InvalidCardException());
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Card card = (Card) o;
	    return subject.equals(card.subject);
	}

	@Override
	public int hashCode() {
	    return subject.hashCode();
	}
}
