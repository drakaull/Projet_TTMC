package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import models.Card;

public class CardController {
	private List<Card> allCards;
	private Set<Card> usedCards;

	public CardController(List<Card> cards) {
		this.allCards = cards;
		this.usedCards = new HashSet<>();
	}

	public Card pickCardForTheme(String theme) {
		List<Card> availables = allCards.stream()
				.filter(card -> card.getTheme().equalsIgnoreCase(theme) && !usedCards.contains(card))
				.collect(Collectors.toList());
		
		if (availables.isEmpty()) return null;
		
		Card card = availables.get(new Random().nextInt(availables.size()));
		usedCards.add(card);
		return card;
	}
	
	public void resetUsedCards() {
        usedCards.clear();
    }

    public boolean isCardUsed(Card card) {
        return usedCards.contains(card);
    }
}
