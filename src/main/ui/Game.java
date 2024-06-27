package ui;

import model.NoteBlock;
import model.Song;
import model.Repertoire;
import persistence.JsonWriter;
import persistence.JsonReader;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;

//rhythm game application
public class Game {
    //private static final String JSON_STORE = "./data/rhythmgame.json";
    private static final String JSON_STORE = "./data/rhythmgamedemo.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Scanner input;
    private String command;
    private Repertoire selections;

    private int speed;

    //MODIFIES: this
    //EFFECTS: instantiate selections and add the two tutorial songs to it. run the opening menu.
    public Game() throws FileNotFoundException {
        selections = new Repertoire();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        run();
    }

    //EFFECTS: show the opening menu, either quit or go to the start menu depending on user input
    private void run() {
        System.out.println("--------------------------");
        System.out.println("type space to start");
        System.out.println("type q to quit");
        System.out.println("type s to save to file");
        System.out.println("type l to load from file");
        System.out.println("--------------------------");

        command = input.nextLine();

        if (command.equals("space")) {
            startMenu();
        } else if (command.equals("q")) {
            System.out.println("--------------------------");
            System.out.println("quitting program");
            System.out.println("--------------------------");
        } else if (command.equals("s")) {
            saveRep();
        } else if (command.equals("l")) {
            loadRep();
        } else {
            System.out.println("--------------------------");
            System.out.println("invalid input");
            System.out.println("--------------------------");
            run();
        }
    }

    //EFFECTS: goes to the selection or creation menus, the help screen, or quits based on user input
    private void startMenu() {
        System.out.println("--------------------------");
        System.out.println("press s to enter the song selection menu");
        System.out.println("press c to enter the song creation screen");
        System.out.println("press b to go back");
        System.out.println("press ?h for help");
        System.out.println("--------------------------");

        command = input.nextLine();

        if (command.equals("s")) {
            selectMenu();
        } else if (command.equals("c")) {
            createMenu();
        } else if (command.equals("?h")) {
            getHelp();
        } else if (command.equals("b")) {
            run();
        } else {
            System.out.println("--------------------------");
            System.out.println("invalid input");
            System.out.println("--------------------------");
            startMenu();
        }
    }

    //EFFECTS: return to the start menu or print a list of playable songs and selects a song to play
    private void selectMenu() {
        songSelectionPrint();
        command = input.nextLine();

        if (command.equals("b")) {
            startMenu();
        } else {
            try {
                Integer.valueOf(command);
                System.out.println("under construction. expect results in 2 weeks");
                selectMenu();
            } catch (Exception e) {
                System.out.println("invalid input");
                selectMenu();
            }
        }
    }

