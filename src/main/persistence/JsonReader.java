package persistence;

import model.Repertoire;
import model.Song;
import model.NoteBlock;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Source: JsonSerializationDemo, CPSC 210 course content
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a list of songs from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Repertoire read() throws IOException {
        //find a way to do this
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        try {
            return parseSongs(jsonObject);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses repertoire from JSON object and returns it
    private Repertoire parseSongs(JSONObject jsonObject) throws Exception {
        String name = jsonObject.getString("list name");
        Repertoire rep = new Repertoire();
        rep.setListName(name);
        addSongs(rep, jsonObject);
        try {
            rep.deleteNthSong(0);
            rep.deleteNthSong(0);
        } catch (Exception e) {
            throw new Exception();
        }
        return rep;
    }

    // MODIFIES: rep
    // EFFECTS: parses songs from JSON object and adds them to workroom
    private void addSongs(Repertoire rep, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("song list"); //these are the songs
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json; //represents the song
            addSong(rep, nextSong);
        } //eventually will add all the songs to rep
    }

    // MODIFIES: rep
    // EFFECTS: parses song from JSON object and adds it to workroom
    //NOTE: previously private, but I moved it to public so I could test code.
    public void addSong(Repertoire rep, JSONObject nextSong) {
        String songName = nextSong.getString("songName");
        int highestScore = nextSong.getInt("highestScore");
        int end = nextSong.getInt("songLength");
        JSONArray jsonNotes = nextSong.getJSONArray("songNotes");

        Song song = new Song(songName);
        song.setEnd(end);
        song.setHighestScore(highestScore);

        for (Object json2 : jsonNotes) { //for each note read off of the new array
            JSONObject note = (JSONObject) json2; //note is the object
            song.addNote(readNote(note)); //read the note and add it
        } //all notes added to song

        rep.addSong(song); //add song to rep
    }

    // MODIFIES: rep
    // EFFECTS: reads notes and adds to list
    // used to be private, changed to public for ease of testing
    public NoteBlock readNote(JSONObject note) {
        int col = note.getInt("column");
        int time = note.getInt("start");
        int length = note.getInt("holdLength");

        return new NoteBlock(col, time, length);
    }
}
