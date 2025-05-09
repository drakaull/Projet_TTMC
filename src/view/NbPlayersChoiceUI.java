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
            // check that the sliders are not null 
            if (playerSlider == null || difficultySlider == null) {
                throw new IllegalStateException("⚠ The sliders are not initialized. Check the fx:id in the FXML.");
            }

            int nbPlayers = (int) playerSlider.getValue();
            int difficulty = (int) difficultySlider.getValue();

            // Creating player names
            List<String> names = new ArrayList<>();
            for (int i = 1; i <= nbPlayers; i++) {
                TextInputDialog dialog = new TextInputDialog("Player "+i);
                dialog.setTitle("Enter Player Name");
                dialog.setHeaderText(null);
                dialog.setContentText("Enter the name for player "+i);
                
                
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                dialog.initOwner(currentStage);
                
                // visual of dialog
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.setStyle("-fx-background-color :linear-gradient(from 0% 0% to 100% 100%, #a0522d, #d2691e, #f4a460);-fx-font-size: 14px;");
                
                dialogPane.lookupButton(ButtonType.OK).setStyle(
                		"-fx-background-color: #4CAF50; -fx-text-fill: white;-fx-font-weight:bold");
                
                dialogPane.lookupButton(ButtonType.CANCEL).setStyle(
                		"-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold");
                
                String playerName = dialog.showAndWait().orElse("Player "+i);
                names.add(playerName);
            }

            PlayerController playerController = new PlayerController();
            List<Player> players = playerController.createPlayersWithPawns(names);

            List<Card> allCards = CardDataLoader.loadCardsFromJson();

            GameController game = new GameController(allCards, players);

            // loading the main view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameUI.fxml"));
            Parent root = loader.load();
            
            

            BoardGameController controller = loader.getController();
            controller.setGameController(game); // Pass the controller
            
            controller.setDifficultyLevel(difficulty);

            // Viewing the new scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            System.err.println("⚠ Make sure that the fx:id in your FXML matches the names in your Java class.");
        }
    }
}
