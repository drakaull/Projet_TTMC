// Nouveau BoardGameController avec gestion multi-joueurs

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
import utils.CardDataLoader;

public class BoardGameController {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private Label currentPlayerLabel;


    private GameController gameController;
    private BoardGame boardGame;
    private Map<Player, Circle> playerCircles = new HashMap<>();
    private AudioClip backgroundMusic;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        initializeGame();
    }

    // question apparait avant platezu ?
    private void initializeGame() {
        boardGame = new BoardGame();
        List<Player> players = gameController.getPlayers();
        List<Color> colors = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        
        int i = 0;
        for (Player player : players) {
            Pawn pawn = new Pawn(player.getName(), 0, player);
            player.setPawn(pawn);

            Circle circle = new Circle(10);
            circle.setFill(colors.get(i % colors.size())); // Rotation si > 4 joueurs
            circle.setId(player.getName());
            gamePane.getChildren().add(circle);
            playerCircles.put(player, circle);

            Case start = boardGame.getCase(0);

            // Positionnement décalé dès le départ
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

        startBackgroundMusic();
        updatePlayerLabel();
        askNewCard();
    }

    
    

    private void startBackgroundMusic() {
        try {
            URL resource = getClass().getResource("/resource/background.mp3");
            if (resource == null) {
                System.out.println("Erreur : fichier mp3 non trouvé !");
                return;
            }

            backgroundMusic = new AudioClip(resource.toExternalForm());
            backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
            backgroundMusic.setVolume(0.8); // Volume entre 0 et 1
            backgroundMusic.play();
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la musique : " + e.getMessage());
        }
    }


    
    public void nextPlayer() {
        gameController.nextPlayer();
        updatePlayerLabel();
    }



    public void movePawn(int movement) {
        Player currentPlayer = gameController.getCurrentPlayer();
        PawnController pawnController = new PawnController(currentPlayer.getPawn());
        pawnController.moveForward(movement, boardGame);

        Case newCase = boardGame.getCase(currentPlayer.getPawn().getPosition());
        Circle circle = playerCircles.get(currentPlayer);

        // Récupère la position du joueur dans la liste
        int playerIndex = gameController.getPlayers().indexOf(currentPlayer);

        double offsetX = 0;
        double offsetY = 0;

        // Décalages spéciaux pour 4 joueurs
        switch (playerIndex) {
            case 0: // Joueur 1
                offsetX = -10;
                offsetY = -10;
                break;
            case 1: // Joueur 2
                offsetX = 10;
                offsetY = -10;
                break;
            case 2: // Joueur 3
                offsetX = -10;
                offsetY = 10;
                break;
            case 3: // Joueur 4
                offsetX = 10;
                offsetY = 10;
                break;
            default:
                // Si jamais il y a plus de 4 joueurs, on reste au centre
                offsetX = 0;
                offsetY = 0;
                break;
        }

        TranslateTransition transition = new TranslateTransition(Duration.millis(300), circle);
        transition.setToX(newCase.getX() + offsetX);
        transition.setToY(newCase.getY() + offsetY);

        transition.setOnFinished(e -> {
            if (currentPlayer.getPawn().getPosition() == boardGame.getTotalCases() - 1) {
                showVictoryPopup(currentPlayer.getName());
            } else {
                gameController.nextPlayer();
                updatePlayerLabel();
                askNewCard();
            }
        });

        transition.play();
    }


    public void askNewCard() {
        Card card = CardDataLoader.getRandomCard();
        Platform.runLater(() -> showDifficultyChoice(card));
    }

    private void updatePlayerLabel() {
    	currentPlayerLabel.setText("Au tour de : " + gameController.getCurrentPlayer().getName());
    }

    private void showVictoryPopup(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText(winner + " a gagné la partie !");
        alert.showAndWait();
    }

    private void showDifficultyChoice(Card card) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionDifficultyUI.fxml"));
            Parent root = loader.load();

            QuestionController controller = loader.getController();
            controller.setCard(card);
            controller.setBoardGameController(this);
            controller.setPlayerName(gameController.getCurrentPlayer().getName());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(gamePane.getScene().getWindow());
            stage.setResizable(false);
            //stage.setAlwaysOnTop(true);
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


} 
