package persistence;

import model.NoteBlock;
import model.Song;
import model.Repertoire;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Source: JsonSerializationDemo, CPSC 210 course content
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    //add @test
    @Test
    protected void checkNoteBlock(int col, int hold, int start, NoteBlock noteBlock) {
        assertEquals(start, noteBlock.getStart());
        assertEquals(col, noteBlock.getColumn());
        assertEquals(hold, noteBlock.getHoldLength());
        assertFalse(noteBlock.isPlayed());
    }

    @Test
    protected void checkSong(String name, int score, ArrayList<NoteBlock> notes, int length, Song song) {
        assertEquals(name, song.getSongName());
        assertEquals(score, song.getHighestScore());
        assertEquals(notes.size(), song.getNoteNumber());
        assertEquals(length, song.getSongLength());

        int n = 0;
        int size = notes.size();
        while (n < size) {
            NoteBlock next = notes.get(n);
            NoteBlock ref = song.getSongNotes().get(n);
            checkNoteBlock(next.getColumn(), next.getHoldLength(), next.getStart(), ref);
            n++;
        }
    }

    @Test
    protected void checkRep(String name, ArrayList<Song> songs, Repertoire rep) {
        assertEquals(name, rep.getListName());
        assertEquals(songs.size(), rep.getSize());
        int n = 0;
        int size = songs.size();
        while (n < size) {
            Song next = songs.get(n);
            try {
                Song ref = rep.getNthSong(n);
                checkSong(next.getSongName(), next.getHighestScore(), next.getSongNotes(), next.getSongLength(), ref);
            } catch (Exception e) {
                fail();
            }
            n++;
        }
    }
}
