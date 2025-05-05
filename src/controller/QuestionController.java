package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

import exceptions.InvalidAnswerException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    @FXML private Button difficulty1Button;
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
        Stage currentStage = (Stage) themeLabel.getScene().getWindow();
        currentStage.close();

        Stage mainStage = (Stage) mainController.getGamePane().getScene().getWindow();

        Question questionObj = card.getQuestions().get(level - 1);
        this.question = questionObj;
        String questionText = questionObj.getQuestion();
        String expectedAnswer = questionObj.getAnswer();

        if (questionObj.getOptions() != null && questionObj.getOptions().size() == 4) {
            showMultipleChoiceDialog(questionText, questionObj.getOptions().toArray(new String[0]), level, mainStage, expectedAnswer);
            return;
        }

        int timeLimit = gameController.getTimeLimit();
        IntegerProperty timeSeconds = new SimpleIntegerProperty(timeLimit);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Question Level " + level);
        dialog.setHeaderText(null);
        dialog.initOwner(mainStage);

        StringBuilder questionDisplay = new StringBuilder(questionText);
        if (questionObj.getOptions() != null && !questionObj.getOptions().isEmpty()) {
            questionDisplay.append("\nOptions:\n");
            for (String option : questionObj.getOptions()) {
                questionDisplay.append("- ").append(option).append("\n");
            }
        }
        dialog.setContentText(questionDisplay.toString());

        if (externalTimerLabel != null) {
            externalTimerLabel.setText("Time left: " + timeSeconds.get() + "s");
        }

        Timeline countdown = new Timeline();
        countdown.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), e -> {
                int current = timeSeconds.get();
                timeSeconds.set(current - 1);
                if (externalTimerLabel != null) {
                    externalTimerLabel.setText("Time left: " + (current - 1) + "s");
                }
                if (current - 1 <= 0) {
                    countdown.stop();
                    dialog.setResult("");
                    dialog.close();
                }
            })
        );
        countdown.setCycleCount(timeLimit);
        countdown.play();

        Optional<String> result = dialog.showAndWait();
        countdown.stop();

        if (externalTimerLabel != null) {
            externalTimerLabel.setText("");
        }

        result.ifPresent(answer -> {
            try {
				if (checkAnswer(answer)) {
				    Alert success = new Alert(Alert.AlertType.INFORMATION, "Correct answer!");
				    success.initOwner(mainStage);
				    success.showAndWait();
				    mainController.movePawn(level);
				} else {
				    Alert fail = new Alert(Alert.AlertType.INFORMATION, "Incorrect answer.\nCorrect was: " + expectedAnswer);
				    fail.initOwner(mainStage);
				    fail.showAndWait();
				    mainController.movePawn(0);
				}
			} catch (InvalidAnswerException e1) {
				e1.printStackTrace();
			}
        });
    }

    public boolean checkAnswer(String userInput) throws InvalidAnswerException {
        return AnswerValidator.isCorrect(userInput, question.getAnswer());
    }

    @FXML private void handleDifficulty1() { handleDifficultyChoice(1); }
    @FXML private void handleDifficulty2() { handleDifficultyChoice(2); }
    @FXML private void handleDifficulty3() { handleDifficultyChoice(3); }
    @FXML private void handleDifficulty4() { handleDifficultyChoice(4); }

    private void showMultipleChoiceDialog(String questionText, String[] options, int level, Stage mainStage, String expectedAnswer) {
        Stage dialogStage = new Stage();
        dialogStage.initOwner(mainStage);
        dialogStage.setTitle("Multiple Choice Question");

        int timeLimit = gameController.getTimeLimit();
        IntegerProperty timeSeconds = new SimpleIntegerProperty(timeLimit);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setHalignment(javafx.geometry.HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setHalignment(javafx.geometry.HPos.CENTER);

        grid.getColumnConstraints().addAll(col1, col2);

        Text questionLabel = new Text(questionText);
        grid.add(questionLabel, 0, 0, 2, 1);

        Button[] buttons = new Button[4];
        double maxTextWidth = 0;
        double maxTextHeight = 0;

        for (String option : options) {
            Text tempText = new Text(option);
            double width = tempText.getLayoutBounds().getWidth();
            double height = tempText.getLayoutBounds().getHeight();
            if (width > maxTextWidth) maxTextWidth = width;
            if (height > maxTextHeight) maxTextHeight = height;
        }

        double buttonWidth = maxTextWidth + 60;
        double buttonHeight = maxTextHeight + 20;

        Timeline countdown = new Timeline();
        countdown.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), e -> {
                int current = timeSeconds.get();
                timeSeconds.set(current - 1);
                if (externalTimerLabel != null) {
                    externalTimerLabel.setText("Time left: " + (current - 1) + "s");
                }
                if (current - 1 <= 0) {
                    countdown.stop();
                    dialogStage.close();
                    if (externalTimerLabel != null) {
                        externalTimerLabel.setText("");
                    }
                    try {
						handleMultipleChoiceAnswer("", expectedAnswer, level, mainStage);
					} catch (InvalidAnswerException e1) {
						e1.printStackTrace();
					}
                }
            })
        );
        countdown.setCycleCount(timeLimit);
        countdown.play();

        for (int i = 0; i < 4; i++) {
            Button button = new Button(options[i]);
            button.setPrefWidth(buttonWidth);
            button.setPrefHeight(buttonHeight);

            button.setStyle(
                "-fx-background-color: #5DADE2; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 10; " +
                "-fx-font-size: 14px;"
            );

            button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #3498DB; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 10; " +
                "-fx-font-size: 14px;"
            ));
            button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #5DADE2; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 10; " +
                "-fx-font-size: 14px;"
            ));

            final String selectedOption = options[i];
            button.setOnAction(e -> {
                countdown.stop();
                dialogStage.close();
                if (externalTimerLabel != null) {
                    externalTimerLabel.setText("");
                }
                try {
					handleMultipleChoiceAnswer(selectedOption, expectedAnswer, level, mainStage);
				} catch (InvalidAnswerException e1) {
					e1.printStackTrace();
				}
            });

            buttons[i] = button;
        }

        grid.add(buttons[0], 0, 1);
        grid.add(buttons[1], 1, 1);
        grid.add(buttons[2], 0, 2);
        grid.add(buttons[3], 1, 2);

        VBox root = new VBox(grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(10);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void handleMultipleChoiceAnswer(String selectedAnswer, String expectedAnswer, int level, Stage mainStage) throws InvalidAnswerException {
        if (checkAnswer(selectedAnswer)) {
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Correct answer!");
            success.initOwner(mainStage);
            success.showAndWait();
            mainController.movePawn(level);
        } else {
            Alert fail = new Alert(Alert.AlertType.INFORMATION, "Incorrect answer.\nCorrect was: " + expectedAnswer);
            fail.initOwner(mainStage);
            fail.showAndWait();
            mainController.movePawn(0);
        }
    }
}
