package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SongTest {
    Song testSong;
    NoteBlock note1Single;
    NoteBlock note1Hold;
    NoteBlock note2Hold;
    NoteBlock note2Single;

    @BeforeEach
    void runBefore() {
        testSong = new Song("test");
        note1Single = new NoteBlock(1, 3, 0);
        note1Hold = new NoteBlock(2, 5, 4);
        note2Hold = new NoteBlock(3, 4, 3);
        note2Single = new NoteBlock(4, 4, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testSong.getSongLength());
        assertEquals(0, testSong.getMaxHits());
        assertEquals(0, testSong.getNoteNumber());
        assertEquals(0, testSong.getHighestScore());
        assertEquals("test", testSong.getSongName());
    }

    @Test
    void testAddNoteOneSingle() {
        assertTrue(testSong.addNote(note1Single));

        assertEquals(4, testSong.getSongLength());
        assertEquals(1, testSong.getMaxHits());
        assertEquals(1, testSong.getNoteNumber());
    }

    @Test
    void testAddNoteOneHold() {
        assertTrue(testSong.addNote(note1Hold));

        assertEquals(10, testSong.getSongLength());
        assertEquals(5, testSong.getMaxHits());
        assertEquals(1, testSong.getNoteNumber());
    }

    @Test
    void testAddNoteMultipleLongestFirst() {
        assertTrue(testSong.addNote(note1Hold));
        assertTrue(testSong.addNote(note1Single));

        assertEquals(10, testSong.getSongLength());
        assertEquals(6, testSong.getMaxHits());
        assertEquals(2, testSong.getNoteNumber());
    }

    @Test
    void testAddNoteMultipleLongestLast() {
        assertTrue(testSong.addNote(note1Single));
        assertTrue(testSong.addNote(note1Hold));

        assertEquals(10, testSong.getSongLength());
        assertEquals(6, testSong.getMaxHits());
        assertEquals(2, testSong.getNoteNumber());
    }

    @Test
    void testAddNoteMultipleTwoSame() {
        assertTrue(testSong.addNote(note1Single));
        assertFalse(testSong.addNote(note1Single));

        assertEquals(4, testSong.getSongLength());
        assertEquals(1, testSong.getMaxHits());
        assertEquals(1, testSong.getNoteNumber());

        assertTrue(testSong.addNote(note1Hold));
        assertFalse(testSong.addNote(note1Hold));
    }

    @Test
    void testAddNoteMultipleInRange() {
        assertTrue(testSong.addNote(note1Hold));
        assertFalse(testSong.addNote(note2Hold));
        assertTrue(testSong.addNote(note1Single));
    }

    @Test
    void testResetOneFalseAlready() {
        assertTrue(testSong.addNote(note1Hold));
        assertFalse(note1Hold.isPlayed());

        testSong.reset();
        assertFalse(note1Hold.isPlayed());
    }

    @Test
    void testResetOneTrue() {
        assertTrue(testSong.addNote(note1Hold));
        assertFalse(note1Hold.isPlayed());
        note1Hold.setPlayed(true);
        assertTrue(note1Hold.isPlayed());

        testSong.reset();
        assertFalse(note1Hold.isPlayed());
    }

    @Test
    void testResetOneTrueOnwFalse() {
        testSong.addNote(note1Hold);
        testSong.addNote(note1Single);

        note1Hold.setPlayed(true);
        note1Single.setPlayed(false);

        testSong.reset();
        assertFalse(note1Hold.isPlayed());
        assertFalse(note1Single.isPlayed());
    }

    @Test
    void testResetOneFalseOneTrue() {
        testSong.addNote(note1Hold);
        testSong.addNote(note1Single);

        note1Hold.setPlayed(false);
        note1Single.setPlayed(true);

        testSong.reset();
        assertFalse(note1Hold.isPlayed());
        assertFalse(note1Single.isPlayed());
    }

    @Test
    void testResetAllTrue() {
        testSong.addNote(note1Hold);
        testSong.addNote(note1Single);
        testSong.addNote(note2Single);

        note1Hold.setPlayed(true);
        note1Single.setPlayed(true);
        note2Single.setPlayed(true);

        testSong.reset();
        assertFalse(note1Hold.isPlayed());
        assertFalse(note1Single.isPlayed());
        assertFalse(note2Single.isPlayed());
    }

    @Test
    void testResetAllFalse() {
        testSong.addNote(note1Hold);
        testSong.addNote(note1Single);
        testSong.addNote(note2Single);

        note1Hold.setPlayed(false);
        note1Single.setPlayed(false);
        note2Single.setPlayed(false);

        testSong.reset();
        assertFalse(note1Hold.isPlayed());
        assertFalse(note1Single.isPlayed());
        assertFalse(note2Single.isPlayed());
    }

    @Test
    void testDistinctTime() {
        assertTrue(testSong.distinctTime(0));
        assertTrue(testSong.distinctTime(3));

        assertTrue(testSong.addNote(note1Single));
        assertFalse(testSong.distinctTime(3));
        assertTrue(testSong.distinctTime(2));
        assertTrue(testSong.distinctTime(4));

        assertTrue(testSong.addNote(note1Hold));
        assertFalse(testSong.distinctTime(5));
        assertFalse(testSong.distinctTime(9));
        assertFalse(testSong.distinctTime(7));
        assertFalse(testSong.distinctTime(3));
        assertTrue(testSong.distinctTime(2));
        assertTrue(testSong.distinctTime(4));

    }

    @Test
    void testGetSongNotes() {
        testSong.addNote(note1Hold);
        testSong.addNote(note1Single);
        testSong.addNote(note2Single);

        ArrayList<NoteBlock> testSongNotes = testSong.getSongNotes();

        assertEquals(3, testSongNotes.size());
        assertEquals(note1Hold, testSongNotes.get(0));
        assertEquals(note1Single, testSongNotes.get(1));
        assertEquals(note2Single, testSongNotes.get(2));
    }

    @Test
    void testSetHighestScore() {
        assertEquals(0, testSong.getHighestScore());
        testSong.setHighestScore(200);
        assertEquals(200, testSong.getHighestScore());
    }

    @Test
    void testSetEnd() {
        assertEquals(0, testSong.getSongLength());
        assertTrue(testSong.addNote(note1Single));
        assertEquals(4, testSong.getSongLength());
        testSong.setEnd(6);
        assertEquals(6, testSong.getSongLength());
    }
}
