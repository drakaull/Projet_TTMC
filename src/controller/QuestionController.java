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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Card;
import models.Question;
import utils.AnswerValidator;

import java.util.Optional;

public class QuestionController {

	@FXML
	private Label themeLabel;
	@FXML
	private Label timerLabel;
	@FXML
	private Button difficulty1Button;

	private Card card;
	private String playerName;
	private Question question;
	private BoardGameController mainController;
	private GameController gameController;
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

	@FXML
	private void handleDifficulty1() {
		handleDifficultyChoice(1);
	}

	@FXML
	private void handleDifficulty2() {
		handleDifficultyChoice(2);
	}

	@FXML
	private void handleDifficulty3() {
		handleDifficultyChoice(3);
	}

	@FXML
	private void handleDifficulty4() {
		handleDifficultyChoice(4);
	}

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

	private boolean isMultipleChoice(Question q) {
		return q.getOptions() != null && q.getOptions().size() == 4;
	}

	private void showTextInputDialog(Question q, int level, Stage mainStage) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Question Level " + level);
		dialog.setHeaderText(null);
		dialog.initOwner(mainStage);

		// hide cancel button
		Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		if (cancelButton != null) {
			cancelButton.setVisible(false);
			cancelButton.setManaged(false);
		}

		StringBuilder content = new StringBuilder(q.getQuestion());
		if (q.getOptions() != null && !q.getOptions().isEmpty()) {
			content.append("\nOptions:\n");
			q.getOptions().forEach(opt -> content.append("- ").append(opt).append("\n"));
		}
		dialog.setContentText(content.toString());

		final boolean[] answered = { false };

		final Timeline[] timelineHolder = new Timeline[1];
		timelineHolder[0] = startTimer(gameController.getTimeLimit(), () -> {
		    if (!answered[0]) {
		        answered[0] = true;
		        dialog.setResult("");
		        dialog.close();
		        if (externalTimerLabel != null)
		            externalTimerLabel.setText("");

		        // Call handleAnswer to move on to the next player
		        Platform.runLater(() -> {
		            handleAnswer("", q.getAnswer(), level, mainStage);
		        });
		    }
		});


		Optional<String> result = dialog.showAndWait();
		if (!answered[0]) {
			answered[0] = true;
			if (timelineHolder[0] != null)
				timelineHolder[0].stop();
			if (externalTimerLabel != null)
				externalTimerLabel.setText("");
			handleAnswer(result.orElse(""), q.getAnswer(), level, mainStage);
		}
	}

	private void showMultipleChoiceDialog(Question q, int level, Stage mainStage) {
		Stage dialogStage = new Stage();
		dialogStage.initOwner(mainStage);
		dialogStage.setTitle("Multiple Choice Question");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));
		grid.setAlignment(Pos.CENTER);
		grid.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints());

		grid.add(new Text(q.getQuestion()), 0, 0, 2, 1);

		String[] options = q.getOptions().toArray(new String[0]);
		final Timeline[] timelineHolder = new Timeline[1];

		for (int i = 0; i < options.length; i++) {
			Button button = new Button(options[i]);
			button.setPrefSize(200, 40);
			final String selected = options[i];
			button.setOnAction(e -> {
				if (timelineHolder[0] != null)
					timelineHolder[0].stop();
				dialogStage.close();
				if (externalTimerLabel != null)
					externalTimerLabel.setText("");
				handleAnswer(selected, q.getAnswer(), level, mainStage);
			});
			grid.add(button, i % 2, 1 + i / 2);
		}

		timelineHolder[0] = startTimer(gameController.getTimeLimit(), () -> {
			dialogStage.close();
			if (externalTimerLabel != null)
				externalTimerLabel.setText("");
			handleAnswer("", q.getAnswer(), level, mainStage);
		});

		VBox root = new VBox(grid);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20));
		dialogStage.setScene(new Scene(root));
		dialogStage.show();
	}

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

	private void updateTimerDisplay(int secondsLeft) {
		if (externalTimerLabel != null) {
			externalTimerLabel.setText("Time left: " + secondsLeft + "s");
			String style = "-fx-font-size: " + (secondsLeft <= 5 ? 28 : secondsLeft <= 10 ? 20 : 14)
					+ "px; -fx-text-fill: red;";
			externalTimerLabel.setStyle(style);
		}
	}

	private void handleAnswer(String input, String expected, int level, Stage mainStage) {
		Platform.runLater(() -> {
			try {
				gameController.markCardAsUsed(card);
				boolean correct = AnswerValidator.isCorrect(input, expected);
				if (correct) {
					showAlert("Correct answer!", mainStage);
					mainController.movePawn(level);
				} else {
					if (input == null || input.isEmpty()) {
						showTimedAlert("Time finished!", mainStage, () -> mainController.nextPlayer());
					} else {
						showAlert("Incorrect answer.\nCorrect was: " + expected, mainStage);
						mainController.movePawn(0);
					}
				}
			} catch (InvalidAnswerException e) {
				e.printStackTrace();
			}
		});
	}

	private void showAlert(String message, Stage stage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		styleAlert(alert, stage);
		alert.showAndWait();
	}

	private void showTimedAlert(String message, Stage stage, Runnable afterClose) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
		styleAlert(alert, stage);
		PauseTransition delay = new PauseTransition(Duration.seconds(2));
		delay.setOnFinished(e -> {
			alert.close();
			if (afterClose != null)
				afterClose.run();
		});
		alert.show();
		delay.play();
	}

	private void styleAlert(Alert alert, Stage stage) {
		alert.initOwner(stage);
		alert.setHeaderText(null);
		DialogPane pane = alert.getDialogPane();
		pane.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		pane.getStyleClass().add("custom-alert");
	}

}
