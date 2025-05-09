package controller;

import exceptions.InvalidAnswerException;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Card;
import models.Question;
import utils.AnswerValidator;

import java.util.Optional;

public class QuestionController {

	// FXML UI components
	@FXML private Label themeLabel;
	@FXML private Label timerLabel;
	@FXML private Button difficulty1Button;

	// Game-related objects
	private Card card;
	private String playerName;
	private Question question;
	private BoardGameController mainController;
	private GameController gameController;
	private Label externalTimerLabel;

	// Setters for initializing controller fields
	public void setExternalTimerLabel(Label label) { this.externalTimerLabel = label; }
	public void setCard(Card card) { this.card = card; updateLabel(); }
	public void setPlayerName(String name) { this.playerName = name; updateLabel(); }
	public void setBoardGameController(BoardGameController controller) { this.mainController = controller; }
	public void setGameController(GameController gameController) { this.gameController = gameController; }

	// Update question prompt based on player and card subject
	private void updateLabel() {
		if (card != null && playerName != null && themeLabel != null) {
			themeLabel.setText(playerName + ", how confident are you in " + card.getSubject() + "?");
		}
	}

	// Difficulty level selection
	@FXML private void handleDifficulty1() { handleDifficultyChoice(1); }
	@FXML private void handleDifficulty2() { handleDifficultyChoice(2); }
	@FXML private void handleDifficulty3() { handleDifficultyChoice(3); }
	@FXML private void handleDifficulty4() { handleDifficultyChoice(4); }

	// Load question based on selected difficulty
	private void handleDifficultyChoice(int level) {
		Stage currentStage = (Stage) themeLabel.getScene().getWindow();
		currentStage.close();

		question = card.getQuestions().get(level - 1);
		Stage mainStage = (Stage) mainController.getGamePane().getScene().getWindow();

		if (isMultipleChoice(question)) {
			showMultipleChoiceDialog(question, level, mainStage);
		} else {
			showTextInputDialog(question, level, mainStage);
		}
	}

	// Determine if question is multiple choice
	private boolean isMultipleChoice(Question q) {
		return q.getOptions() != null && q.getOptions().size() == 4;
	}

	// Show dialog for free-text questions
	private void showTextInputDialog(Question q, int level, Stage mainStage) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Question Level " + level);
		dialog.setHeaderText(null);
		dialog.initOwner(mainStage);

		// Hide cancel button
		Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		if (cancelButton != null) {
			cancelButton.setVisible(false);
			cancelButton.setManaged(false);
		}

		// Style the dialog
		DialogPane pane = dialog.getDialogPane();
		pane.getStyleClass().add("custom-dialog");
		pane.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

		dialog.setContentText(q.getQuestion());

		final boolean[] answered = {false};
		final Timeline[] timelineHolder = new Timeline[1];

		// Start countdown timer
		timelineHolder[0] = startTimer(gameController.getTimeLimit(), () -> {
			if (!answered[0]) {
				answered[0] = true;
				dialog.setResult("");
				dialog.close();
				if (externalTimerLabel != null) externalTimerLabel.setText("");
				Platform.runLater(() -> handleAnswer("", q.getAnswer(), level, mainStage));
			}
		});

