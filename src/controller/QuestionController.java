package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Card;
import models.Question;
import utils.AnswerValidator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionController {

    @FXML
    private Label themeLabel;
    @FXML
    private Label timerLabel;


    private Card card;
    private String playerName;
    private BoardGameController mainController;
    private GameController gameController;
    private Question question;
    @FXML private Button difficulty1Button; // Un seul bouton suffit à identifier la fenêtre
    private Label externalTimerLabel;
    
    public void setExternalTimerLabel(Label label) {
        this.externalTimerLabel = label;
    }



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
        // Fermer la fenêtre de choix de difficulté
        Stage currentStage = (Stage) themeLabel.getScene().getWindow();
        currentStage.close();

        Stage mainStage = (Stage) mainController.getGamePane().getScene().getWindow();

        Question questionObj = card.getQuestions().get(level - 1);
        this.question = questionObj;
        String questionTextStr = questionObj.getQuestion();
        String expected = questionObj.getAnswer();

        // Temps limite selon difficulté de la partie
        int timeLimit = gameController.getTimeLimit();


        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Question niveau " + level);
        dialog.setHeaderText(null);
        dialog.initOwner(mainStage);

        StringBuilder questionText = new StringBuilder(questionTextStr);
        if (questionObj.getOptions() != null && !questionObj.getOptions().isEmpty()) {
            questionText.append("\nOptions :\n");
            for (String option : questionObj.getOptions()) {
                questionText.append("- ").append(option).append("\n");
            }
        }
        dialog.setContentText(questionText.toString());

        // Timer avec Timeline
        Timeline countdown = new Timeline();
        IntegerProperty timeSeconds = new SimpleIntegerProperty(timeLimit);
        
        if (externalTimerLabel != null) {
            externalTimerLabel.setText("Temps restant : " + timeSeconds.get() + "s");
        }

        countdown.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), e -> {
                int current = timeSeconds.get();
                timeSeconds.set(current - 1);
                if (externalTimerLabel != null) {
                    externalTimerLabel.setText("Temps restant : " + (current - 1) + "s");
                }
                if (current - 1 <= 0) {
                    countdown.stop();
                    dialog.setResult(""); // Vide pour simuler une non-réponse
                    dialog.close();
                }
            })
        );
        countdown.setCycleCount(timeLimit);
        countdown.play();

        Optional<String> result = dialog.showAndWait();
        countdown.stop(); // arrêter si la réponse est donnée

        if (externalTimerLabel != null) {
            externalTimerLabel.setText(""); // effacer timer
        }

        result.ifPresent(answer -> {
            if (checkAnswer(answer)) {
                Alert success = new Alert(Alert.AlertType.INFORMATION, "Bonne réponse !");
                success.initOwner(mainStage);
                success.showAndWait();
                mainController.movePawn(level);
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Mauvaise réponse.\nLa bonne était : " + expected);
                a.initOwner(mainStage);
                a.showAndWait();
                mainController.movePawn(0);
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
