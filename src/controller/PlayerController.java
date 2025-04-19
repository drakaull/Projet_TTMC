package controller;

import java.util.ArrayList;
import java.util.List;

import models.Pawn;
import models.Player;

public class PlayerController {
	public List<Player> createPlayersWithPawns(List<String> playerNames) {
		String[] pawnColors = { "Red", "Blue", "Green", "Yellow" };
		List<Player> players = new ArrayList<Player>();

		for (int i = 0; i < playerNames.size(); i++) {
			String playerName = playerNames.get(i);
			String color = pawnColors[i % pawnColors.length];

			Pawn pawn = new Pawn(color, 0, null);
			Player player = new Player(playerName);
			pawn.setOwner(player);
		}

		return players;
	}
}
