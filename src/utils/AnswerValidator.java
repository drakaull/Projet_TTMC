package utils;

import exceptions.InvalidAnswerException;

public class AnswerValidator {

	public static boolean isCorrect(String userInput, String correctAnswer) throws InvalidAnswerException {
        if (correctAnswer == null) {
            throw new InvalidAnswerException();
        }
        if (userInput == null) {
            throw new InvalidAnswerException();
        }
        if (userInput.trim().isEmpty()) {
            return false;
        }

        String input = normalize(userInput.trim().toLowerCase());
        String answer = normalize(correctAnswer.trim().toLowerCase());

        // Compare similarity: allow small typos or near matches
        return levenshteinDistance(input, answer) <= 1
                || answer.contains(input)
                || input.contains(answer);
    }

    private static String normalize(String s) {
        return s.replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ").trim();
    }

    private static int levenshteinDistance(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                                  a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
