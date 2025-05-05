package controller;

import java.util.List;
import java.util.Set;

import models.BoardGame;
import models.Card;
import models.Player;
import models.Question;
import strategyDifficulty.DifficultyStrategy;

import java.util.HashSet;

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

    public Card drawCardForTheme(String theme) {
        if (usedCards.size() == cardController.getAllCards().size()) {
            resetUsedCards();
            shuffleCards();
        }

        Card card = cardController.pickCardForTheme(theme);
        if (card == null) {
            System.out.println("Plus de cartes pour ce thème : " + theme);
            return null;
        }

        usedCards.add(card);
        return card;
    }

    public Question getQuestionForLevel(Card card, int level) {
        return card.getQuestionByLevel(level);
    }

    public boolean isCardUsed(Card card) {
        return usedCards.contains(card);
    }

    public void resetUsedCards() {
        usedCards.clear();
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
