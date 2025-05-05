package models;

import java.util.List;

import exceptions.InvalidQuestionException;

public class Question {
	private int difficulty;	
	private String question;		//the question itself		
	private List<String> options;	//null if open question	
	private String answer;			//the correct answer
	private String subject;			//the subject of the question (football, langages, etc.)


    public Question(int difficulty, String question, String answer, List<String> options, String subject) throws InvalidQuestionException {
        if (question == null || question.trim().isEmpty()) {
            throw new InvalidQuestionException();
        }
        if (answer == null || answer.trim().isEmpty()) {
            throw new InvalidQuestionException();
        }
        if (subject == null || subject.trim().isEmpty()) {
            throw new InvalidQuestionException();
        }
        if (difficulty < 1 || difficulty > 4) {
            throw new InvalidQuestionException();
        }

        this.answer = answer;
        this.question = question;
        this.difficulty = difficulty;
        this.options = options;
        this.subject = subject;
    }

	public String getQuestion() {
		return question;
	}

	public String getTitle() {
        return question;
    }

    public void setTitle(String title) {
        this.question = title;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isMultipleChoice() {
        return options != null && !options.isEmpty();
    }

}
