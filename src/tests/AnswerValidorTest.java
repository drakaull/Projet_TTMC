package tests;

import org.junit.jupiter.api.Test;

import exceptions.InvalidAnswerException;
import utils.AnswerValidator;

import static org.junit.jupiter.api.Assertions.*;

class AnswerValidatorTest {
	
	@Test
    void testNullInputThrowsException() {
        String expected = "something";
        assertThrows(InvalidAnswerException.class, () -> {
            AnswerValidator.isCorrect(null, expected);
        }, "Expected null user input to throw InvalidAnswerException.");
    }

    @Test
    void testNullExpectedThrowsException() {
        String userInput = "something";
        assertThrows(InvalidAnswerException.class, () -> {
            AnswerValidator.isCorrect(userInput, null);
        }, "Expected null expected answer to throw InvalidAnswerException.");
    }

	@Test
	void testExactMatch() throws InvalidAnswerException {
		String expected = "42";
		String userInput = "42";
		assertTrue(AnswerValidator.isCorrect(userInput, expected), "Expected exact match to return true.");
	}

	@Test
	void testTrimmedInput() throws InvalidAnswerException {
		String expected = "answer";
		String userInput = "  answer  ";
		assertTrue(AnswerValidator.isCorrect(userInput, expected), "Expected trimmed input to return true.");
	}

	@Test
	void testCaseInsensitiveMatch() throws InvalidAnswerException {
		String expected = "Blue";
		String userInput = "blue";
		assertTrue(AnswerValidator.isCorrect(userInput, expected), "Expected case-insensitive match to return true.");
	}

	@Test
	void testIncorrectAnswer() throws InvalidAnswerException {
		String expected = "correct";
		String userInput = "wrong";
		assertFalse(AnswerValidator.isCorrect(userInput, expected), "Expected incorrect answer to return false.");
	}

	@Test
	void testEmptyInput() throws InvalidAnswerException {
		String expected = "something";
		String userInput = "";
		assertFalse(AnswerValidator.isCorrect(userInput, expected), "Expected empty input to return false.");
	}

	@Test
	void testNullInput() throws InvalidAnswerException {
		String expected = "something";
		String userInput = null;
		assertFalse(AnswerValidator.isCorrect(userInput, expected), "Expected null input to return false.");
	}

	@Test
	void testNullExpected() throws InvalidAnswerException {
		String expected = null;
		String userInput = "something";
		assertFalse(AnswerValidator.isCorrect(userInput, expected), "Expected null expected answer to return false.");
	}

	@Test
	void testBothNull() throws InvalidAnswerException {
		String expected = null;
		String userInput = null;
		assertFalse(AnswerValidator.isCorrect(userInput, expected), "Expected both null to return false.");
	}
}
