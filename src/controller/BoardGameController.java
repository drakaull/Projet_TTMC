package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.NoMoreThemeCardsException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.BoardGame;
import models.Card;
import models.Case;
import models.Pawn;
import models.Player;
import strategyDifficulty.EasyDifficulty;
import strategyDifficulty.HardDifficulty;
import strategyDifficulty.MediumDifficulty;
import utils.CardDataLoader;

public class BoardGameController {

    @FXML
    private Button pickACardBtn;

    @FXML
    private AnchorPane gamePane;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label timerLabel;

    private GameController gameController;
    private BoardGame boardGame;
    private Map<Player, Circle> playerCircles = new HashMap<>();
    private AudioClip backgroundMusic;

    private Stage stage;
    private Scene scene;

    // Initialize game with game controller and setup board and players
    public void setGameController(GameController gameController) throws NoMoreThemeCardsException {
        this.gameController = gameController;
        initializeGame();
    }

    // Create the board, initialize pawns, and set their colors and positions
    private void initializeGame() throws NoMoreThemeCardsException {
        boardGame = new BoardGame();
        List<Player> players = gameController.getPlayers();
        List<Color> colors = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);

        int i = 0;
        for (Player player : players) {
            Pawn pawn = new Pawn(player.getName(), 0, player);
            player.setPawn(pawn);

            Circle circle = new Circle(10);
            circle.setFill(colors.get(i % colors.size())); // Rotate colors if more than 4 players
            circle.setId(player.getName());
            gamePane.getChildren().add(circle);
            playerCircles.put(player, circle);

            Case start = boardGame.getCase(0);

            // Offset positioning so that circles don’t overlap
            double offsetX = 0;
            double offsetY = 0;

            switch (i) {
                case 0:
                    offsetX = -10;
                    offsetY = -10;
                    break;
                case 1:
                    offsetX = 10;
                    offsetY = -10;
                    break;
                case 2:
                    offsetX = -10;
                    offsetY = 10;
                    break;
                case 3:
                    offsetX = 10;
                    offsetY = 10;
                    break;
                default:
                    offsetX = 0;
                    offsetY = 0;
                    break;
            }

            circle.setTranslateX(start.getX() + offsetX);
            circle.setTranslateY(start.getY() + offsetY);

            i++;
        }
        startBackgroundMusic(); // Start game music
        updatePlayerLabel(); // Display current player's name
    }

    // Load and start looping background music
    private void startBackgroundMusic() {
        try {
            URL resource = getClass().getResource("/resource/music/background.mp3");
            if (resource == null) {
                System.out.println("Erreur : fichier mp3 non trouvé !");
                return;
            }

            backgroundMusic = new AudioClip(resource.toExternalForm());
            backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
            backgroundMusic.setVolume(0.8);
            backgroundMusic.play();

        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la musique : " + e.getMessage());
        }
    }

    // Move to the next player and update the UI
    public void nextPlayer() {
        gameController.nextPlayer();
        updatePlayerLabel();
        // askNewCard(); // Can be triggered manually now
    }

    // When the player clicks the "Pick a Card" button
    @FXML
    private void handlePickCardButton() {
        try {
            askNewCard();
        } catch (NoMoreThemeCardsException e) {
            e.printStackTrace();
        }
    }

    // Animate pawn movement and trigger next turn logic
    public void movePawn(int movement) {
        Player currentPlayer = gameController.getCurrentPlayer();
        PawnController pawnController = new PawnController(currentPlayer.getPawn());
        pawnController.moveForward(movement, boardGame);

        Case newCase = boardGame.getCase(currentPlayer.getPawn().getPosition());
        Circle circle = playerCircles.get(currentPlayer);

        // Offset to avoid overlapping pawns visually
        int playerIndex = gameController.getPlayers().indexOf(currentPlayer);
        double offsetX = 0;
        double offsetY = 0;

        switch (playerIndex) {
            case 0 -> {
                offsetX = -10;
                offsetY = -10;
            }
            case 1 -> {
                offsetX = 10;
                offsetY = -10;
            }
            case 2 -> {
                offsetX = -10;
                offsetY = 10;
            }
            case 3 -> {
                offsetX = 10;
                offsetY = 10;
            }
            default -> {
                offsetX = 0;
                offsetY = 0;
            }
        }

        // Translate animation for pawn movement
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), circle);
        transition.setToX(newCase.getX() + offsetX);
        transition.setToY(newCase.getY() + offsetY);

        transition.setOnFinished(e -> {
            if (currentPlayer.getPawn().getPosition() == boardGame.getTotalCases() - 1) {
                // Show victory screen after a delay
                Platform.runLater(() -> {
                    PauseTransition delay = new PauseTransition(Duration.millis(100));
                    delay.setOnFinished(ev -> showVictoryPopup(currentPlayer.getName()));
                    delay.play();
                });
            } else {
                gameController.nextPlayer();
                updatePlayerLabel();
            }
        });

        transition.play();
    }

    // Get a new question card based on the current case type
    public void askNewCard() throws NoMoreThemeCardsException {
        Player currentPlayer = gameController.getCurrentPlayer();
        Case currentCase = boardGame.getCase(currentPlayer.getPawn().getPosition());
        String theme = currentCase.getType();

        Card card;

        if ("Start".equals(theme) || "Random".equals(theme)) {
            card = CardDataLoader.getRandomCard();
        } else {
            card = gameController.drawCardForTheme(theme);
        }

        if (card == null) {
            throw new NoMoreThemeCardsException();
        }

        gameController.markCardAsUsed(card); // Mark the card as used for this game

        Platform.runLater(() -> showDifficultyChoice(card));
    }

    // Update UI label with the name of the current player
    private void updatePlayerLabel() {
        currentPlayerLabel.setText(gameController.getCurrentPlayer().getName() + "'s turn !");
    }

    // Show a popup when a player wins the game
    private void showVictoryPopup(String winner) {
        Platform.runLater(() -> {
            Stage popupStage = new Stage();
            popupStage.initOwner(gamePane.getScene().getWindow());
            popupStage.initModality(Modality.NONE);
            popupStage.setResizable(false);
            popupStage.setTitle("Victory!");

            Label message = new Label(winner + " won the game!");
            message.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-padding: 20px;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-alignment: center;"
            );

            StackPane root = new StackPane(message);
            root.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #a0522d, #d2691e, #f4a460);" +
                "-fx-background-radius: 10px;"
            );

            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.show();

            Stage mainStage = (Stage) gamePane.getScene().getWindow();
            mainStage.setOnCloseRequest(e -> popupStage.close());

            mainStage.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != scene) {
                    popupStage.close();
                }
            });
        });
    }


    // Display the question difficulty selection UI
    private void showDifficultyChoice(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionDifficultyUI.fxml"));
            Parent root = loader.load();

            QuestionController controller = loader.getController();
            controller.setCard(card);
            controller.setBoardGameController(this);
            controller.setPlayerName(gameController.getCurrentPlayer().getName());
            controller.setGameController(gameController);
            controller.setExternalTimerLabel(timerLabel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gamePane.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for the game pane (used for positioning and UI overlay)
    public AnchorPane getGamePane() {
        return gamePane;
    }

    // Set game difficulty strategy
    public void setDifficultyLevel(int level) {
        switch (level) {
            case 1 -> gameController.setDifficultyStrategy(new EasyDifficulty());
            case 2 -> gameController.setDifficultyStrategy(new MediumDifficulty());
            case 3 -> gameController.setDifficultyStrategy(new HardDifficulty());
            default -> gameController.setDifficultyStrategy(new EasyDifficulty());
        }
    }

    // Return to the main menu screen
    public void switchToScene(ActionEvent event) throws IOException {
        stopBackgroundMusic();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameMenuUI.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    // Allow keyboard shortcuts (e.g., move forward on Z key)
    public void setupKeyHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z -> movePawn(1);
            }
        });
    }

    // Get the type of the case the current player is on
    public String getCurrentCaseType() {
        Player currentPlayer = gameController.getCurrentPlayer();
        return boardGame.getCase(currentPlayer.getPawn().getPosition()).getType();
    }

    // Stop music when exiting game
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }
}
