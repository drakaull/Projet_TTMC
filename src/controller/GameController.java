package controller;

import java.util.List;
import java.util.Set;

import models.BoardGame;
import models.Card;
import models.Player;
import models.Question;

import java.util.HashSet;

public class GameController {
    private CardController cardController;
    private Set<Card> usedCards;
    private List<Player> players;
    private int currentPlayerIndex = 0;
    private BoardGame boardGame;
    private int difficultyLevel = 1;

    public void setDifficultyLevel(int level) {
        this.difficultyLevel = level;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }


    public GameController(List<Card> allCards, List<Player> players) {
        this.cardController = new CardController(allCards);
        this.usedCards = new HashSet<>();
        this.players = players;
        this.boardGame = new BoardGame(); // ⬅️ ajoute cette ligne
    }


    // The player lands on a square with a main theme
    public Card drawCardForTheme(String theme) {
        Card card = cardController.pickCardForTheme(theme);
        if (card != null) {
            usedCards.add(card);
        }
        return card;
    }

    // The player chooses a difficulty level and gets the corresponding question
    public Question getQuestionForLevel(Card card, int level) {
        return card.getQuestionByLevel(level);
    }

    // Check if the card has already been used
    public boolean isCardUsed(Card card) {
        return usedCards.contains(card);
    }

    // Can be used to reset the used cards (new game)
    public void resetUsedCards() {
        usedCards.clear();
    }
    
    // Get current player
    public Player getCurrentPlayer() {
    	return players.get(currentPlayerIndex);
    }
    
    // Go to next player
    public void nextPlayer() {
    	currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    
    public void resetPlayers() {
    	currentPlayerIndex = 0;
    	for (Player player : players) {
    		player.setScore(0);
    		if (player.getPawn() != null) {
    			player.getPawn().setPosition(0);
    		}
    	}
    }
    
    public List<Player> getPlayers() {
    	return players;
    }
    public BoardGame getBoardGame() {
        return boardGame;
    }

} 
