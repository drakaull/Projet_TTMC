package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import models.Card;
import models.Question;
import utils.AnswerValidator;

import java.util.Optional;

public class QuestionController {

    @FXML
    private Label themeLabel;

    private Card card;
    private String playerName;
    private BoardGameController mainController;
    private Question question;

    public void setCard(Card card) {
        this.card = card;
        updateLabel();
    }

    public void setPlayerName(String name) {
        this.playerName = name;
        updateLabel();
    }

    public void setBoardGameController(BoardGameController controller) {
        this.mainController = controller;
    }

    private void updateLabel() {
        if (card != null && playerName != null && themeLabel != null) {
            themeLabel.setText(playerName + ", how confident are you in " + card.getSubject() + "?");
        }
    }

    private void handleDifficultyChoice(int level) {
        Question questionObj = card.getQuestions().get(level - 1);
        String question = questionObj.getQuestion();         // ou getQuestion()
        String expected = questionObj.getAnswer();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Level " + level + " Question");
        dialog.setHeaderText(null);
        StringBuilder questionText = new StringBuilder(question);
        
        if(questionObj.getOptions() != null) {
        	questionText.append("\nOptions :\n");
        	for(int i=0 ; i< questionObj.getOptions().size() ; i++) {
        		String option = questionObj.getOptions().get(i);
        		questionText.append("-").append(option).append("\n");
        	}
        	dialog.setContentText(questionText.toString());
        }

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(answer -> {
            if (answer.equalsIgnoreCase(expected)) {
                mainController.movePawn(level); // Cette méthode relance le tour après déplacement
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Wrong answer!\nThe correct answer was: "+expected);
                a.showAndWait();

                // 👇 Reposer une nouvelle carte
                mainController.askNewCard();
            }
        });
        mainController.nextPlayer();


        ((Stage) themeLabel.getScene().getWindow()).close();
    }
    
    public boolean checkAnswer(String userInput) {
        return AnswerValidator.isCorrect(userInput, question.getAnswer());
    }


    @FXML private void handleDifficulty1() { handleDifficultyChoice(1); }
    @FXML private void handleDifficulty2() { handleDifficultyChoice(2); }
    @FXML private void handleDifficulty3() { handleDifficultyChoice(3); }
    @FXML private void handleDifficulty4() { handleDifficultyChoice(4); }
}

