package model;

import org.json.JSONObject;
import persistence.Writable;

//represents a note with the column it appears in, the start timestamp in 0.5 seconds, the length
//it should be held in .5 seconds, and whether it has been played yet
public class NoteBlock implements Writable {

    private int column; //the column the note appears in during the game
    private int holdLength; //how long the note should be "held"
    private int start; //the time at which the note will first hit the line, in 0.5 seconds
    private boolean played; //has the note been played yet?

    //REQUIRES: col is 1, 2, 3, or 4, length is >= 0, time >= 0
    //MODIFIES: this
    //EFFECTS: creates a note with a column, start time, length in .5 seconds, sets played to false
    public NoteBlock(int col, int time, int length) {
        this.column = col;
        this.holdLength = length;
        this.start = time;
        this.played = false;
    }

    //REQUIRES: newLength >= 0
    //MODIFIES: this
    //EFFECTS: changes the holdLength of the note to the input
    public void setHoldLength(int newLength) {
        this.holdLength = newLength;
    }

    //REQUIRES: newCol = one of 1, 2, 3, 4
    //MODIFIES: this
    //EFFECTS: changes the column of the note to the input
    public void setColumn(int newCol) {
        this.column = newCol;
    }

    //REQUIRES: newStart >= 0
    //MODIFIES: this
    //EFFECTS: changes the start of the note to the input
    public void setStart(int newStart) {
        this.start = newStart;
    }

    //MODIFIES: this
    //EFFECTS: sets the played to the input
    public void setPlayed(boolean played) {
        this.played = played;
    }

    //EFFECTS: returns the column of the note
    public int getColumn() {
        return column;
    }

    //EFFECTS: returns the holdLength of the note
    public int getHoldLength() {
        return holdLength;
    }

    //EFFECTS: returns the start time of the note
    public int getStart() {
        return start;
    }

    //EFFECTS: returns true if the note has been played
    public boolean isPlayed() {
        return this.played;
    }

    //EFFECTS: converts noteblock to json
    // Source: JsonSerializationDemo, CPSC 210 course content.
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("column", column);
        json.put("holdLength", holdLength);
        json.put("start", start);
        return json;
    }
}
