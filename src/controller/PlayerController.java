package controller;

import java.util.ArrayList;
import java.util.List;

import models.Pawn;
import models.Player;

public class PlayerController {
	public List<Player> createPlayersWithPawns(List<String> playerNames) {
		String[] pawnColors = { "Red", "Blue", "Green", "Yellow" };
		List<Player> players = new ArrayList<>();

		for (int i = 0; i < playerNames.size(); i++) {
			String playerName = playerNames.get(i);
			String color = pawnColors[i % pawnColors.length];

			Player player = new Player(playerName);
			Pawn pawn = new Pawn(color, 0, player);

			player.setPawn(pawn); // only if your Player class has this method

			players.add(player);
		}

		return players;
	}
}

