package controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import models.Card;

public class CardController {
    private List<Card> allCards;

    public CardController(List<Card> cards) {
        this.allCards = cards;
    }

    public Card pickCardForTheme(String theme, Set<Card> usedCards) {
        List<Card> availableCards = allCards.stream()
            .filter(c -> c.getTheme().equals(theme) && !usedCards.contains(c))
            .collect(Collectors.toList());

        if (availableCards.isEmpty()) {
            return null;
        }

        Collections.shuffle(availableCards);
        return availableCards.get(0);
    }

    public void shuffleCards() {
        Collections.shuffle(this.allCards);
    }

    public List<Card> getAllCards() {
        return allCards;
    }
}
