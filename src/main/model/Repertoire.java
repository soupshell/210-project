package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a repertoire with a save file name and a list of songs
public class Repertoire implements Writable {
    private ArrayList<Song> songs;
    private String listName;

    //MODIFIES: this
    //EFFECTS: instantiates a new Repertoire with 2 tutorial songs and listName "default"
    public Repertoire() {
        listName = "default";
        songs = new ArrayList<Song>();
        makeSongTutorial();
    }

    //MODIFIES: this
    //EFFECTS: makes two tutorial songs and adds them to songs
    public void makeSongTutorial() {
        Song tutorial1 = new Song("Tutorial 1");
        Song tutorial2 = new Song("Tutorial 2");

        tutorial1.addNote(new NoteBlock(1,5,0));
        tutorial1.addNote(new NoteBlock(2,6,0));
        tutorial1.addNote(new NoteBlock(3,7,0));
        tutorial1.addNote(new NoteBlock(4,8,0));
        tutorial1.addNote(new NoteBlock(3,9,0));
        tutorial1.addNote(new NoteBlock(2,10,0));
        tutorial1.addNote(new NoteBlock(1,11,0));

        tutorial2.addNote(new NoteBlock(3,4,3));
        tutorial2.addNote(new NoteBlock(1,8,2));
        tutorial2.addNote(new NoteBlock(1,12,0));
        tutorial2.addNote(new NoteBlock(1,14,0));
        tutorial2.addNote(new NoteBlock(2,16,0));

        songs.add(tutorial1);
        songs.add(tutorial2);
    }

    //MODIFIES: this
    //EFFECTS: changes the listName to the given string
    public void setListName(String name) {
        listName = name;
    }

    //REQUIRES: n >= 0
    //EFFECTS: returns the nth song in the songs list. if n is invalid, throw an exception
    public Song getNthSong(int n) throws Exception {
        try {
            return songs.get(n);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds the given song to the list of songs
    public void addSong(Song song) {
        songs.add(song);
        String desc = "Added new song " + song.getSongName() + " to rep " + this.getListName();
        EventLog.getInstance().logEvent(new Event(desc));
    }

    //REQUIRES: n >= 0
    //MODIFIES: this
    //EFFECTS: removes the song at given n in the list
    public void deleteNthSong(int n) throws Exception {
        try {
            Song toDelete = songs.remove(n);
            //songs.remove(n);
            String desc = "Removed song " + toDelete.getSongName() + " from rep " + this.getListName();
            EventLog.getInstance().logEvent(new Event(desc));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    //EFFECTS: returns the number of songs in the songs list
    public int getSize() {
        return songs.size();
    }

    //EFFECTS: return the list of songs
    public ArrayList<Song> getSongs() {
        return songs;
    }

    //EFFECTS: returns the listName
    public String getListName() {
        return listName;
    }

    //EFFECTS: converts repertoire to json
    // Source: JsonSerializationDemo, CPSC 210 course content
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("song list", songs);
        json.put("list name", listName);
        return json;
    }
}
