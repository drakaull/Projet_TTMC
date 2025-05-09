package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.BoardGameController;
import controller.GameController;
import controller.PlayerController;
import exceptions.InvalidCardException;
import exceptions.NoMoreThemeCardsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import models.Card;
import models.Player;
import utils.CardDataLoader;

public class NbPlayersChoiceUI {

    @FXML
    private Slider playerSlider;

    @FXML
    private Slider difficultySlider;

    private Stage stage;
    private Scene scene;

    public void switchToScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gameMenuUI.fxml")); 
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    private void switchToScene2(ActionEvent event) throws InvalidCardException, NoMoreThemeCardsException {
        try {
            if (playerSlider == null || difficultySlider == null) {
                throw new IllegalStateException("⚠ The sliders are not initialized. Check the fx:id in the FXML.");
            }

            int nbPlayers = (int) playerSlider.getValue();
            int difficulty = (int) difficultySlider.getValue();

            List<String> names = new ArrayList<>();
            for (int i = 1; i <= nbPlayers; i++) {
                TextInputDialog dialog = new TextInputDialog("Player " + i);
                dialog.setTitle("Enter Player Name");
                dialog.setHeaderText(null);
                dialog.setContentText("Enter the name for player " + i);

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                dialog.initOwner(currentStage);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.setStyle("-fx-background-color :linear-gradient(from 0% 0% to 100% 100%, #a0522d, #d2691e, #f4a460);-fx-font-size: 14px;");
                dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;-fx-font-weight:bold");
                dialogPane.lookupButton(ButtonType.CANCEL).setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold");

                String playerName = dialog.showAndWait().orElse("Player " + i);
                names.add(playerName);
            }

            PlayerController playerController = new PlayerController();
            List<Player> players = playerController.createPlayersWithPawns(names);
            List<Card> allCards = CardDataLoader.loadCardsFromJson();
            GameController game = new GameController(allCards, players);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameUI.fxml"));
            Parent root = loader.load();
            BoardGameController controller = loader.getController();

            // Créer la scène une seule fois
            Scene scene = new Scene(root);

            // Configurer la scène avec les données nécessaires
            controller.setGameController(game);
            controller.setDifficultyLevel(difficulty);
            controller.setupKeyHandler(scene); // pour détecter la touche Z

            // Afficher la scène
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();

            root.requestFocus(); // pour capter les touches clavier

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
