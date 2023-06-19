package org.cis1200.Wordle;

import org.cis1200.wordle.Wordle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    @Test
    public void testAddDelLetter() {
        Wordle w = new Wordle();
        w.fixAnswer("apple");
        w.addLetter('a');
        w.delLetter();

        int[][] expectedColor = new int[6][5];
        char[][] expectedLetter = new char[6][5];

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("apple", w.getAnswer());
        assertEquals(0, w.getRound());
        assertEquals(0, w.getInputIndex());

    }

    @Test
    public void testAddGuessWithDuplicates() {
        Wordle w = new Wordle();
        w.fixAnswer("apple");
        w.addLetter('a');
        w.delLetter();
        w.addLetter('p');
        w.addLetter('a');
        w.addLetter('p');
        w.addLetter('e');
        w.addLetter('r');

        int[][] expectedColor = new int[6][5];
        expectedColor[0] = new int[] { 2, 2, 3, 2, 1 };
        char[][] expectedLetter = new char[6][5];
        expectedLetter[0] = new char[] { 'p', 'a', 'p', 'e', 'r' };

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("apple", w.getAnswer());
        assertEquals(1, w.getRound());
        assertEquals(0, w.getInputIndex());
        assertEquals("paper", w.getGuessed().peekLast());

    }

    @Test
    public void testAddGuessWithDuplicatesInGuessNotAnswer() {
        Wordle w = new Wordle();
        w.fixAnswer("stuck");
        w.addLetter('t');
        w.addLetter('u');
        w.addLetter('t');
        w.addLetter('o');
        w.addLetter('r');

        int[][] expectedColor = new int[6][5];
        expectedColor[0] = new int[] { 2, 2, 1, 1, 1 };
        char[][] expectedLetter = new char[6][5];
        expectedLetter[0] = new char[] { 't', 'u', 't', 'o', 'r' };

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("stuck", w.getAnswer());
        assertEquals(1, w.getRound());
        assertEquals(0, w.getInputIndex());
        assertEquals("tutor", w.getGuessed().peekLast());

    }

    @Test
    public void testInvalidWordNotAdded() {
        Wordle w = new Wordle();
        w.fixAnswer("stuck");
        w.addLetter('t');
        w.addLetter('t');
        w.addLetter('t');
        w.addLetter('t');
        w.addLetter('t');

        int[][] expectedColor = new int[6][5];
        expectedColor[0] = new int[] { 1, 1, 1, 1, 1 };
        char[][] expectedLetter = new char[6][5];
        expectedLetter[0] = new char[] { 't', 't', 't', 't', 't' };

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("stuck", w.getAnswer());
        assertEquals(0, w.getRound());
        assertEquals(5, w.getInputIndex());
        assertNull(w.getGuessed().peekLast());

    }

    @Test
    public void testCorrectAnswerAfterMultiple() {
        Wordle w = new Wordle();
        w.fixAnswer("apple");
        w.addLetter('p');
        w.addLetter('l');
        w.addLetter('u');
        w.addLetter('c');
        w.addLetter('k');

        int[][] expectedColor = new int[6][5];
        expectedColor[0] = new int[] { 2, 2, 1, 1, 1 };
        char[][] expectedLetter = new char[6][5];
        expectedLetter[0] = new char[] { 'p', 'l', 'u', 'c', 'k' };

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("apple", w.getAnswer());
        assertEquals(1, w.getRound());
        assertEquals(0, w.getInputIndex());

        w.addLetter('l');
        w.addLetter('a');
        w.addLetter('p');
        w.addLetter('e');
        w.addLetter('l');

        expectedColor[1] = new int[] { 2, 2, 3, 2, 1 };
        expectedLetter[1] = new char[] { 'l', 'a', 'p', 'e', 'l' };

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("apple", w.getAnswer());
        assertEquals(2, w.getRound());
        assertEquals(0, w.getInputIndex());

        w.addLetter('a');
        w.addLetter('p');
        w.addLetter('p');
        w.addLetter('l');
        w.addLetter('e');

        expectedColor[2] = new int[] { 3, 3, 3, 3, 3 };
        expectedLetter[2] = new char[] { 'a', 'p', 'p', 'l', 'e' };

        assertArrayEquals(expectedColor, w.getBoardColors());
        assertArrayEquals(expectedLetter, w.getBoardLetters());
        assertEquals("apple", w.getAnswer());
        assertEquals(3, w.getRound());
        assertTrue(w.isGameOver());
        assertEquals(0, w.getInputIndex());

    }

}
