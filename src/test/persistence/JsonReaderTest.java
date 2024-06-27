package persistence;

import model.NoteBlock;
import model.Song;
import model.Repertoire;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Source: JsonSerializationDemo, CPSC 210 course content
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Repertoire rep = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRepertoire.json");
        try {
            Repertoire rep = reader.read();
            assertEquals("default", rep.getListName());
            assertEquals(0, rep.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRepertoire.json");
        try {
            Repertoire rep = reader.read();
            assertEquals("default", rep.getListName());
            ArrayList<Song> songs = rep.getSongs();
            assertEquals(3, songs.size());

            ArrayList<NoteBlock> list1 = new ArrayList<>();
            ArrayList<NoteBlock> list2 = new ArrayList<>();
            ArrayList<NoteBlock> list3 = new ArrayList<>();

            list1.add(new NoteBlock(1,5,0));
            list1.add(new NoteBlock(2,6,0));
            list1.add(new NoteBlock(3,7,0));
            list1.add(new NoteBlock(4,8,0));
            list1.add(new NoteBlock(3,9,0));
            list1.add(new NoteBlock(2,10,0));
            list1.add(new NoteBlock(1,11,0));

            list2.add(new NoteBlock(3,4,3));
            list2.add(new NoteBlock(1,8,2));
            list2.add(new NoteBlock(1,12,0));
            list2.add(new NoteBlock(1,14,0));
            list2.add(new NoteBlock(2,16,0));

            list3.add(new NoteBlock(1,3,0));
            list3.add(new NoteBlock(3,8,3));
            try {
                checkSong("Tutorial 1", 0, list1, 12, rep.getNthSong(0));
                checkSong("Tutorial 2", 0, list2, 17, rep.getNthSong(1));
                checkSong("\"this song\"", 0, list3, 12, rep.getNthSong(2));
            } catch (Exception e) {
                fail();
            }
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testParseSongsException() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRepertoire.json");
        try {
            Repertoire rep = reader.read();
            assertEquals("default", rep.getListName());
            assertEquals(0, rep.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