    //getSpeed();
    //new SongLoop(speed, getSongFromInput(command, "not edit"));

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: show the list of selectable songs, and go back to start or edit the song/new song
    private void createMenu() {
        System.out.println("welcome to the song creation menu.");
        System.out.println("please select a song to add to. to create a new song, press n");
        songSelectionPrint();

        command = input.nextLine();

        if (command.equals("b")) {
            startMenu();
        } else if (command.equals("n")) {
            createNewSong();
        } else {
            try {
                Integer.valueOf(command);
                editSong(getSongFromInput(command, "edit"), Integer.valueOf(command));
            } catch (Exception e) {
                System.out.println("invalid input");
                selectMenu();
            }
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: get the nth song on the song list from the command and
    // depending on the control, decide what to do with it
    private Song getSongFromInput(String command, String control) {
        try {
            selections.getNthSong(Integer.parseInt(command));
            return selections.getNthSong(Integer.parseInt(command));
        } catch (Exception e) {
            System.out.println("invalid input");
            if (control.equals("edit")) {
                createMenu();
            } else {
                selectMenu();
            }

            return null;
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: get the nth note on the note list from the command
    private NoteBlock getNoteFromInput(String command, Song song) {
        try {
            return song.getSongNotes().get(Integer.parseInt(command));
        } catch (Exception e) {
            System.out.println("invalid input, creating new note");
            return new NoteBlock(5, 5, 5);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new song with name from user input and adds it to selections
    private void createNewSong() {
        command = input.nextLine();

        System.out.println("please input the name of the new song");
        Song newSong = new Song(command);
        selections.addSong(newSong);
        editSong(newSong, selections.getSize());
    }

    //REQUIRES: note that index will always be valid
    //MODIFIES: this (selections)
    //EFFECTS: chooses to exit, edit a note, or create a new note. changes the selected note/
    // adds new note to song according to specifications
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void editSong(Song song, int index) {
        boolean keepGoing = true;
        System.out.println("press x to exit, d to delete, note index to edit, anything else to make new note");

        while (keepGoing) {
            printNotes(song);
            command = input.nextLine();
            if (command.equals("x")) {
                keepGoing = false;
                createMenu();
            } else if (command.equals("d")) {
                try {
                    selections.deleteNthSong(index);
                    keepGoing = false;
                } catch (Exception e) {
                    //do nothing
                }
            } else {
                try {
                    if (getNoteFromInput(command, song).getColumn() == 5) {
                        createNewNote(song, index);
                    } else {
                        editNote(getNoteFromInput(command, song), song, Integer.parseInt(command), index);
                    }
                }  catch (Exception e) {
                    //nothing
                }
            }
        }
    }

    //REQUIRES: index < size of list of notes in song, index >= 0
    //MODIFIES: song, this (selections)
    //EFFECTS: edits a note in the song according to inputs, or deletes the song at index2
    private void editNote(NoteBlock note, Song song, int index, int index2) {
        int time = note.getStart();
        int length = note.getHoldLength();
        int column = note.getColumn();

        System.out.println("press t to edit the time, l to edit length, c to edit column, d to delete");
        command = input.nextLine();

        if (command.equals("t")) {
            postAssignVal(assignVal(1), song, index2);
            note.setStart(assignVal(1));
        } else if (command.equals("l")) {
            postAssignVal(assignVal(3), song, index2);
            note.setHoldLength(assignVal(3));
        } else if (command.equals("c")) {
            postAssignVal(assignVal(2), song, index2);
            note.setColumn(assignVal(2));
        } else if (command.equals("d")) {
            song.getSongNotes().remove(index);
            System.out.println("removed: note at time " + time + " in column " + column + " of length " + length);
        } else {
            editNote(note, song, index, index2);
        }

        editSong(song, index2);
    }

    //MODIFIES: this
    //EFFECTS: creates a new note at inputted start time, column, and hold length, adds it to song
    private void createNewNote(Song song, int index) {
        int time;
        int length;
        int column;

        time = assignVal(1);
        postAssignVal(time, song, index);

        column = assignVal(2);
        postAssignVal(column, song, index);

        length = assignVal(3);
        postAssignVal(length, song, index);

        System.out.println("confirm: note at time " + time + " in column " + column + " of length " + length);
        song.addNote(new NoteBlock(column, time, length));
    }

    //EFFECTS: prints out the notes and their characteristics in a given song
    private void printNotes(Song song) {
        int index = 0;

        for (NoteBlock nextNote : song.getSongNotes()) {
            System.out.println("Note at index " + index + " time = " + nextNote.getStart());
            System.out.println("              length = " + nextNote.getHoldLength());
            System.out.println("              column = " + nextNote.getColumn());
            index++;
        }
    }

    //REQUIRES: n = one of 1, 2, 3
    //EFFECTS: returns a value for r based on the value of n
    private int assignVal(int n) {
        int r;
        if (n == 1) {
            System.out.println("When to put the new note?");
            r = assignTimeLength();
        } else if (n == 2) {
            System.out.println("Which column to put the new note?");
            r = assignCol();
        } else {
            System.out.println("How long is the new note?");
            r = assignTimeLength();
        }

        return r;
    }

    //EFFECTS: if returnedValue is -1, return to the song edit menu
    private void postAssignVal(int returnedValue, Song returnTo, int index) {
        if (returnedValue == -1) {
            System.out.println("invalid input");
            editSong(returnTo, index);
        }
    }

    //EFFECTS: if the input isn't an integer, back to create menu. else, returns value based on input
    private int assignTimeLength() {
        command = input.nextLine();
        try {
            Integer.parseInt(command);
        } catch (Exception e) {
            System.out.println("invalid input");
            createMenu();
        }
        if (Integer.parseInt(command) >= 0) {
            return Integer.parseInt(command);
        } else {
            return -1;
        }
    }

    //EFFECTS: if the input isn't an integer, back to create menu. else, returns value based on input
    private int assignCol() {
        command = input.nextLine();
        try {
            Integer.valueOf(command);
            if (Integer.parseInt(command) > 0 && Integer.parseInt(command) < 5) {
                return Integer.parseInt(command);
            } else {
                return -1;
            }
        } catch (Exception e) {
            System.out.println("invalid input");
            createMenu();
            return -1;
        }
    }

    //EFFECTS: prints out songs in list selections
    private void songSelectionPrint() {
        int n = 0;
        for (Song nextSong : selections.getSongs()) {
            System.out.println("press " + n + " to access song " + nextSong.getSongName());
            n++;
        }
        System.out.println("press b to go back");
    }

    //MODIFIES: this (speed)
    //EFFECTS: assigns value to speed based on input
    private void getSpeed() {
        command = input.nextLine();
        try {
            Integer.valueOf(command);
        } catch (Exception e) {
            System.out.println("invalid input");
            selectMenu();
        }
        if (Integer.parseInt(command) >= 0 && Integer.parseInt(command) <= 10) {
            this.speed = Integer.parseInt(command);
        } else {
            System.out.println("invalid input");
            selectMenu();
        }
    }

    //EFFECTS: saves the repertoire to file
    // Source: JsonSerializationDemo, CPSC 210 course content
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void saveRep() {
        try {
            jsonWriter.open();
            jsonWriter.write(selections);
            jsonWriter.close();
            System.out.println("Saved " + selections.getListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: loads repertoire from file
    // Source: JsonSerializationDemo, CPSC 210 course content
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadRep() {
        try {
            selections = jsonReader.read();
            System.out.println("Loaded " + selections.getListName() + " from " + JSON_STORE);
            run();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            run();
        }
    }

//    //MODIFIES: this
//    //EFFECTS: plays the song (currently lists song name, length, and highest score
//    //                          and then returns to menu
//    private void playSong(Song song) {
//
//        System.out.println("Song " + song.getSongName() + ": Length " + song.getSongLength());
//        System.out.println("previous record " + song.getHighestScore());
//
//        int timeStamp = 0;
//
//        System.out.println("this is still under construction. return to menu");
//
//        selectMenu();
//
//
//
//        //for loop that makes call to render + checkclick, getCurr
//    }

//    //REQUIRES: time >= 0
//    //EFFECTS: finds notes in the song's note list and adds them to a new list if their
//    //          start to start + length range includes the current int time and they
//    //          have not yet been played
//    private ArrayList<NoteBlock> getCurr(int time) {
//        return null; //stub
//    }
//
//    //REQUIRES: time >= 0
//    //EFFECTS: print out the current screen with the combo and score
//    private void render(int time) {
//        //stub
//    }
//
//    //MODIFIES: this
//    //EFFECTS: checks for user input of column number, sets the closest note in the column to
//    //          played
//    private void checkClick() {
//        //stub
//    }

    //EFFECTS: prints out instruction menu
    private void getHelp() {
        System.out.println("state your query");
        System.out.println("press 1 for gameplay instructions");
        System.out.println("that's it that's the only menu option");
        System.out.println("press 2 to go back");

        command = input.nextLine();

        if (command.equals("1")) {
            //printed gameplay instructions
            instruct();
        } else if (command.equals("2")) {
            startMenu();
        } else {
            System.out.println("--------------------------");
            System.out.println("invalid input");
            System.out.println("--------------------------");
            getHelp();
        }
    }

    //EFFECTS: prints out gameplay instructions
    private void instruct() {
        System.out.println("instructions to be added later");

        //4 columns - 1 2 3 4
        //press number keys 1 2 3 4 and then Enter to hit notes as they fall down

        getHelp();
    }
}
