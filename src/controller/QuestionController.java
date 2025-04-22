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
    private GameController gameController;
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

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void updateLabel() {
        if (card != null && playerName != null && themeLabel != null) {
            themeLabel.setText(playerName + ", how confident are you in " + card.getSubject() + "?");
        }
    }

    private void handleDifficultyChoice(int level) {
        Question questionObj = card.getQuestions().get(level - 1);
        this.question = questionObj; // store for checkAnswer
        String questionTextStr = questionObj.getQuestion();
        String expected = questionObj.getAnswer();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Level " + level + " Question");
        dialog.setHeaderText(null);
        StringBuilder questionText = new StringBuilder(questionTextStr);

        if (questionObj.getOptions() != null && !questionObj.getOptions().isEmpty()) {
            questionText.append("\nOptions :\n");
            for (String option : questionObj.getOptions()) {
                questionText.append("- ").append(option).append("\n");
            }
        }
        dialog.setContentText(questionText.toString());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(answer -> {
            if (checkAnswer(answer)) {
                Alert success = new Alert(Alert.AlertType.INFORMATION, "Correct answer!");
                success.showAndWait();
                mainController.movePawn(level);
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Wrong answer!\nThe correct answer was: " + expected);
                a.showAndWait();
                mainController.askNewCard();
            }
            if (gameController != null) {
                gameController.nextPlayer();
            }
        });

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
