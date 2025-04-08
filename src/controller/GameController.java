package controller;

import java.util.List;
import java.util.Set;

import models.Card;
import models.Question;

import java.util.HashSet;

public class GameController {
    private CardController cardController;
    private Set<Card> usedCards;

    public GameController(List<Card> allCards) {
        this.cardController = new CardController(allCards);
        this.usedCards = new HashSet<>();
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
} 
