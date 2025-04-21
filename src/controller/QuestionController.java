package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import models.Card;
import models.Question;

import java.util.Optional;

public class QuestionController {

    @FXML
    private Label themeLabel;

    private Card card;
    private String playerName;
    private BoardGameController mainController;

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
        dialog.setContentText(question);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(answer -> {
            if (answer.equalsIgnoreCase(expected)) {
                mainController.movePawn(level); // Cette méthode relance le tour après déplacement
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Wrong answer!");
                a.showAndWait();

                // 👇 Reposer une nouvelle carte
                mainController.askNewCard();
            }
        });


        ((Stage) themeLabel.getScene().getWindow()).close();
    }


    @FXML private void handleDifficulty1() { handleDifficultyChoice(1); }
    @FXML private void handleDifficulty2() { handleDifficultyChoice(2); }
    @FXML private void handleDifficulty3() { handleDifficultyChoice(3); }
    @FXML private void handleDifficulty4() { handleDifficultyChoice(4); }
}

