package utils;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Card;

public class CardDataLoader {
	public static List<Card> loadCardsFromJson(String file) {
		try (FileReader reader = new FileReader(file)) {
			Gson gson = new Gson();
			Type cardListType = new TypeToken<List<Card>>() {}.getType(); 
			return gson.fromJson(reader, cardListType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
