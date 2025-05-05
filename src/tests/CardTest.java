package tests;

import org.junit.jupiter.api.Test;

import exceptions.InvalidCardException;
import exceptions.InvalidQuestionException;
import models.Card;
import models.Question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private List<Question> createSampleQuestions() throws InvalidQuestionException {
        return Arrays.asList(
                new Question(1, "Q1?", "A1", null, "Subject"),
                new Question(2, "Q2?", "A2", null, "Subject"),
                new Question(3, "Q3?", "A3", null, "Subject"),
                new Question(4, "Q4?", "A4", null, "Subject")
        );
    }

    @Test
    void testValidCardCreation() throws InvalidCardException, InvalidQuestionException {
        Card card = new Card("General", "Geography", createSampleQuestions());

        assertEquals("General", card.getTheme());
        assertEquals("Geography", card.getSubject());
        assertEquals(4, card.getQuestions().size());
    }

    @Test
    void testNullThemeThrowsException() {
        assertThrows(InvalidCardException.class, () ->
                new Card(null, "Subject", createSampleQuestions()));
    }

    @Test
    void testEmptyThemeThrowsException() {
        assertThrows(InvalidCardException.class, () ->
                new Card("   ", "Subject", createSampleQuestions()));
    }

    @Test
    void testNullSubjectThrowsException() {
        assertThrows(InvalidCardException.class, () ->
                new Card("Theme", null, createSampleQuestions()));
    }

    @Test
    void testEmptySubjectThrowsException() {
        assertThrows(InvalidCardException.class, () ->
                new Card("Theme", "   ", createSampleQuestions()));
    }

    @Test
    void testNullQuestionsThrowsException() {
        assertThrows(InvalidCardException.class, () ->
                new Card("Theme", "Subject", null));
    }

    @Test
    void testEmptyQuestionsThrowsException() {
        assertThrows(InvalidCardException.class, () ->
                new Card("Theme", "Subject", Collections.emptyList()));
    }

    @Test
    void testLessThanFourQuestionsThrowsException() throws InvalidQuestionException {
        List<Question> smallList = Arrays.asList(
                new Question(1, "Q1?", "A1", null, "Subject"),
                new Question(2, "Q2?", "A2", null, "Subject"),
                new Question(3, "Q3?", "A3", null, "Subject")
        );

        assertThrows(InvalidCardException.class, () ->
                new Card("Theme", "Subject", smallList));
    }

    @Test
    void testGetQuestionByLevelSuccess() throws InvalidCardException, InvalidQuestionException {
        Card card = new Card("Theme", "Subject", createSampleQuestions());

        Question q = card.getQuestionByLevel(2);
        assertNotNull(q);
        assertEquals(2, q.getDifficulty());
        assertEquals("Q2?", q.getQuestion());
    }

    @Test
    void testGetQuestionByLevelNotFoundThrowsException() throws InvalidCardException, InvalidQuestionException {
        Card card = new Card("Theme", "Subject", createSampleQuestions());

        assertThrows(InvalidCardException.class, () ->
                card.getQuestionByLevel(5));
    }
}
