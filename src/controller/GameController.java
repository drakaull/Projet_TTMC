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
    private CardController cardController;
    private Set<Card> usedCards;
    private List<Player> players;
    private int currentPlayerIndex = 0;
    private BoardGame boardGame;
    private DifficultyStrategy difficultyStrategy;

    public GameController(List<Card> allCards, List<Player> players) {
        this.cardController = new CardController(allCards);
        this.usedCards = new HashSet<>();
        this.players = players;
        this.boardGame = new BoardGame();
        shuffleCards();
    }

    public void setDifficultyStrategy(DifficultyStrategy strategy) {
        this.difficultyStrategy = strategy;
    }

    public int getTimeLimit() {
        if (difficultyStrategy == null) {
            throw new IllegalStateException("La difficulté n'a pas été définie !");
        }
        return difficultyStrategy.getTimeLimitInSeconds();
    }

    private void shuffleCards() {
        cardController.shuffleCards();
    }

    public Card drawCardForTheme(String theme) throws NoMoreThemeCardsException {
        if (usedCards.size() == cardController.getAllCards().size()) {
            throw new NoMoreThemeCardsException();
        }

        Card card = cardController.pickCardForTheme(theme, usedCards);
        if (card == null) {
            throw new NoMoreThemeCardsException();
        }

        // Marquer immédiatement la carte comme utilisée
        usedCards.add(card);
        return card;
    }

    public Question getQuestionForLevel(Card card, int level) throws InvalidCardException {
        return card.getQuestionByLevel(level);
    }

    public void resetUsedCards() {
        usedCards.clear();
    }

    public void markCardAsUsed(Card card) {
        usedCards.add(card);
    }

    public boolean isCardUsed(Card card) {
        return usedCards.contains(card);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

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

    public void startNewGame() {
        resetPlayers();
        resetUsedCards();
        shuffleCards();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }
}
