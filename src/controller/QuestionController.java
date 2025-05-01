package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    @FXML private Button difficulty1Button; // Un seul bouton suffit à identifier la fenêtre


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
        // Récupérer et fermer la fenêtre de choix de difficulté
        Stage currentStage = (Stage) themeLabel.getScene().getWindow();
        currentStage.close();

        // Récupérer la fenêtre principale (le plateau)
        Stage mainStage = (Stage) mainController.getGamePane().getScene().getWindow();

        Question questionObj = card.getQuestions().get(level - 1);
        this.question = questionObj;
        String questionTextStr = questionObj.getQuestion();
        String expected = questionObj.getAnswer();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Level " + level + " Question");
        dialog.setHeaderText(null);
        dialog.initOwner(mainStage); // ✅ Attache la fenêtre principale
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
                success.initOwner(mainStage); // ✅ aussi pour les Alert
                success.showAndWait();
                mainController.movePawn(level);
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Wrong answer!\nThe correct answer was: " + expected);
                a.initOwner(mainStage); // ✅ aussi pour les Alert
                a.showAndWait();
                mainController.movePawn(0);
            }
            if (gameController != null) {
                gameController.nextPlayer();
            }
        });
    }






    public boolean checkAnswer(String userInput) {
        return AnswerValidator.isCorrect(userInput, question.getAnswer());
    }

    @FXML private void handleDifficulty1() { handleDifficultyChoice(1); }
    @FXML private void handleDifficulty2() { handleDifficultyChoice(2); }
    @FXML private void handleDifficulty3() { handleDifficultyChoice(3); }
    @FXML private void handleDifficulty4() { handleDifficultyChoice(4); }
}
