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

    // Cached list of cards to avoid reading the file multiple times
    private static List<Card> cards;

    // Used for picking random cards
    private static final Random random = new Random();

    // Path to the JSON file containing the questions database
    private static final String JSON_PATH = "src/resource/json/databaseQuestion.json";

    /**
     * Loads the cards from the JSON file if not already loaded.
     * Uses Gson to deserialize the JSON into a List<Card>.
     * 
     * @return List of all cards loaded from the JSON file
     */
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

    /**
     * Selects and returns a random card from the loaded card list.
     * 
     * @return A random Card object, or null if the list is empty or unavailable
     */
    public static Card getRandomCard() {
        List<Card> availableCards = loadCardsFromJson();
        if (availableCards == null || availableCards.isEmpty()) return null;

        int index = random.nextInt(availableCards.size());
        return availableCards.get(index);
    }
}
