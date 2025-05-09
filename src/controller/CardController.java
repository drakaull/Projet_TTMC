package controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import models.Card;

public class CardController {
    private List<Card> allCards;

    // Constructor that initializes the controller with a list of all cards
    public CardController(List<Card> cards) {
        this.allCards = cards;
    }

    // Picks a random card of a given theme that hasn't already been used
    public Card pickCardForTheme(String theme, Set<Card> usedCards) {
        // Filter out cards that are not of the requested theme or have already been used
        List<Card> availableCards = allCards.stream()
            .filter(c -> c.getTheme().equals(theme) && !usedCards.contains(c))
            .collect(Collectors.toList());

        // If no available cards, return null
        if (availableCards.isEmpty()) {
            return null;
        }

        // Shuffle and return a random available card
        Collections.shuffle(availableCards);
        return availableCards.get(0);
    }

    // Shuffle the full list of cards (useful for randomizing game order)
    public void shuffleCards() {
        Collections.shuffle(this.allCards);
    }

    // Getter to retrieve the list of all cards
    public List<Card> getAllCards() {
        return allCards;
    }
}
