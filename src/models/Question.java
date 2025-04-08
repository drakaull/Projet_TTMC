package models;

import java.util.List;

public class Question {
	private int difficulty;	
	private String question;		//the question itself		
	private List<String> options;	//null if open question	
	private String answer;			// the correct answer
	private String subject;			//the subject of the question (football, langages, etc.)

	public Question(int difficulty, String question, String answer, List<String> options, String subject) {
		this.answer = answer;
		this.question = question;
		this.difficulty = difficulty;
		this.options = options;
		this.subject = subject;
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
