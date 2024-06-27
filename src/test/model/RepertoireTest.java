package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepertoireTest {

    Repertoire testRep;
    Song song;

    @BeforeEach
    void runBefore() {
        testRep = new Repertoire();
        song = new Song("testing");
    }

    @Test
    void testConstructor() {
        assertEquals(2, testRep.getSize());
        assertEquals("default", testRep.getListName());
        try {
            assertEquals("Tutorial 1", testRep.getNthSong(0).getSongName());
            assertEquals("Tutorial 2", testRep.getNthSong(1).getSongName());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testSetListName() {
        assertEquals("default", testRep.getListName());
        testRep.setListName("hello");
        assertEquals("hello", testRep.getListName());
    }

    @Test
    void testGetNthSong() {
        try {
            Song song1 = testRep.getNthSong(0);
            Song song2 = testRep.getNthSong(1);
            assertEquals("Tutorial 1", song1.getSongName());
            assertEquals("Tutorial 2", song2.getSongName());
            assertEquals(12, song1.getSongLength());
            assertEquals(17, song2.getSongLength());

            assertEquals(7, song1.getNoteNumber());
            assertEquals(5, song2.getNoteNumber());
            assertEquals(0, song1.getHighestScore());
            assertEquals(0, song2.getHighestScore());
        } catch (Exception e) {
            fail();
        }

        try {
            testRep.getNthSong(2);
            fail();
        } catch (Exception e) {
            assertEquals("default", testRep.getListName());
        }
    }

    @Test
    void testAddSong() {
        testRep.addSong(song);
        try {
            assertEquals("Tutorial 1", testRep.getNthSong(0).getSongName());
            assertEquals("Tutorial 2", testRep.getNthSong(1).getSongName());
            assertEquals("testing", testRep.getNthSong(2).getSongName());
        } catch (Exception e) {
            fail();
        }

        try {
            testRep.getNthSong(3);
            fail();
        } catch (Exception e) {
            assertEquals(3, testRep.getSize());
        }
    }

    @Test
    void testDeleteNthSong() {
        try {
            assertEquals(2, testRep.getSize());
            testRep.deleteNthSong(1);
            assertEquals(1, testRep.getSize());
            assertEquals("Tutorial 1", testRep.getNthSong(0).getSongName());
            testRep.deleteNthSong(0);
            assertEquals(0, testRep.getSize());
        } catch (Exception e) {
            fail();
        }

        try {
            testRep.addSong(song);
            testRep.deleteNthSong(2);
            fail();
        } catch (Exception e) {
            try {
                testRep.deleteNthSong(1);
                fail();
            } catch (Exception e1) {
                assertEquals(1, testRep.getSize());
            }
        }
    }

    @Test
    void testGetSongs() {
        ArrayList<Song> songList = testRep.getSongs();
        assertEquals(songList.size(), testRep.getSize());
        try {
            assertEquals(songList.get(0), testRep.getNthSong(0));
            assertEquals(songList.get(1), testRep.getNthSong(1));
        } catch (Exception e) {
            fail();
        }
    }
}
