package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Player> players;
	private List<Card> cards;
	private List<Card> playedCards;
	private Player activePlayer;

	public Game(Player activePlayer) {
		setActivePlayer(activePlayer);
		players = new ArrayList<Player>();
		cards = new ArrayList<Card>();
		playedCards = new ArrayList<Card>();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getPlayedCards() {
		return playedCards;
	}

	public void setPlayedCards(List<Card> playedCards) {
		this.playedCards = playedCards;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	

}
