package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteBlockTest {

    NoteBlock testBlock;

    @BeforeEach
    void runBefore() {

    }

    @Test
    void testConstructor() {
        testBlock = new NoteBlock(3, 1, 0);

        assertEquals(3, testBlock.getColumn());
        assertEquals(1, testBlock.getStart());
        assertEquals(0, testBlock.getHoldLength());
        assertFalse(testBlock.isPlayed());
    }

    @Test
    void testSetHoldLength() {
        testBlock = new NoteBlock(2, 1, 0);
        assertEquals(0, testBlock.getHoldLength());

        testBlock.setHoldLength(3);
        assertEquals(3, testBlock.getHoldLength());

        testBlock.setHoldLength(3);
        assertEquals(3, testBlock.getHoldLength());

        testBlock.setHoldLength(7);
        assertEquals(7, testBlock.getHoldLength());

        testBlock.setHoldLength(0);
        assertEquals(0, testBlock.getHoldLength());
    }

    @Test
    void testSetPlayed() {
        testBlock = new NoteBlock(2, 1, 0);
        assertFalse(testBlock.isPlayed());

        testBlock.setPlayed(false);
        assertFalse(testBlock.isPlayed());

        testBlock.setPlayed(true);
        assertTrue(testBlock.isPlayed());

    }

    @Test
    void testSetColumn() {
        testBlock = new NoteBlock(2, 1, 0);
        assertEquals(2, testBlock.getColumn());

        testBlock.setColumn(3);
        assertEquals(3, testBlock.getColumn());
    }

    @Test
    void testSetStart() {
        testBlock = new NoteBlock(2, 1, 0);
        assertEquals(1, testBlock.getStart());

        testBlock.setStart(3);
        assertEquals(3, testBlock.getStart());

    }
}
