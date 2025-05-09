package utils;

import exceptions.InvalidAnswerException;

public class AnswerValidator {

    /**
     * Validates if the user's input matches the correct answer,
     * allowing for small typos depending on the answer's length.
     */
    public static boolean isCorrect(String userInput, String correctAnswer) throws InvalidAnswerException {
        if (correctAnswer == null || userInput == null) {
            throw new InvalidAnswerException();
        }

        if (userInput.trim().isEmpty()) {
            return false;
        }

        // Normalize both strings: lowercase, remove punctuation, extra spaces
        String input = normalize(userInput.trim().toLowerCase());
        String answer = normalize(correctAnswer.trim().toLowerCase());

        int allowedDistance;
        int answerLength = answer.length();

        // Strict check for very short answers
        if (answerLength <= 4) {
            return input.equals(answer);
        } else if (answerLength <= 8) {
            allowedDistance = 1;
        } else if (answerLength <= 15) {
            allowedDistance = 2;
        } else {
            allowedDistance = 3;
        }

        // Accept if distance is within threshold, or input is contained in answer or vice versa
        return levenshteinDistance(input, answer) <= allowedDistance
                || answer.contains(input)
                || input.contains(answer);
    }

    /**
     * Removes all non-alphanumeric characters (except spaces),
     * collapses multiple spaces, and trims the result.
     */
    private static String normalize(String s) {
        return s.replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ").trim();
    }

    /**
     * Computes the Levenshtein distance between two strings,
     * which is the number of edits needed to turn one into the other.
     */
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
