package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Player> players; // List of players participating in the game
	private List<Card> cards; // list of available cards in the game
	private List<Card> playedCards; // list of cards that have already been played
	private Player activePlayer; // The player whose turn it is currently

	/*
	 * Constructor initializes the game with the active player.
	 * Also initializes lists for players, cards, and played cards
	 */
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
