package controller;

import java.util.ArrayList;
import java.util.List;

import models.Pawn;
import models.Player;

public class PlayerController {

    // Creates a list of Player objects each associated with a uniquely colored Pawn
    public List<Player> createPlayersWithPawns(List<String> playerNames) {
        String[] pawnColors = { "Red", "Blue", "Green", "Yellow" }; // Predefined pawn colors
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            String playerName = playerNames.get(i); // Get player name
            String color = pawnColors[i % pawnColors.length]; // Assign color in cyclic manner

            Player player = new Player(playerName); // Create player instance
            Pawn pawn = new Pawn(color, 0, player); // Create pawn at starting position 0

            player.setPawn(pawn); // Associate the pawn with the player

            players.add(player); // Add player to the list
        }

        return players;
    }
}
