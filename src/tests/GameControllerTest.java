package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.GameController;
import exceptions.InvalidCardException;
import exceptions.InvalidGameException;
import exceptions.InvalidQuestionException;
import models.Card;
import models.Pawn;
import models.Player;
import models.Question;
import strategyDifficulty.DifficultyStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController;
    private List<Card> cards;
    private List<Player> players;

    @BeforeEach
    public void setUp() throws InvalidQuestionException, InvalidCardException {
        cards = new ArrayList<>();
        players = new ArrayList<>();

        List<Question> questions = List.of(
                new Question(1, "Q1?", "A1", null, "Subject"),
                new Question(2, "Q2?", "A2", null, "Subject"),
                new Question(3, "Q3?", "A3", null, "Subject"),
                new Question(4, "Q4?", "A4", null, "Subject")
        );

        cards.add(new Card("Theme1", "Subject1", questions));
        cards.add(new Card("Theme2", "Subject2", questions));

        players.add(new Player("Alice"));
        players.add(new Player("Bob"));

        gameController = new GameController(cards, players);
    }

    @Test
    public void testSetAndGetDifficultyStrategy() {
        DifficultyStrategy dummyStrategy = () -> 30;
        gameController.setDifficultyStrategy(dummyStrategy);

        assertEquals(30, gameController.getTimeLimit());
    }

    @Test
    public void testGetTimeLimitWithoutStrategyThrows() {
        assertThrows(IllegalStateException.class, () -> gameController.getTimeLimit());
    }

    @Test
    public void testDrawCardForThemeReturnsCard() {
        Card card = gameController.drawCardForTheme("Theme1");
        assertNotNull(card);
        assertEquals("Theme1", card.getTheme());
    }

    @Test
    public void testIsCardUsed() {
        Card card = gameController.drawCardForTheme("Theme1");
        assertTrue(gameController.isCardUsed(card));
    }

    @Test
    public void testResetUsedCardsClearsSet() {
        Card card = gameController.drawCardForTheme("Theme1");
        assertTrue(gameController.isCardUsed(card));

        gameController.resetUsedCards();
        assertFalse(gameController.isCardUsed(card));
    }

    @Test
    public void testGetCurrentPlayer() {
        Player current = gameController.getCurrentPlayer();
        assertEquals("Alice", current.getName());
    }

    @Test
    public void testNextPlayerCycles() {
        Player first = gameController.getCurrentPlayer();
        gameController.nextPlayer();
        Player second = gameController.getCurrentPlayer();

        assertNotEquals(first, second);
    }

    @Test
    public void testResetPlayersResetsScoresAndPositions() {
        Player player = gameController.getCurrentPlayer();
        player.setScore(10);
        player.setPawn(new Pawn("blue", 5, player));

        gameController.resetPlayers();

        assertEquals(0, player.getScore());
        assertEquals(0, player.getPawn().getPosition());
    }

    @Test
    public void testStartNewGameResetsEverything() throws InvalidGameException {
        gameController.startNewGame();

        for (Player p : gameController.getPlayers()) {
            assertEquals(0, p.getScore());
            if (p.getPawn() != null) {
                assertEquals(0, p.getPawn().getPosition());
            }
        }
    }

    @Test
    public void testGetBoardGameNotNull() {
        assertNotNull(gameController.getBoardGame());
    }
}
