package controller;

import models.Question;

public class QuestionController {
    private Question question;

    public QuestionController(Question question) {
        this.question = question;
    }

    // Check if the player's answer matches the correct one
    public boolean checkAnswer(String playerInput) {
        if (question.getAnswer() == null || playerInput == null) return false;
        return question.getAnswer().trim().equalsIgnoreCase(playerInput.trim());
    }

    public boolean isMultipleChoice() {
        return question.isMultipleChoice();
    }

    public Question getQuestion() {
        return question;
    }
} 
