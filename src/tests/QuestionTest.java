package tests;

import org.junit.jupiter.api.Test;

import exceptions.InvalidQuestionException;
import models.Question;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void testValidQuestionWithOptions() throws InvalidQuestionException {
        Question q = new Question(2, "What is the capital of France?", "Paris",
                Arrays.asList("Paris", "Berlin", "Madrid", "Rome"), "Geography");

        assertEquals(2, q.getDifficulty());
        assertEquals("What is the capital of France?", q.getQuestion());
        assertEquals("Paris", q.getAnswer());
        assertEquals("Geography", q.getSubject());
        assertTrue(q.isMultipleChoice());
        assertEquals(4, q.getOptions().size());
    }

    @Test
    void testValidOpenQuestion() throws InvalidQuestionException {
        Question q = new Question(1, "Explain photosynthesis.", "Process by which plants...", null, "Biology");

        assertEquals(1, q.getDifficulty());
        assertFalse(q.isMultipleChoice());
        assertNull(q.getOptions());
    }

    @Test
    void testNullQuestionThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(1, null, "Answer", null, "Subject"));
    }

    @Test
    void testEmptyQuestionThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(1, "   ", "Answer", null, "Subject"));
    }

    @Test
    void testNullAnswerThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(1, "Question?", null, null, "Subject"));
    }

    @Test
    void testEmptyAnswerThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(1, "Question?", "   ", null, "Subject"));
    }

    @Test
    void testNullSubjectThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(1, "Question?", "Answer", null, null));
    }

    @Test
    void testEmptySubjectThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(1, "Question?", "Answer", null, "   "));
    }

    @Test
    void testInvalidDifficultyLowThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(0, "Question?", "Answer", null, "Subject"));
    }

    @Test
    void testInvalidDifficultyHighThrowsException() {
        assertThrows(InvalidQuestionException.class, () ->
                new Question(5, "Question?", "Answer", null, "Subject"));
    }
}
