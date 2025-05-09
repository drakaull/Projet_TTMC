package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import exceptions.InvalidCardException;
import exceptions.NoMoreThemeCardsException;
import models.BoardGame;
import models.Card;
import models.Player;
import models.Question;
import strategyDifficulty.DifficultyStrategy;

public class GameController {
    private CardController cardController; // Manages all cards and selection logic
    private Set<Card> usedCards; // Tracks cards that have already been drawn during the game
    private List<Player> players; // List of players in the current game
    private int currentPlayerIndex = 0; // Index of the player whose turn it is
    private BoardGame boardGame; // The game board instance
    private DifficultyStrategy difficultyStrategy; // Difficulty setting to control gameplay speed

    public GameController(List<Card> allCards, List<Player> players) {
        this.cardController = new CardController(allCards);
        this.usedCards = new HashSet<>(); 
        this.players = players;
        this.boardGame = new BoardGame();
        shuffleCards();
    }

    // Assigns the difficulty strategy (used for time limits etc.)
    public void setDifficultyStrategy(DifficultyStrategy strategy) {
        this.difficultyStrategy = strategy;
    }

    // Returns the time limit based on the current difficulty setting
    public int getTimeLimit() {
        if (difficultyStrategy == null) {
            throw new IllegalStateException("The difficulty has not been defined !");
        }
        return difficultyStrategy.getTimeLimitInSeconds();
    }

    // Shuffles the card deck
    private void shuffleCards() {
        cardController.shuffleCards();
    }

    // Draws a card for a specific theme; ensures the card hasn't already been used
    public Card drawCardForTheme(String theme) throws NoMoreThemeCardsException {
        if (usedCards.size() == cardController.getAllCards().size()) {
            throw new NoMoreThemeCardsException(); // All cards are exhausted
        }

        Card card = cardController.pickCardForTheme(theme, usedCards);
        if (card == null) {
            throw new NoMoreThemeCardsException(); // No available card for the theme
        }

        // Mark the card as used immediately
        usedCards.add(card);
        return card;
    }

    // Retrieves a question from a card based on the chosen difficulty level
    public Question getQuestionForLevel(Card card, int level) throws InvalidCardException {
        return card.getQuestionByLevel(level);
    }

    // Resets the used cards set
    public void resetUsedCards() {
        usedCards.clear();
    }

    // Explicitly marks a card as used
    public void markCardAsUsed(Card card) {
        usedCards.add(card);
    }

    // Checks whether a given card has already been used
    public boolean isCardUsed(Card card) {
        return usedCards.contains(card);
    }

    // Returns the current player
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    // Advances to the next player
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    // Resets all players and their positions for a new game
    public void resetPlayers() {
        currentPlayerIndex = 0;
        for (Player player : players) {
            player.setScore(0);
            if (player.getPawn() != null) {
                player.getPawn().setPosition(0);
            }
        }
    }

    // Prepares the game for a new round: reset players and cards, and reshuffle
    public void startNewGame() {
        resetPlayers();
        resetUsedCards();
        shuffleCards();
    }

    // Returns the list of all players
    public List<Player> getPlayers() {
        return players;
    }

    // Returns the board game instance
    public BoardGame getBoardGame() {
        return boardGame;
    }
}
