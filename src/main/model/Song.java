package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

//represents a playable song in the game, with a list of note blocks, an ending time in 1/2 seconds,
//a current highest score, and a name.
public class Song implements Writable {

    private ArrayList<NoteBlock> notes; //the list of notes to play in the song
    private int end; //the timestamp in units of 0.5 seconds of the end of the song
    private int highestScore; //the current highest score
    private String songName; //name of the song

    //MODIFIES: this
    //EFFECTS: name on song is set to input name. Creates a song with no end, no notes, no highest score
    public Song(String name) {
        this.notes = new ArrayList<NoteBlock>();
        this.end = 0;
        this.highestScore = 0;
        this.songName = name;
    }

    //REQUIRES: time >= 0
    //EFFECTS: produces true if at the current time there are no notes being played
    public boolean distinctTime(int time) {
        boolean distinct = true;
        for (NoteBlock note : this.notes) {
            int noteStart = note.getStart();
            int noteEnd = noteStart + note.getHoldLength();

            if (time >= noteStart && noteEnd >= time) {
                distinct = false;
                break;
            }
        }
        return distinct;
    }

    //MODIFIES: this
    //EFFECTS: adds noteblock n to the end of the notes list and produces true
    //          if there are no other blocks in the list being played
    //          between noteblock's start and end times, changes the end
    //          of the song to be 1 + the end of the last noteblock if the new block's end is
    //          larger than the previous end
    //          else, produce false and do nothing
    public boolean addNote(NoteBlock n) {
        int nstart = n.getStart();
        int nend = nstart + n.getHoldLength();

        boolean add = true;

        for (int i = nstart; i <= nend; i++) {
            if (!this.distinctTime(i)) {
                add = false;
                break;
            }
        }

        if (add) {
            if (nend + 1 > this.end) {
                this.end = nend + 1;
            }

            String desc = "Added new note from " + nstart + " to " + nend + " in column " + n.getColumn();

            EventLog.getInstance().logEvent(new Event(desc));
            return this.notes.add(n);
        } else {
            return false;
        }

    }

    //MODIFIES: this
    //EFFECTS: set the played of every note in the note list to false
    public void reset() {
        for (NoteBlock note : this.notes) {
            note.setPlayed(false);
        }
    }

    //EFFECTS: count how many "hits" are in the note list in total
    public int getMaxHits() {
        int maxHits = 0;

        for (NoteBlock nextNote : this.notes) {
            maxHits += nextNote.getHoldLength() + 1;
        }

        return maxHits;

    }

    //EFFECTS: returns the end timestamp of the song
    public int getSongLength() {
        return this.end;
    }

    //EFFECTS: return the number of notes in the note list
    public int getNoteNumber() {
        return this.notes.size();
    }

    //EFFECTS: return the highest score of the song
    public int getHighestScore() {
        return this.highestScore;
    }

    //EFFECTS: return the name of the song
    public String getSongName() {
        return this.songName;
    }


    //STARTING FROM HERE EVERYTHING NEEDS TO BE TESTED

    //REQUIRES: n >= 0
    //MODIFIES: this
    //EFFECTS: sets the end timestamp of the song to n
    public void setEnd(int n) {
        this.end = n;
    }

    //REQUIRES: n >= 0
    //MODIFIES: this
    //EFFECTS: set the highest score to n
    public void setHighestScore(int n) {
        this.highestScore = n;
    }

    //EFFECTS: return the list of notes in a song
    public ArrayList<NoteBlock> getSongNotes() {
        return this.notes;
    }

    //EFFECTS: converts song to json
    // Source: JsonSerializationDemo, CPSC 210 course content
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("songName", songName);
        json.put("highestScore", highestScore);
        json.put("songNotes", notes);
        json.put("songLength", end);
        return json;
    }
}