		Optional<String> result = dialog.showAndWait();
		if (!answered[0]) {
			answered[0] = true;
			if (timelineHolder[0] != null) timelineHolder[0].stop();
			if (externalTimerLabel != null) externalTimerLabel.setText("");
			handleAnswer(result.orElse(""), q.getAnswer(), level, mainStage);
		}
	}

	// Show dialog for multiple-choice questions
	private void showMultipleChoiceDialog(Question q, int level, Stage mainStage) {
	    Stage dialogStage = new Stage();
	    dialogStage.initOwner(mainStage);
	    dialogStage.setTitle("Multiple Choice Question");

	    VBox layout = new VBox(15);
	    layout.setAlignment(Pos.CENTER);
	    layout.setPadding(new Insets(20));
	    layout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #a0522d, #d2691e, #f4a460);");

	    Text questionText = new Text(q.getQuestion());
	    questionText.setStyle("-fx-font-size: 16px; -fx-fill: white;");
	    layout.getChildren().add(questionText);

	    String[] options = q.getOptions().toArray(new String[0]);
	    final Timeline[] timelineHolder = new Timeline[1];

	    // Calculate maximum width for buttons
	    Text tempText = new Text();
	    double maxWidth = 0;
	    for (String option : options) {
	        tempText.setText(option);
	        tempText.applyCss();
	        double width = tempText.getLayoutBounds().getWidth();
	        if (width > maxWidth) maxWidth = width;
	    }
	    double buttonWidth = maxWidth + 60; 

	    // Create and style buttons
	    for (String option : options) {
	        Button button = new Button(option);
	        button.setPrefWidth(buttonWidth);
	        button.setPrefHeight(40);
	        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #eee;");
	        button.setOnAction(e -> {
	            if (timelineHolder[0] != null) timelineHolder[0].stop();
	            dialogStage.close();
	            if (externalTimerLabel != null) externalTimerLabel.setText("");
	            handleAnswer(option, q.getAnswer(), level, mainStage);
	        });
	        layout.getChildren().add(button);
	    }

	    // Timer for timeout handling
	    timelineHolder[0] = startTimer(gameController.getTimeLimit(), () -> {
	        dialogStage.close();
	        if (externalTimerLabel != null) externalTimerLabel.setText("");
	        handleAnswer("", q.getAnswer(), level, mainStage);
	    });

	    Scene scene = new Scene(layout);
	    scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
	    dialogStage.setScene(scene);
	    dialogStage.sizeToScene(); 
	    dialogStage.show();
	}

	// Starts a countdown and updates external timer label
	private Timeline startTimer(int seconds, Runnable onTimeout) {
		IntegerProperty time = new SimpleIntegerProperty(seconds);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			int current = time.get();
			time.set(current - 1);
			updateTimerDisplay(current - 1);
			if (current - 1 <= 0) {
				onTimeout.run();
			}
		}));
		timeline.setCycleCount(seconds);
		timeline.play();
		return timeline;
	}

	// Updates the timer label each second
	private void updateTimerDisplay(int secondsLeft) {
		if (externalTimerLabel != null) {
			externalTimerLabel.setText("Time left: " + secondsLeft + "s");
		}
	}

	// Evaluate and process the player's answer
	private void handleAnswer(String input, String expected, int level, Stage mainStage) {
		Platform.runLater(() -> {
			try {
				gameController.markCardAsUsed(card); // Mark card used
				boolean correct = AnswerValidator.isCorrect(input, expected);

				if (correct) {
					showAlert("Correct answer!", mainStage);
					mainController.movePawn(level);
				} else {
					if (input == null || input.isEmpty()) {
						showTimedAlert("Time finished!", mainStage, () -> mainController.nextPlayer());
					} else {
						showAlert("Incorrect answer.\nCorrect was: " + expected, mainStage);
						String caseType = mainController.getCurrentCaseType();
						if ("Random".equals(caseType)) {
							mainController.movePawn(-level);
						} else {
							mainController.movePawn(0);
						}
					}
				}
			} catch (InvalidAnswerException e) {
				e.printStackTrace();
			}
		});
	}

	// Show styled alert dialog with confirmation
	private void showAlert(String message, Stage stage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		styleAlert(alert, stage);
		alert.showAndWait();
	}

	// Show styled alert that disappears after 2 seconds
	private void showTimedAlert(String message, Stage stage, Runnable afterClose) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		styleAlert(alert, stage);
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished(e -> {
			alert.close();
			if (afterClose != null) afterClose.run();
		});
		alert.show();
		delay.play();
	}

	// Applies stylesheet and custom class to alert dialog
	private void styleAlert(Alert alert, Stage stage) {
		alert.initOwner(stage);
		alert.setHeaderText(null);
		DialogPane pane = alert.getDialogPane();
		pane.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		pane.getStyleClass().add("custom-dialog");
	}
}
