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
import javafx.scene.control.Slider;
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
        Parent root = FXMLLoader.load(getClass().getResource("gameMenuUI.fxml")); // corrigé avec le bon chemin
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    private void switchToScene2(ActionEvent event) throws InvalidCardException, NoMoreThemeCardsException {
        try {
            // 🔒 Vérifie que les sliders ne sont pas null
            if (playerSlider == null || difficultySlider == null) {
                throw new IllegalStateException("Les sliders ne sont pas initialisés. Vérifie les fx:id dans le FXML.");
            }

            int nbPlayers = (int) playerSlider.getValue();
            int difficulty = (int) difficultySlider.getValue(); // Tu peux l'utiliser plus tard

            // Création des noms des joueurs
            List<String> names = new ArrayList<>();
            for (int i = 1; i <= nbPlayers; i++) {
                names.add("Player " + i);
            }

            PlayerController playerController = new PlayerController();
            List<Player> players = playerController.createPlayersWithPawns(names);

            List<Card> allCards = CardDataLoader.loadCardsFromJson();

            GameController game = new GameController(allCards, players);

            // Chargement de la vue principale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameUI.fxml"));
            Parent root = loader.load();
            
            

            BoardGameController controller = loader.getController();
            controller.setGameController(game); // Passe le contrôleur
            
            controller.setDifficultyLevel(difficulty);

            // Affichage de la nouvelle scène
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            System.err.println("⚠ Vérifie bien que les fx:id dans ton FXML matchent les noms dans ta classe Java.");
        }
    }
}
