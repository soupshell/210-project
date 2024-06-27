package ui;

import model.NoteBlock;
import model.Song;

import java.util.*;

public class SongLoop {
    private Scanner input;
    private String command;
    private Song playing;
    private int speed;
    private int timestamp;

    private int combo;
    private int score;

    //MODIFIES: this
    //EFFECTS:
    public SongLoop(int speed, Song song) {
        playing = song;
        this.speed = speed;
        this.timestamp = 0;
    }

    //MODIFIES: this
    //EFFECTS: plays the song (currently lists song name, length, and highest score
    //                          and then returns to menu
    private void playSong(Song song) {

        System.out.println("Song " + song.getSongName() + ": Length " + song.getSongLength());
        System.out.println("previous record " + song.getHighestScore());

        System.out.println("this is still under construction. return to menu");



        //for loop that makes call to render + checkclick, getCurr
    }

    //REQUIRES: time >= 0
    //EFFECTS: finds notes in the song's note list and adds them to a new list if their
    //          start to start + length range includes the current int time and they
    //          have not yet been played
    private ArrayList<NoteBlock> getCurr(int time) {
        ArrayList<NoteBlock> curr = new ArrayList<>();
        for (NoteBlock nextNote : playing.getSongNotes()) {
            //from time - 1 ti time + speed, get those notes if they're in range
            int start = nextNote.getStart();
            int hold = nextNote.getHoldLength();
            if (!nextNote.isPlayed()) {
                if (start >= time - 1 && start <= time + speed) {
                    curr.add(nextNote);
                } else if (hold > 0 && isWithinTime(start, hold)) {
                    curr.add(nextNote);
                }
            }
        }
        return curr; //stub
    }

    //REQUIRES:
    //EFFECTS:
    private Boolean isWithinTime(int start, int range) {
        int t = timestamp - 1;
        while (t <= timestamp + speed) {
            if (t >= start && t <= start + range) {
                return true;
            }
            t++;
        }
        return false; //stub
    }

    //REQUIRES: time >= 0
    //EFFECTS: print out the current screen with the combo and score
    private void render(int time) {
        //stub
    }

    //MODIFIES: this
    //EFFECTS: checks for user input of column number, sets the closest note in the column to
    //          played
    private void checkClick() {
        //stub
    }
}
