package models;

import java.util.List;
import exceptions.InvalidQuestionException;

/**
 * Represents a question in the game, which may be open-ended or multiple-choice.
 */
public class Question {
    // The difficulty level of the question (1 to 4)
    private int difficulty;

    // The question text itself
    private String question;

    // List of multiple-choice options (null if open-ended)
    private List<String> options;

    // The correct answer to the question
    private String answer;

    // The subject or category of the question (e.g., football, languages, etc.)
    private String subject;

    /**
     * Constructs a Question with the specified attributes.
     * Throws an InvalidQuestionException if required fields are missing or invalid.
     *
     * @param difficulty the difficulty level (1 to 4)
     * @param question the text of the question
     * @param answer the correct answer
     * @param options list of possible options (can be null for open-ended)
     * @param subject the subject or theme of the question
     * @throws InvalidQuestionException if any required field is invalid
     */
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

    /**
     * Returns the question text.
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the question title (alias for getQuestion).
     * @return the title
     */
    public String getTitle() {
        return question;
    }

    /**
     * Sets the question title.
     * @param title the new title
     */
    public void setTitle(String title) {
        this.question = title;
    }

    /**
     * Returns the difficulty level.
     * @return the difficulty (1–4)
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level.
     * @param difficulty new difficulty value
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Returns the correct answer.
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the correct answer.
     * @param answer the new answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Returns the list of possible options (if any).
     * @return the list of options or null
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Sets the list of possible options.
     * @param options list of new options
     */
    public void setOptions(List<String> options) {
        this.options = options;
    }

    /**
     * Returns the subject or category of the question.
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject or category of the question.
     * @param subject the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Determines if the question is multiple-choice.
     * @return true if it has options, false otherwise
     */
    public boolean isMultipleChoice() {
        return options != null && !options.isEmpty();
    }
}
