package persistence;

import model.NoteBlock;
import model.Song;
import model.Repertoire;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Source: JsonSerializationDemo, CPSC 210 course content
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Repertoire rep = new Repertoire();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRepertoire() {
        try {
            Repertoire rep = new Repertoire();
            try {
                rep.deleteNthSong(0);
                rep.deleteNthSong(0);
            } catch (Exception e1){
                fail();
            }
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRepertoire.json");
            writer.open();
            writer.write(rep);
            assertEquals(0, rep.getSize());
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRepertoire.json");
            rep = reader.read();
            assertEquals("default", rep.getListName());
            assertEquals(0, rep.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRepertoire() {
        try {
            Repertoire rep = new Repertoire();

            rep.addSong(new Song("testing one!"));
            rep.addSong(new Song("testing two!"));

            try {
                rep.getNthSong(2).addNote(new NoteBlock(1, 1, 1));
                rep.getNthSong(2).addNote(new NoteBlock(3, 8, 0)); //length 9
                rep.getNthSong(3).addNote(new NoteBlock(2, 4, 2)); //l7
            } catch (Exception e) {
                fail();
            }

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRepertoire.json");
            writer.open();
            writer.write(rep);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRepertoire.json");
            rep = reader.read();
            assertEquals("default", rep.getListName());
            ArrayList<Song> songs = rep.getSongs();
            assertEquals(4, rep.getSize());

            assertEquals("Tutorial 1", songs.get(0).getSongName());
            assertEquals("Tutorial 2", songs.get(1).getSongName());
            assertEquals("testing one!", songs.get(2).getSongName());
            assertEquals("testing two!", songs.get(3).getSongName());

            assertEquals(0, songs.get(0).getHighestScore());
            assertEquals(0, songs.get(1).getHighestScore());
            assertEquals(0, songs.get(2).getHighestScore());
            assertEquals(0, songs.get(3).getHighestScore());

            assertEquals(7, songs.get(0).getSongNotes().size());
            assertEquals(5, songs.get(1).getSongNotes().size());
            assertEquals(2, songs.get(2).getSongNotes().size());
            assertEquals(1, songs.get(3).getSongNotes().size());

            assertEquals(12, songs.get(0).getSongLength());
            assertEquals(17, songs.get(1).getSongLength());
            assertEquals(9, songs.get(2).getSongLength());
            assertEquals(7, songs.get(3).getSongLength());

            ArrayList<NoteBlock> list = songs.get(3).getSongNotes();
            assertEquals(2, list.get(0).getColumn());
            assertEquals(4, list.get(0).getStart());
            assertEquals(2, list.get(0).getHoldLength());

            ArrayList<NoteBlock> list1 = new ArrayList<>();
            ArrayList<NoteBlock> list2 = new ArrayList<>();
            ArrayList<NoteBlock> list3 = new ArrayList<>();
            ArrayList<NoteBlock> list4 = new ArrayList<>();

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

            list3.add(new NoteBlock(1, 1, 1));
            list3.add(new NoteBlock(3, 8, 0));

            list4.add(new NoteBlock(2, 4, 2));

            try {
                checkSong("Tutorial 1", 0, list1, 12, rep.getNthSong(0));
                checkSong("Tutorial 2", 0, list2, 17, rep.getNthSong(1));
                checkSong("testing one!", 0, list3, 9, rep.getNthSong(2));
                checkSong("testing two!", 0, list4, 7, rep.getNthSong(3));
            } catch (Exception e) {
                fail();
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testSongToJson() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRepertoire.json");
        Repertoire rep = new Repertoire();
        assertEquals(2, rep.getSize());

        Song song = new Song("hello");
        song.addNote(new NoteBlock(2, 4, 2));
        song.addNote(new NoteBlock(1, 0, 0));

        JSONObject obj = song.toJson();

        reader.addSong(rep, obj);
        assertEquals(3, rep.getSize());

        try {
            Song testSong = rep.getNthSong(2);
            assertEquals("hello", testSong.getSongName());
            assertEquals(0, testSong.getHighestScore());
            assertEquals(2, testSong.getSongNotes().size());
            assertEquals(7, testSong.getSongLength());

            ArrayList<NoteBlock> list = testSong.getSongNotes();
            assertEquals(2, list.get(0).getColumn());
            assertEquals(4, list.get(0).getStart());
            assertEquals(2, list.get(0).getHoldLength());

            assertEquals(1, list.get(1).getColumn());
            assertEquals(0, list.get(1).getStart());
            assertEquals(0, list.get(1).getHoldLength());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testNoteBlockToJson() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRepertoire.json");
        NoteBlock note = new NoteBlock(1, 1, 1);
        JSONObject obj = note.toJson();

        NoteBlock testNote = reader.readNote(obj);

        assertEquals(1, testNote.getColumn());
        assertEquals(1, testNote.getStart());
        assertEquals(1, testNote.getHoldLength());
    }
}
