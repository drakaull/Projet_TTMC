package utils;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Card;

public class CardDataLoader {

    private static List<Card> cards; // cached cards
    private static final Random random = new Random();

    private static final String JSON_PATH = "src/resource/json/databaseQuestion.json";

    // Lazy loading of cards
    public static List<Card> loadCardsFromJson() {
        if (cards == null) {
            try (FileReader reader = new FileReader(JSON_PATH)) {
                Gson gson = new Gson();
                Type cardListType = new TypeToken<List<Card>>() {}.getType();
                cards = gson.fromJson(reader, cardListType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cards;
    }

    // Pick a random card from loaded list
    public static Card getRandomCard() {
        List<Card> availableCards = loadCardsFromJson();
        if (availableCards == null || availableCards.isEmpty()) return null;

        int index = random.nextInt(availableCards.size());
        return availableCards.get(index);
    }
}
